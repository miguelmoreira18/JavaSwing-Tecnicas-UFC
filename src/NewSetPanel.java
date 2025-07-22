import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewSetPanel extends JPanel {
    private JList<Item> itemsList;
    private JTextField nameField;

    public NewSetPanel(ArrayList<Item> items, Set set, boolean edit) {
        this.setLayout(new BorderLayout());

        DefaultListModel<Item> listModel = new DefaultListModel<>();
        for (Item item : items) {
            if(edit) {
                if(!set.containsItem(item)) {
                    if(item.isAvailable()) listModel.addElement(item);
                }
            } else {
                if(item.isAvailable()) listModel.addElement(item);
            }
        }

        itemsList = new JList<>(listModel);

        itemsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        itemsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // essa checagem faz com q o código rode só uma vez quando a seleção é final
                if (!e.getValueIsAdjusting()) {
                    Item selectedItem = itemsList.getSelectedValue();

                    // garante que algo foi selecionado
                    if (selectedItem != null) {
                        System.out.println("You selected: " + selectedItem.getName());
                    }
                }
            }
        });

        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameInputPanel.add(new JLabel("Nome:"));
        nameField = new JTextField(20);
        if(edit) nameField.setText(set.getName());
        nameInputPanel.add(nameField);
        add(nameInputPanel, BorderLayout.NORTH);
        add(new JScrollPane(itemsList), BorderLayout.CENTER);
    }

    public List<Item> getSelectedItems() {
        return itemsList.getSelectedValuesList();
    }

    public String getNameField() {
        return nameField.getText();
    }
}