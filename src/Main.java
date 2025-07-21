import java.util.ArrayList;

public class Main {
    private ArrayList<Item> itemList = new ArrayList<>();

    public static void main(String[] args) {
        // criando uma instancia do Main pra passar pro setter do listener
        Main appController = new Main();
        appController.start();
    }

    public void start() {
        //MyWindow window = new MyWindow();
        //window.setItemCreationListener(this);
    }
}
