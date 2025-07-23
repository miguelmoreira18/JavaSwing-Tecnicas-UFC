import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ApplicationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Set> setList = new ArrayList<>();
    private Item mostUsedItem;
    private Set mostUsedSet;

    public ArrayList<Item> getItemList() { return itemList; }
    public void setItemList(ArrayList<Item> itemList) { this.itemList = itemList; }

    public ArrayList<Set> getSetList() { return setList; }
    public void setSetList(ArrayList<Set> setList) { this.setList = setList; }

    public Item getMostUsedItem() { return mostUsedItem; }
    public void setMostUsedItem(Item mostUsedItem) { this.mostUsedItem = mostUsedItem; }

    public Set getMostUsedSet() { return mostUsedSet; }
    public void setMostUsedSet(Set mostUsedSet) { this.mostUsedSet = mostUsedSet; }
}