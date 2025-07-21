import java.util.ArrayList;

public class Set {
    private ArrayList<Item> pieces;
    private ArrayList<String> usages;

    public ArrayList<Item> getPieces() {
        return pieces;
    }

    public void setPieces(Item item) {
        pieces.add(item);
    }

    public ArrayList<String> getUsages() {
        return usages;
    }

    public void setUsages(String date){
        usages.add(date);
    }
}