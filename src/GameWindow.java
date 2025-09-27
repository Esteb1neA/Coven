import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static void main(String[]args){
        JFrame frame = new JFrame("Coven");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        ImageIcon spriteIcon = new ImageIcon("pixil-frame-0.png");
        JLabel spriteLabel = new JLabel(spriteIcon);
        frame.add(spriteLabel);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
