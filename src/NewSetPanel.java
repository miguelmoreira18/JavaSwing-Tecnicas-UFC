import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewSetPanel extends JPanel {
    // 1. Make the JList a class field to access it in the listener
    private JList<Item> itemsList;
    private JTextField nameField;

    public NewSetPanel(ArrayList<Item> items) {
        // Use a BorderLayout to better position the list
        this.setLayout(new BorderLayout());

        DefaultListModel<Item> listModel = new DefaultListModel<>();
        for (Item item : items) {
            listModel.addElement(item);
        }

        // Initialize the class field
        itemsList = new JList<>(listModel);

        itemsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // It's good practice to set a custom renderer for your Item objects
        // itemsList.setCellRenderer(new ItemRenderer());

        // 2. Add the ListSelectionListener
        itemsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // This check ensures the code runs only once when the selection is final
                if (!e.getValueIsAdjusting()) {
                    Item selectedItem = itemsList.getSelectedValue();

                    // Make sure something is actually selected
                    if (selectedItem != null) {
                        // Perform an action with the selected item
                        System.out.println("You selected: " + selectedItem.getName());
                    }
                }
            }
        });

        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameInputPanel.add(new JLabel("Nome:"));
        nameField = new JTextField(20);
        nameInputPanel.add(nameField);
        add(nameInputPanel, BorderLayout.NORTH);
        add(new JScrollPane(itemsList), BorderLayout.CENTER);
    }

    /**
     * Returns a List of all the items currently selected in the JList.
     * @return A List containing the selected Item objects.
     */
    public List<Item> getSelectedItems() {
        return itemsList.getSelectedValuesList();
    }

    public String getNameField() {
        return nameField.getText();
    }
}