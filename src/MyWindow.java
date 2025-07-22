import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyWindow {
    private static ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Set> setList = new ArrayList<>();
    private static CategoryView vestuarioView;
    private static CategoryView combinacoesView;
    private static JLabel currentlyClickedLabel;

    public static void main(String[] args) {
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.okButtonText", "OK");

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
            JButton novoEmprestimoButton = new JButton("Emprestar item");

            // mesmo alignment pros 2 botoes
            novoItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            novaLavagemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            novoEmprestimoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            vestuarioActionPanel.add(novoItemButton);
            vestuarioActionPanel.add(Box.createRigidArea(new Dimension(0, 5))); // gap entre os botoes
            vestuarioActionPanel.add(novaLavagemButton);
            vestuarioActionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            vestuarioActionPanel.add(novoEmprestimoButton);

            // criando o view dos itens do vestuario
            vestuarioView = new CategoryView("Vestuário", vestuarioActionPanel);

            novoItemButton.addActionListener(e -> {
                NewItemPanel newItemPanel = new NewItemPanel(null, false);
                // pegando a info do novo item no popup
                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newItemPanel,
                        "Adicionar Novo Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if(result == JOptionPane.OK_OPTION) {
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

                    vestuarioView.newLabel(itemView);
                    vestuarioView.filterItems();
                }
            });

            novaLavagemButton.addActionListener(e -> {
                NewEmprestimoOrLaundryPanel newEmprestimoOrLaundryPanel = new NewEmprestimoOrLaundryPanel(itemList, frame, false);

                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newEmprestimoOrLaundryPanel,
                        "Lavar itens:",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if(result == JOptionPane.OK_OPTION) {

                }
            });

            novoEmprestimoButton.addActionListener(e -> {
                NewEmprestimoOrLaundryPanel newEmprestimoOrLaundryPanel = new NewEmprestimoOrLaundryPanel(itemList, frame, true);

                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newEmprestimoOrLaundryPanel,
                        "Emprestar item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if(result == JOptionPane.OK_OPTION) {
                    for(Item i : newEmprestimoOrLaundryPanel.getSelectedItems()) {
                        i.lendItem();
                    }
                }
            });

            // panel pra guardar o botao das combinações
            JPanel combinacoesActionPanel = new JPanel(new GridBagLayout()); // gridbag pra ficar centrado no meio
            JButton novaCombinacaoButton = new JButton("Nova combinação:");
            combinacoesActionPanel.add(novaCombinacaoButton);

            // cria a view de combinações
            combinacoesView = new CategoryView("Combinações", combinacoesActionPanel);

            novaCombinacaoButton.addActionListener(e -> {
                NewSetPanel newSetPanel = new NewSetPanel(itemList, null, false);
                // pegando a info da nova combinação no popup
                int result = JOptionPane.showConfirmDialog(
                        mainContainer,
                        newSetPanel,
                        "Adicionar Nova combinação",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if(result == JOptionPane.OK_OPTION) {
                    Set newSet = window.onSetCreate(newSetPanel.getNameField(), newSetPanel.getSelectedItems());

                    JLabel setView = window.createSetView(newSet);

                    combinacoesView.newLabel(setView);
                    combinacoesView.filterItems();
                }
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

    // metodo pra criar o set
    public Set onSetCreate(String name, List<Item> items) {
        Set newSet = new Set(name, items);
        setList.add(newSet);
        for(Item i : items) {
            i.setsIn.add(newSet);
        }
        return newSet;
    }

    // metodo pra criar o item
    public Item onItemCreate(String name, String color, String size, String origin, String purchase, String conservation, String imagePath, boolean lending, boolean available) {
        Item newItem = new Item(name, color, size, origin, purchase, conservation, imagePath, lending, available);
        itemList.add(newItem);
        return newItem;
    }

    private JLabel createSetView(Set set) {
        JLabel label = new JLabel(set.getName(), SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(100, 140));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setText(set.getName());

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editSet = new JMenuItem("Editar");
        JMenuItem removeSet = new JMenuItem("Remover");
        JMenuItem usageSet = new JMenuItem("Registrar uso");

        popupMenu.add(editSet);
        popupMenu.add(removeSet);
        popupMenu.add(usageSet);

        editSet.addActionListener(e -> {
            NewSetPanel editSetPanel = new NewSetPanel(itemList, set, true);

            int result = JOptionPane.showConfirmDialog(label, editSetPanel, "Editar combinação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                set.setPiecesBulk(editSetPanel.getSelectedItems());
                label.setText(editSetPanel.getNameField());
            }
        });

        removeSet.addActionListener(e -> {
            if (currentlyClickedLabel != null) {
                int result = JOptionPane.showConfirmDialog(
                        label,
                        "Tem certeza que deseja remover '" + currentlyClickedLabel.getText() + "'?",
                        "Confirmar Remoção",
                        JOptionPane.YES_NO_OPTION
                );

                if(result == JOptionPane.YES_OPTION) {
                    combinacoesView.removeItem(currentlyClickedLabel);
                    currentlyClickedLabel = null;
                    setList.remove(set);
                }
            }
        });

        usageSet.addActionListener(e -> {
            if(!set.getUsages().isEmpty() && set.getUsages().size() >= 5) {
                set.getUsages().removeFirst();
            }
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = today.format(formatter);

            System.out.println(formattedDate); // Prints something like "22/07/2025"
            set.setUsages(formattedDate);
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            public void showPopup(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    currentlyClickedLabel = (JLabel) e.getComponent();
                    // popup na posição do mouse
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        return label;
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

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem removeItem = new JMenuItem("Remover");

        popupMenu.add(editItem);
        popupMenu.add(removeItem);

        editItem.addActionListener(e -> {
            NewItemPanel editItemPanel = new NewItemPanel(item, true);

                int result = JOptionPane.showConfirmDialog(label, editItemPanel, "Editar item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    label.setText(editItemPanel.getItemName());
                    item.setName(editItemPanel.getItemName());
                    item.setColor(editItemPanel.getItemColor());
                    item.setSize(editItemPanel.getItemSize());
                    item.setOrigin_shop(editItemPanel.getItemOrigin());
                    item.setPurchase_date(editItemPanel.getItemPurchase());
                    item.setConservation(editItemPanel.getItemConservation());
                    item.setImage_path(editItemPanel.getItemImagePath());
                    item.setLending_warning(editItemPanel.hasLendingWarning());
                    item.setAvailable(editItemPanel.isAvailable());
                }
        });

        removeItem.addActionListener(e -> {
            if (currentlyClickedLabel != null) {
                int result = JOptionPane.showConfirmDialog(
                        label,
                        "Tem certeza que deseja remover '" + currentlyClickedLabel.getText() + "'?",
                        "Confirmar Remoção",
                        JOptionPane.YES_NO_OPTION
                );

                if(result == JOptionPane.YES_OPTION) {
                    vestuarioView.removeItem(currentlyClickedLabel);
                    currentlyClickedLabel = null;
                    itemList.remove(item);
                }
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            public void showPopup(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    currentlyClickedLabel = (JLabel) e.getComponent();
                    // popup na posição do mouse
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        return label;
    }
}