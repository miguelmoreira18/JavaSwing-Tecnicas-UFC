import javax.swing.*;
import java.awt.*;

/**
 * A utility class to show a temporary "toast" message on a parent window.
 */
public class Toast {

    /**
     * Shows a toast message at the bottom-center of the parent frame.
     * @param parent The parent JFrame.
     * @param message The message to display.
     * @param durationMs The duration in milliseconds for the toast to be visible.
     */
    public static void show(JFrame parent, String message, int durationMs) {
        // Create a JWindow for the toast
        JWindow toastWindow = new JWindow(parent);

        // Create a panel for styling
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 180)); // Translucent black
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Create the message label
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(messageLabel);

        toastWindow.add(panel);
        toastWindow.pack();

        // Calculate the position to be at the bottom-center of the parent frame
        int x = parent.getX() + (parent.getWidth() - toastWindow.getWidth()) / 2;
        int y = parent.getY() + parent.getHeight() - toastWindow.getHeight() - 50; // 50px from bottom
        toastWindow.setLocation(x, y);

        // Make the toast visible
        toastWindow.setVisible(true);

        // Create a Swing Timer to hide the toast after the specified duration
        Timer timer = new Timer(durationMs, e -> {
            toastWindow.setVisible(false);
            toastWindow.dispose(); // Free up resources
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }
}