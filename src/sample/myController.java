package sample;

import domain.exp.ArithExp;
import domain.exp.ConstExp;
import domain.exp.Exp;
import domain.exp.VarExp;
import domain.fileHandling.*;
import domain.heapStatements.HeapAllocation;
import domain.heapStatements.HeapReading;
import domain.heapStatements.HeapWriting;
import domain.stmt.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class myController {
    @FXML
    private ListView<String> theListView;

    @FXML
    private Button theButton;

    public static IStmt statement;
    public List<IStmt> StmtList = new ArrayList<IStmt>();

    @FXML
    public void initialize() {

        IStmt a = new AssignStmt("a", new ArithExp('+', new ConstExp(2), new ArithExp('*', new ConstExp(3), new ConstExp(5))));
        IStmt b = new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ConstExp(1))), new PrintStmt(new VarExp("b")));
        IStmt ex1 = new CompStmt(a, b);

        IStmt aa = new AssignStmt("a", new ArithExp('-', new ConstExp(2), new ConstExp(2)));
        IStmt thenS = new AssignStmt("v", new ConstExp(2));
        IStmt elseS = new AssignStmt("v", new ConstExp(3));
        IStmt decision = new IfStmt(new VarExp("a"), thenS, elseS);
        IStmt ex2 = new CompStmt(aa, new CompStmt(decision, new PrintStmt(new VarExp("v"))));

        IStmt a1 = new AssignStmt("v", new ConstExp(6));
        IStmt a2 = new WhileStatement(new ArithExp('-', new VarExp("v"), new ConstExp(4)),
                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))));
        IStmt a3 = new PrintStmt(new VarExp("v"));
        IStmt ex3 = new CompStmt(new CompStmt(a1, a2), a3);

        IStmt q1 = new AssignStmt("v", new ConstExp(10));
        IStmt q2 = new HeapAllocation("a", new ConstExp(22));
        IStmt q3 = new HeapWriting("a", new ConstExp(30));
        IStmt q4 = new AssignStmt("v", new ConstExp(32));
        IStmt q5 = new PrintStmt(new VarExp("v"));
        IStmt q6 = new PrintStmt(new HeapReading("a"));
        IStmt q7 = new CompStmt(new CompStmt(q3, q4), new CompStmt(q5, q6));
        IStmt q8 = new ForkStmt(q7);
        IStmt q9 = new PrintStmt(new VarExp("v"));
        IStmt q10 = new PrintStmt(new HeapReading("a"));
        IStmt c1 = new CompStmt(q1, q2);
        IStmt c2 = new CompStmt(q8, new CompStmt(q9, q10));
        IStmt ex4 = new CompStmt(c1, c2);

        IStmt Ts1 = new OpenRFile("var_f", "text.txt");
        IStmt Ts2 = new ReadFile(new VarExp("var_f"), "var_c");
        IStmt TthenS1 = new ReadFile(new VarExp("var_f"), "var_c");
        IStmt TthenS2 = new PrintStmt(new VarExp("var_c"));
        IStmt TthenS = new CompStmt(TthenS1, TthenS2);
        IStmt TelseS = new PrintStmt(new ConstExp(0));
        IStmt Ts3 = new IfStmt(new VarExp("var_c"), TthenS, TelseS);
        IStmt Ts4 = new CloseRFile(new VarExp("var_f"));
        IStmt Ts5 = new CompStmt(Ts1, Ts2);
        IStmt Ts6 = new CompStmt(Ts3, Ts4);
        IStmt ex5 = new CompStmt(Ts5, Ts6);


        //a=1;b=2;c=5;
        //switch(a*10)
        //(case (b*c) print(a);print(b))
        //(case (10) print(100);print(200))
        //(default print(300));
        // print(300)

        IStmt assign_a = new AssignStmt("a", new ConstExp(1)); // a=1
        IStmt assign_b = new AssignStmt("b", new ConstExp(2)); // b=2
        IStmt assign_c = new AssignStmt("c", new ConstExp(5)); // c=5

        Exp exp = new ArithExp('*', new VarExp("a"), new ConstExp(10)); // exp = a*10

        Exp exp1 = new ArithExp('*', new VarExp("b"), new VarExp("c")); // exp1 = b*c
        IStmt print_a = new PrintStmt(new VarExp("a"));
        IStmt print_b = new PrintStmt(new VarExp("b"));
        IStmt stmt1 = new CompStmt(print_a, print_b); // stmt1 = print(a);print(b)

        IStmt print_100 = new PrintStmt(new ConstExp(100));
        IStmt print_200 = new PrintStmt(new ConstExp(200));
        IStmt stmt2 = new CompStmt(print_100, print_200); //stmt2 = print(100);print(200)

        IStmt stmt3 = new PrintStmt(new ConstExp(300)); // stmt3 = print(300)

        IStmt stmt4 = new PrintStmt(new ConstExp(300)); // stmt4 = print(300)

        IStmt switchStmt = new SwitchStmt(exp, exp1, stmt1, new ConstExp(10), stmt2, stmt3);
        IStmt assigns = new CompStmt(assign_a, assign_b);
        IStmt allAssigns = new CompStmt(assigns, assign_c);

        IStmt preFin = new CompStmt(allAssigns, switchStmt);
        IStmt fin = new CompStmt(preFin, stmt4);

        IStmt s1 = new HeapAllocation("v1", new ConstExp(2));
        IStmt s2 = new HeapAllocation("v2", new ConstExp(3));
        IStmt s3 = new HeapAllocation("v3", new ConstExp(4));
        IStmt s4 = new NewBarrier("cnt", new HeapReading("v2"));

        IStmt s5 = new Await("cnt");
        IStmt s6 = new PrintStmt(new HeapReading("v1"));
        IStmt s7 = new PrintStmt(new HeapReading("v2"));
        IStmt s8 = new PrintStmt(new HeapReading("v3"));

        IStmt s9 = new HeapWriting("v1", new ArithExp('*', new HeapReading("v1"), new ConstExp(10)));
        IStmt s10 = new HeapWriting("v2", new ArithExp('*', new HeapReading("v2"), new ConstExp(10)));
        IStmt s11 = new HeapWriting("v3", new ArithExp('*', new HeapReading("v3"), new ConstExp(10)));


        IStmt cc1 = new CompStmt(s1, new CompStmt(s2, new CompStmt(s3, s4)));
        IStmt cc2 = new ForkStmt(new CompStmt(s5, new CompStmt(s9, s6)));
        IStmt cc3 = new ForkStmt(new CompStmt(s5, new CompStmt(s10, new CompStmt(s10, s7))));

        IStmt fin2 = new CompStmt(cc1, new CompStmt(cc2, new CompStmt(cc3, new CompStmt(s5, s8))));

        StmtList.add(ex1);
        StmtList.add(ex2);
        StmtList.add(ex3);
        StmtList.add(ex4);
        StmtList.add(ex5);
        StmtList.add(fin);
        StmtList.add(fin2);

        ObservableList<String> list = FXCollections.observableArrayList();
        for (IStmt i : StmtList)
            list.add("" + i);

        theListView.setItems(list);
        theListView.getSelectionModel().selectIndices(0);
    }

    @FXML
    public void buttonRun() throws IOException {

        statement = StmtList.get(theListView.getSelectionModel().getSelectedIndex());

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("runProgram.fxml"));

        stage.setTitle("Running Program");
        stage.setScene(new Scene(root, 800, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
