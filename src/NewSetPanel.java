import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class NewSetPanel extends JPanel {
    // 1. Make the JList a class field to access it in the listener
    private JList<String> itemsList;

    public NewSetPanel(ArrayList<Item> items) {
        // Use a BorderLayout to better position the list
        this.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Item item : items) {
            listModel.addElement(item.getName());
        }

        // Initialize the class field
        itemsList = new JList<>(listModel);

        // It's good practice to set a custom renderer for your Item objects
        // itemsList.setCellRenderer(new ItemRenderer());

        // 2. Add the ListSelectionListener
        itemsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // This check ensures the code runs only once when the selection is final
                if (!e.getValueIsAdjusting()) {
                    String selectedItem = itemsList.getSelectedValue();

                    // Make sure something is actually selected
                    if (selectedItem != null) {
                        // Perform an action with the selected item
                        System.out.println("You selected: " + selectedItem);
                    }
                }
            }
        });

        // Add the list (inside a scroll pane) to this panel
        add(new JScrollPane(itemsList), BorderLayout.CENTER);
    }
}