import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class CategoryView extends JPanel {
    private final JPanel itemsScrollPaneContent;
    private final JTextField searchField;
    private ArrayList<JButton> editButtons;

    public CategoryView(String title, JComponent actionPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder(title));

        // vest search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel(title + ":"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchPanel.getPreferredSize().height));

        // items display
        JPanel contentAreaPanel = new JPanel(new BorderLayout());
        contentAreaPanel.setPreferredSize(new Dimension(800, 150));

        // scrollable display
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsScrollPaneContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        itemsScrollPaneContent.setBackground(Color.LIGHT_GRAY);
        JScrollPane itemsScrollPane = new JScrollPane(itemsScrollPaneContent);
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        itemsPanel.add(itemsScrollPane, BorderLayout.CENTER);

        // montando o conteudo
        // adiciona o actionPanel (botao)
        actionPanel.setPreferredSize(new Dimension(160, 0)); // garante a width
        contentAreaPanel.add(actionPanel, BorderLayout.WEST);
        contentAreaPanel.add(itemsPanel, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }
        });

        // adicionando tudo pro this Panel (main panel)
        this.add(searchPanel);
        this.add(contentAreaPanel);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void filterItems() {
        String query = searchField.getText().toLowerCase();

        for(Component component: itemsScrollPaneContent.getComponents()) {
            if(component instanceof JLabel itemLabel) {
                itemLabel.setVisible(itemLabel.getText().toLowerCase().contains(query));
            }
        }
    }

    public void removeItem(JLabel label) {
        itemsScrollPaneContent.remove(label);
    }

    public void newLabel(JLabel label) {
        itemsScrollPaneContent.add(label);
        itemsScrollPaneContent.revalidate();
        itemsScrollPaneContent.repaint();
    }
}