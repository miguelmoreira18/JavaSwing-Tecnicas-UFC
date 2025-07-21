import javax.swing.*;
import java.awt.*;

class NewItemPanel extends JPanel {
    private JTextField nameField;
    private JTextField colorField;
    private JTextField sizeField;
    private JTextField originField;
    private JTextField purchaseField;
    private JTextField conservationField;
    private JTextField imagePathField;
    private JRadioButton lendingYesButton, lendingNoButton;
    private JRadioButton availableYesButton, availableNoButton;

    public NewItemPanel() {
        // grid 8x2
        setLayout(new GridLayout(9, 2, 5, 5)); // rows, cols, hgap, vgap

        nameField = new JTextField();
        add(new JLabel("Nome:"));
        add(nameField);

        colorField = new JTextField();
        add(new JLabel("Cor:"));
        add(colorField);

        sizeField = new JTextField();
        add(new JLabel("Tamanho:"));
        add(sizeField);

        originField = new JTextField();
        add(new JLabel("Loja da compra:"));
        add(originField);

        purchaseField = new JTextField();
        add(new JLabel("Data de compra:"));
        add(purchaseField);

        conservationField = new JTextField();
        add(new JLabel("Conservação:"));
        add(conservationField);

        // Botões de aviso de empréstimo
        lendingYesButton = new JRadioButton("Sim");
        lendingNoButton = new JRadioButton("Não");
        lendingYesButton.setSelected(true); // seleção padrão
        ButtonGroup lendingGroup = new ButtonGroup();
        lendingGroup.add(lendingYesButton);
        lendingGroup.add(lendingNoButton);
        add(new JLabel("Aviso de Empréstimo:"));
        // usando um panel pequeno pra agrupar os botoes na horizontal
        JPanel lendingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lendingPanel.add(lendingYesButton);
        lendingPanel.add(lendingNoButton);
        add(lendingPanel);

        // Botões de disponível
        availableYesButton = new JRadioButton("Sim");
        availableNoButton = new JRadioButton("Não");
        availableYesButton.setSelected(true); // seleção padrão
        ButtonGroup availableGroup = new ButtonGroup();
        availableGroup.add(availableYesButton);
        availableGroup.add(availableNoButton);
        add(new JLabel("Disponível:"));
        // usando um panel pra agrupar os botões na horizontal
        JPanel availablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        availablePanel.add(availableYesButton);
        availablePanel.add(availableNoButton);
        add(availablePanel);

        // browser de imagem
        add(new JLabel("Imagem:"));
        JPanel imagePanel = new JPanel(new BorderLayout(5, 0));
        imagePathField = new JTextField();
        imagePathField.setEditable(false);
        JButton browseButton = new JButton("...");
        imagePanel.add(imagePathField, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.EAST);
        add(imagePanel);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(NewItemPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    // getters
    public String getItemName() { return nameField.getText(); }
    public String getItemColor() { return colorField.getText(); }
    public String getItemSize() { return sizeField.getText(); }
    public String getItemOrigin() { return originField.getText(); }
    public String getItemPurchase() { return purchaseField.getText(); }
    public String getItemConservation() { return conservationField.getText(); }
    public String getItemImagePath() { return imagePathField.getText(); }
    public boolean hasLendingWarning() { return lendingYesButton.isSelected(); }
    public boolean isAvailable() { return availableYesButton.isSelected(); }
}