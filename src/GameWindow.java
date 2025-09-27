import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameWindow {
    public GameWindow(){
        initComponents();
    }
    public static void main(String[]args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new GameWindow();
            }
        });
    }
    private void initComponents(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage img = null;
        try{
            img = ImageIO.read(getClass().getResource("player/pixil-frame-0.png"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        JLabel label = new JLabel(new ImageIcon(img));
        frame.add(label);
        frame.setVisible(true);
    }
}
