import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewEmprestimoOrLaundryPanel extends JPanel {
    private JList<Item> itemsList;

    public NewEmprestimoOrLaundryPanel(ArrayList<Item> items, JFrame frame, boolean emprestimo) {
        this.setLayout(new BorderLayout());

        DefaultListModel<Item> listModel = new DefaultListModel<>();
        for (Item item : items) {
            if (item.isAvailable()) listModel.addElement(item);
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
                        if(emprestimo && !selectedItem.setsIn.isEmpty()) {
                            Toast.show(frame, "Esse item está numa combinação", 3000);
                        } else if(!emprestimo && !selectedItem.isAvailable()) {
                            Toast.show(frame, "Esse item está emprestado", 3000);
                        }
                    }
                }
            }
        });

        add(new JScrollPane(itemsList), BorderLayout.CENTER);
    }

    public List<Item> getSelectedItems() {
        return itemsList.getSelectedValuesList();
    }
}
