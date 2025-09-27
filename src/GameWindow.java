import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GameWindow extends JPanel implements KeyListener, ActionListener {
    private static BufferedImage player;
    private int imageX,imageY=400;
    private final int MOVE_SPEED = 10;
    private Set<Integer> pressedKeys = new HashSet<>();
    private static Timer gameTimer;
    private static Health playerHealth = new Health();
    private static BufferedImage damageOrb;
    public GameWindow(){

        try{
            player = ImageIO.read(getClass().getResource("player/pixil-frame-0.png"));
            damageOrb = ImageIO.read(getClass().getResource("enemy/damage-orb.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setFocusable(true);
        addKeyListener(this);
        gameTimer = new Timer(20,this);
        gameTimer.start();
    }
    public static void main(String[]args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GameWindow panel = new GameWindow();
                frame.add(panel);
                frame.setVisible(true);
                Rectangle boundsPanel = panel.getBounds();
                /*if(boundsPanel.intersects(enemyDamage)){
                    playerHealth.loseHealth(enemyDamage.damage());
                }*/
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(player != null){
            g.drawImage(player,imageX, imageY, this);
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        pressedKeys.add(e.getKeyCode());
    }
    @Override
    public void keyReleased(KeyEvent e){
        pressedKeys.remove(e.getKeyCode());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        int dx=0;
        int dy=0;
        if (pressedKeys.contains(KeyEvent.VK_LEFT)&&imageX>0){
            dx -= MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)&&imageX<1650) {
            dx += MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)&&imageY>0){
            dy -= MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)&&imageY<1000) {
            dy += MOVE_SPEED;
        }
        if(dx!=0&&dy!=0){
            double magnitude = Math.sqrt(dx*dx+dy*dy);
            dx=(int)(dx/magnitude*MOVE_SPEED);
            dy=(int)(dy/magnitude*MOVE_SPEED);
        }
        imageX+=dx;
        imageY+=dy;
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
}
