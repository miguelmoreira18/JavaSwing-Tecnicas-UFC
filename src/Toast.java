import javax.swing.*;
import java.awt.*;

public class Toast {

    public static void show(JFrame parent, String message, int durationMs) {
        JWindow toastWindow = new JWindow(parent);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(messageLabel);

        toastWindow.add(panel);
        toastWindow.pack();

        // calcuando a posição pra ficar no meio em baixo da janela
        int x = parent.getX() + (parent.getWidth() - toastWindow.getWidth()) / 2;
        int y = parent.getY() + parent.getHeight() - toastWindow.getHeight() - 50;
        toastWindow.setLocation(x, y);

        toastWindow.setVisible(true);

        Timer timer = new Timer(durationMs, e -> {
            toastWindow.setVisible(false);
            toastWindow.dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
}