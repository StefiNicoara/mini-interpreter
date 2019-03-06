package view;

import controller.Controller;
import domain.PrgState;
import domain.adt.*;
import domain.booleanExpressions.booleanExpresion;
import domain.exp.ArithExp;
import domain.exp.ConstExp;
import domain.exp.VarExp;
import domain.fileHandling.*;
import domain.heapStatements.HeapAllocation;
import domain.heapStatements.HeapReading;
import domain.heapStatements.HeapWriting;
import domain.stmt.*;
import repository.Repository;

public class View {
    //private static Repository myRepository = new Repository();
    //private static Controller myController = new Controller(myRepository);

    public static void main(String[] args) {
//        IStmt originalProgram = new IfStmt(new ConstExp(10), new CompStmt(new AssignStmt("v", new ConstExp(5)), new PrintStmt(new ArithExp('/', new VarExp("v"), new ConstExp(3)))), new PrintStmt(new ConstExp(100)));
//        IStmt originalProgram = new CompStmt( new PrintStmt(new VarExp("v1")), new AssignStmt("v", new ConstExp(10)));
//        MyIStack<IStmt> exeStack = new MyStack<>();
//        MyIDictionary<String, Integer> symTable = new MyDictionary<>();
//        MyIList<Integer> out = new MyList<>();
//        FileTable<Integer, FileData> fileTable = new FileTable<>();
//        PrgState myPrgState = new PrgState(exeStack, symTable, out, originalProgram, fileTable);
//        myController.addProgram(myPrgState);
//        myController.allStep();


//        IStmt ifStmt = new IfStmt(new VarExp("var_c"), new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))), new PrintStmt(new ConstExp(0)));
//        IStmt opStmt = new OpenRFile("var_f", "text.txt");
//        IStmt readStmt = new ReadFile(new VarExp("var_f"), "var_c");
//        IStmt closeFile = new CloseRFile(new VarExp("var_f"));
//        IStmt originalProgram = new CompStmt(opStmt, new CompStmt(readStmt, new CompStmt(new PrintStmt(new VarExp("var_c")), new CompStmt(ifStmt, closeFile))));

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
//        IStmt q = new AssignStmt("v", new ConstExp(10));
//        IStmt q1 = new HeapAllocation("v",new ConstExp(20));
//        IStmt q2 = new HeapAllocation("a", new ConstExp(22));
//        IStmt q3 = new HeapWriting("a", new ConstExp(30));
//        IStmt q4 = new PrintStmt(new VarExp("a"));
//        IStmt q5 = new PrintStmt(new HeapReading("a"));
//        IStmt q6 = new AssignStmt("a", new ConstExp(0));
//
//
//        IStmt d1 = new CompStmt(q, q1);
//        IStmt d2 = new CompStmt(q2, q3);
//        IStmt d3 = new CompStmt(q4, q5);

//        IStmt originalProgram = new CompStmt(new CompStmt(d1,d2), new CompStmt(d3,q6));

        //v=6; (while (v-4) print(v);v=v-1);print(v<=1)
//        IStmt assignStmt =  new AssignStmt("v", new ConstExp(6));
//        IStmt whileStmt = new WhileStatement(new ArithExp('-',new VarExp("v"), new ConstExp(4) ),
//                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-',new VarExp("v"), new ConstExp(1) ))));
//
//        IStmt printStmt = new PrintStmt(new booleanExpresion("<=", new VarExp("v"), new ConstExp(1)));
//
//        IStmt originalProgram = new CompStmt(new CompStmt(assignStmt,whileStmt), printStmt);
//
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Integer> symTable = new MyDictionary<>();
        MyIList<Integer> list = new  MyList<>();
        FileTable<Integer, FileData> fileTable = new FileTable<>();
        MyIHeap<Integer, Integer> heap = new MyHeap<>();
        BarrierTable<Integer, myPair> barrier = new BarrierTable<>();

        /*
          Example:
          v=10;new(a,22);
          fork(wH(a,30);v=32;print(v);print(rH(a)));
          print(v);print(rH(a))
        */

        IStmt originalProgram = new CompStmt(new AssignStmt("v", new ConstExp(10)), new CompStmt(new HeapAllocation("a",new ConstExp(22)), new CompStmt(new ForkStmt(new CompStmt(new HeapWriting("a",new ConstExp(30)),new CompStmt(new AssignStmt("v",new ConstExp(32)), new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new HeapReading("a")))))),new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReading("a"))))));

        exeStack.push(originalProgram);

        PrgState state = new PrgState(exeStack, symTable, list, null, fileTable, heap, barrier, GenIDFork.getID());

        Repository repo = new Repository();
        repo.addPrg(state);
        Controller ctrl = new Controller(repo);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",  originalProgram.toString(), ctrl));
        menu.show();

    }
}
