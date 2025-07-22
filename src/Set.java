import java.util.ArrayList;
import java.util.List;

public class Set {
    private String name;
    private List<Item> pieces;
    private ArrayList<String> usages;

    public Set(String name, List<Item> items) {
        this.name = name;
        pieces = items;
    }

    public boolean containsItem(Item item) {
        for(Item i : pieces) {
            if(i.equals(item)) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getPieces() {
        return pieces;
    }

    public void setPieces(Item item) {
        pieces.add(item);
    }

    public void setPiecesBulk(List<Item> items) {
        pieces.addAll(items);
    }

    public ArrayList<String> getUsages() {
        return usages;
    }

    public void setUsages(String date){
        usages.add(date);
    }
}