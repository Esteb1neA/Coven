import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameWindow extends JPanel implements KeyListener {
    private static BufferedImage img;
    private int imageX,imageY;
    private final int MOVE_SPEED = 5;
    public GameWindow(){

        try{
            img = ImageIO.read(getClass().getResource("player/pixil-frame-0.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageX=50;
        imageY=50;
        setFocusable(true);
        addKeyListener(this);
    }
    public static void main(String[]args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GameWindow panel = new GameWindow();
                frame.add(panel);
                frame.setVisible(true);
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(img!= null){
            g.drawImage(img,imageX, imageY, this);
        }
    }
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT){
            imageX -= MOVE_SPEED;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            imageX += MOVE_SPEED;
        } else if (keyCode == KeyEvent.VK_UP){
            imageY -= MOVE_SPEED;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            imageY += MOVE_SPEED;
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e){

    }
    @Override
    public void keyTyped(KeyEvent e){

    }
}
