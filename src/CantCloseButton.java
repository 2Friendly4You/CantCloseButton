import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CantCloseButton {
    public static void main(String[] args) {
        Random random = new Random();

        JDialog dialog = new JDialog();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton button = new JButton("Schließen");
        button.addActionListener(e -> System.exit(0));
        button.setBounds(getMouseLocation().x, getMouseLocation().y, 100, 30);
        button.setFocusable(false);
        panel.add(button);


        // get random key event of a letter
        int keyCode = random.nextInt(26) + 65;

        // add listener for key events
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == keyCode) {
                    System.exit(0);
                }
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);
        dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        dialog.setLocationRelativeTo(null);
        dialog.setAlwaysOnTop(true);
        dialog.setContentPane(panel);
        dialog.setVisible(true);

        while (true) {
            if (distance(getMouseLocation().x, getMouseLocation().y, button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() / 2) < 80) {
                // if the mouse is close to the button, the button will be moved in the direction of the mouse
                button.setLocation((int) (button.getX() + Math.cos(direction(getMouseLocation().x, getMouseLocation().y, button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() / 2)) * 10), (int) (button.getY() + Math.sin(direction(getMouseLocation().x, getMouseLocation().y, button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() / 2)) * 10));
                // if button is moved out of the screen, it will be moved back to the center
                if (button.getX() < 0 || button.getX() > Toolkit.getDefaultToolkit().getScreenSize().getWidth() - button.getWidth() || button.getY() < 0 || button.getY() > Toolkit.getDefaultToolkit().getScreenSize().getHeight() - button.getHeight()) {
                    //move to random location near mouse location
                    int x = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - button.getWidth());
                    int y = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - button.getHeight());
                    // while the distance is less than 100, move to random location near mouse location
                    while (distance(x, y, getMouseLocation().x, getMouseLocation().y) > 100) {
                        x = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - button.getWidth());
                        y = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - button.getHeight());
                    }
                    button.setLocation(x, y);
                }
            }
        }
    }

    // function for getting mouse coordinates
    private static Point getMouseLocation() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    // function for calculating distance between x1,y1 and x2,y2
    private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    // function for getting direction between x1,y1 and x2,y2
    private static double direction(int x1, int y1, int x2, int y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
}
