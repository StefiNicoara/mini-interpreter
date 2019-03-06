package domain.stmt;

public class GenIDFork {
    private static int number=1;
    public static  int getID(){
        return number++;
    }
}