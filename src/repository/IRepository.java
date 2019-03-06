package repository;

import domain.PrgState;
import exception.FileException;

import java.util.List;

public interface IRepository {
    void addPrg(PrgState newPrg);

    public void logPrgStateExec(PrgState state) throws FileException;

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> list);
}
