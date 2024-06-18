import unRunAble.BackEnd;
import unRunAble.FrontEnd;

public class Main {
    public static void main(String[] args) {

        unRunAble.InterFaces.FrontEndInterface frontEnd = new FrontEnd();

        BackEnd backEnd = new BackEnd((FrontEnd) frontEnd);

        frontEnd.setBackEnd(backEnd);
        backEnd.start();

        frontEnd.setVisible();
    }
}
