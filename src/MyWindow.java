import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MyWindow {
    private ItemCreationListener creationListener;
    private ArrayList<Item> itemList = new ArrayList<>();

    public static void main(String[] args) {
        MyWindow window = new MyWindow();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestor de vestuário");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainContainer = new JPanel();
            mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

            // panel pra guardar os botoes do vestuario
            JPanel vestuarioActionPanel = new JPanel();
            // boxLayout pra stackar vertical
            vestuarioActionPanel.setLayout(new BoxLayout(vestuarioActionPanel, BoxLayout.Y_AXIS));
            vestuarioActionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding

            JButton novoItemButton = new JButton("Novo item");
            JButton novaLavagemButton = new JButton("Nova lavagem");

            // mesmo alignment pros 2 botoes
            novoItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            novaLavagemButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            vestuarioActionPanel.add(novoItemButton);
            vestuarioActionPanel.add(Box.createRigidArea(new Dimension(0, 5))); // gap entre os botoes
            vestuarioActionPanel.add(novaLavagemButton);

            // criando o view dos itens do vestuario
            CategoryView vestuarioView = new CategoryView("Vestuário", vestuarioActionPanel);

            novoItemButton.addActionListener(e -> {
                NewItemPanel newItemPanel = new NewItemPanel();
                // pegando a info do novo item no popup
                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newItemPanel,
                        "Adicionar Novo Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                Item newItem = window.onItemCreate(
                        newItemPanel.getItemName(),
                        newItemPanel.getItemColor(),
                        newItemPanel.getItemSize(),
                        newItemPanel.getItemOrigin(),
                        newItemPanel.getItemPurchase(),
                        newItemPanel.getItemConservation(),
                        newItemPanel.getItemImagePath(),
                        newItemPanel.hasLendingWarning(),
                        newItemPanel.isAvailable()
                );
                // passa o novo item pro metodo que vai criar uma label pra ele
                JLabel itemView = window.createItemView(newItem);

                window.itemList.add(newItem);

                vestuarioView.setNewItem(newItem);
                vestuarioView.newItemLabel(itemView);
                vestuarioView.filterItems();
            });

            // panel pra guardar o botao das combinações
            JPanel combinacoesActionPanel = new JPanel(new GridBagLayout()); // gridbag pra ficar centrado no meio
            JButton novaCombinacaoButton = new JButton("Nova combinação:");
            combinacoesActionPanel.add(novaCombinacaoButton);

            // cria a view de combinações
            CategoryView combinacoesView = new CategoryView("Combinações", combinacoesActionPanel);

            novaCombinacaoButton.addActionListener(e -> {
                NewSetPanel newSetPanel = new NewSetPanel(window.itemList);
                // pegando a info da nova combinação no popup
                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newSetPanel,
                        "Adicionar Nova combinação",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
            });

            // bota as 2 views no container principal
            mainContainer.add(vestuarioView);
            mainContainer.add(Box.createRigidArea(new Dimension(0, 10))); // gap entre as views
            mainContainer.add(combinacoesView);
            mainContainer.add(Box.createVerticalGlue());

            JScrollPane mainScrollPane = new JScrollPane(mainContainer);
            frame.add(mainScrollPane);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // metodo pra criar o item
    public Item onItemCreate(String name, String color, String size, String origin, String purchase, String conservation, String imagePath, boolean lending, boolean available) {
        Item newItem = new Item(name, color, size, origin, purchase, conservation, imagePath, lending, available);
        itemList.add(newItem);

        System.out.println("New item created and added to the list in Main!");
        System.out.println("Current item list size: " + itemList.size());
        System.out.println(newItem.getImage_path());

        return newItem;
    }

    private JLabel createItemView(Item item) {
        JLabel label = new JLabel();
        // configura a label pra imagem e texto
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(100, 140)); // A consistent size for all items

        String imagePath = item.getImage_path();

        // se tem uma string pro path da imagem, mostra a imagem
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            System.out.println("Attempting to load image from: " + imagePath); // DEBUG
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);

                // checa se a imagem foi carregada
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image resizedImage = icon.getImage().getScaledInstance(100, 110, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(resizedImage));
                } else {
                    System.err.println("ERROR: Failed to load the image. The file might be corrupted.");
                }
            } else {
                System.err.println("ERROR: File not found at path: " + imagePath);
            }
        }

        // sempre mostra o nome do item
        label.setText(item.getName());
        label.setToolTipText(item.getName()); // mostra o nome completo do item quando passa o mouse por cima

        return label;
    }
}