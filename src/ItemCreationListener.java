public interface ItemCreationListener {
    Item onItemCreate(String name, String color, String size, String origin, String purchase, String conservation, String imagePath, boolean lending, boolean available);
}
