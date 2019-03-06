package sample;

import controller.Controller;
import domain.PrgState;
import domain.adt.*;
import domain.fileHandling.FileData;
import domain.fileHandling.FileTable;
import domain.fileHandling.IFileTable;
import domain.stmt.GenIDFork;
import domain.stmt.IStmt;
import domain.utilsTables.BarrierTableView;
import domain.utilsTables.FileTableView;
import domain.utilsTables.HeapTableView;
import domain.utilsTables.SymTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.Repository;

public class runController {

    // FOR THE HEAP TABLE
    @FXML private TableView<HeapTableView> heapTable;
    @FXML private TableColumn<HeapTableView, Integer> heapAddressColumn;
    @FXML private TableColumn<HeapTableView, Integer> heapValueColumn;

    // FOR THE FILE TABLE
    @FXML private TableView<FileTableView> fileTable;
    @FXML private TableColumn<FileTableView, Integer> fileTableIdentifier;
    @FXML private TableColumn<FileTableView, String> fileTableName;

    // FOR THE SYM TABLE
    @FXML private TableView<SymTableView> symTable;
    @FXML private TableColumn<SymTableView, String> symTableVarName;
    @FXML private TableColumn<SymTableView, Integer> symTableValue;

    @FXML private ListView<String> outList;
    @FXML private ListView<String> exeStack;
    @FXML private ListView<String> prgStateIdentifiers;
    @FXML private Button oneStepButton;
    @FXML private TextField noPrgStateTextField;

    // FOR THE BARRIER TABLE
    @FXML private TableView<BarrierTableView> barrierTable;
    @FXML private TableColumn<BarrierTableView, Integer> barrierID;
    @FXML private TableColumn<BarrierTableView, Integer> barrierValue;
    @FXML private TableColumn<BarrierTableView, String> barrierList;

    private Controller ctrl;

    @FXML
    public void initialize(){

        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Integer> dict = new MyDictionary<>();
        MyIList<Integer> list = new MyList<>();
        IFileTable<Integer, FileData> fileTable = new FileTable<>();
        MyIHeap<Integer, Integer> heap = new MyHeap<>();
        BarrierTable<Integer, myPair> barrier = new BarrierTable<>();
        exeStack.push(myController.statement);

        PrgState programState = new PrgState(exeStack, dict, list, myController.statement, fileTable, heap, barrier, GenIDFork.getID());

        Repository repo = new Repository();
        repo.addPrg(programState);
        Controller c = new Controller(repo);
        ctrl=c;

        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.fileTableIdentifier.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fileTableName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        this.symTableVarName.setCellValueFactory(new PropertyValueFactory<>("varName"));
        this.symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));


        setNoPrgState();
        setPrgStateIdentifiers();
        prgStateIdentifiers.getSelectionModel().select(0);
        setExeStack();
    }



    private void setNoPrgState(){
        Integer nr = ctrl.noPrgStates();
        noPrgStateTextField.setText(String.valueOf(nr));
    }

    private void setPrgStateIdentifiers(){
        prgStateIdentifiers.setItems(ctrl.getPrgStatesID());
    }

    PrgState getCurrentProgramState(){
        int index = prgStateIdentifiers.getSelectionModel().getSelectedIndex();
        if(index == -1)
            index = 0;
        return ctrl.getPrgStateByIndex(index);
    }

    private void setExeStack(){

        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();


        for(IStmt i : programState.getExeStack().getStack())
            list.add(""+i);

        exeStack.setItems(list);
    }

    private void setHeapTable(){

        ObservableList<HeapTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(Integer key: programState.getHeap().getAll())
            list.add(new HeapTableView(key, programState.getHeap().get(key)));

        heapTable.setItems(list);
    }

    private void setFileTable(){

        ObservableList<FileTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(Integer key: programState.getFileTable().getAllKeys())
            list.add(new FileTableView(key, programState.getFileTable().get(key).getFileName()));

        fileTable.setItems(list);
    }

    private void setSymTable(){

        ObservableList<SymTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(String key: programState.getSymTable().getAllKeys())
            list.add(new SymTableView(key, programState.getSymTable().getValue(key)));

        symTable.setItems(list);
    }

    private void setOutList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();   // they all share the same outList

        for(Integer i: programState.getOut().getList())
            list.add(""+i);

        outList.setItems(list);
    }

    private void setBarrierTable() {
        ObservableList<BarrierTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(Integer key: programState.getBarrierTable().getAll()){

            String l = new String();

            for(Integer s : programState.getBarrierTable().get(key).getFirst())
                l += " "+s;

            list.add(new BarrierTableView(key, programState.getBarrierTable().get(key).getSecond(), l) );
        }

        barrierTable.setItems(list);

    }

    private void setAll(){
        setNoPrgState();
        setPrgStateIdentifiers();
        setExeStack();
        setHeapTable();
        setFileTable();
        setSymTable();
        setOutList();
        setBarrierTable();
    }

    public void executeOneStep(ActionEvent ae){

        try {
            if (ctrl.oneStepGUI()) {
                setAll();
            } else {
                setNoPrgState();
                setPrgStateIdentifiers();
            }
        }
        catch (RuntimeException e){

            Node source = (Node) ae.getSource();
            Stage theStage = (Stage) source.getScene().getWindow();
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
            theStage.close();
        }
    }

    public void mouseClickPrgStateIdentifiers() {

        if (ctrl.noPrgStates() > 0)
            setAll();
    }
}
