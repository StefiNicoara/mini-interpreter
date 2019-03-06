package controller;

import domain.PrgState;
import domain.adt.MyIHeap;
import domain.adt.MyIStack;
import domain.fileHandling.FileData;
import domain.fileHandling.IFileTable;
import domain.stmt.IStmt;
import exception.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository myRepository;
    private ExecutorService executor;

    public Controller(Repository myRepository) {
        this.myRepository = myRepository;
        executor = Executors.newFixedThreadPool(2);
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> l){
        return l.stream().filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public int noPrgStates(){
        return myRepository.getPrgList().size();
    }

    public PrgState getPrgStateByIndex(int index){
        return  myRepository.getPrgList().get(index);
    }

    public ObservableList<String> getPrgStatesID(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(PrgState i : myRepository.getPrgList())
            list.add( String.valueOf(i.getID()));

        return list;
    }

    private void oneStepForAll(List<PrgState> list)  {


        List<Callable<PrgState>> callList = list.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());


        List<PrgState> newList = null;
        try {
            newList = executor.invokeAll(callList)
                    .stream()
                    .map(  future ->
                    {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new MyException(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }

        list.forEach(prg-> myRepository.logPrgStateExec(prg));
        list.addAll(newList);
        myRepository.setPrgList(list);

    }

    public void allStep() {
        executor = Executors.newFixedThreadPool(46);

        List<PrgState> prgList = removeCompletedPrg(myRepository.getPrgList());

        prgList.forEach(prg->myRepository.logPrgStateExec(prg));

        while(prgList.size() > 0){

            prgList.forEach( prg -> {
                prg.getHeap().setContent(conservativeGarbageCollector(prg.getSymTable().getAllValues(), prg.getHeap().getContent()));
            });
            oneStepForAll(prgList);
            prgList = removeCompletedPrg(myRepository.getPrgList());

        }

        executor.shutdownNow();

        myRepository.setPrgList(prgList);
    }



    private Map<Integer, Integer> conservativeGarbageCollector(List<Integer> symTableValues, Map<Integer, Integer> heap) {


        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public boolean oneStepGUI () {

        List<PrgState> prgList = removeCompletedPrg(myRepository.getPrgList());

        if (prgList.size() > 0) {
            oneStepForAll(prgList);
            // prgList = removeCompletedPrg(myRepository.getPrgList());
            return true;
        } else {
            executor.shutdownNow();
            myRepository.setPrgList(prgList);
            return false;
        }
    }
}
