package view;

import controller.Controller;
import exception.MyException;

public class RunExample extends Command {
    private Controller ctrl;

    RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctrl = ctr;
    }

    @Override
    public void execute() {
        try {
            ctrl.allStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}

