import java.util.ArrayList;

public class Item extends AbstractItem {
    public Item(String name, String color, String size, String origin_shop, String purchase_date, String conservation, String image_path, boolean lending_warning, boolean available){
        this.name = name;
        this.color = color;
        this.size = size;
        this.origin_shop = origin_shop;
        this.purchase_date = purchase_date;
        this.conservation = conservation;
        this.image_path = image_path;
        this.lending_warning = lending_warning;
        this.available = available;
        this.setsIn = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public void lendItem() {
        this.available = false;
    }

    public int daySinceLending() {
        return 1;
    }
}