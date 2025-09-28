import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
public class BasicEnemy extends JPanel{
    private static int enemyNumber;
    private BufferedImage enemy;
    private int Health;
    private int imageX, imageY;
    private Random random = new Random();
    private static Timer internalTimer;
    private int speed = 3;
    public BasicEnemy(){
        try{
            enemy = ImageIO.read(getClass().getResource("enemy/damage-orb.png"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        Health=2;
        enemyNumber+=1;
    }
    public void move(int targetX, int targetY){
        if(imageX<targetX){
            imageX+=speed;
        }else if (imageX>targetX){
            imageX -= speed;
        }
        if(imageY<targetY){
            imageY+=speed;
        }else if (imageX>targetY){
            imageY -= speed;
        }
    }
    public void takeDamage(int damage){
        Health = Health - damage;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(ImageEntity entity : images){
            g.drawImage(entity.getImage(),entity.getX(),entity.getY(),null);
        }
    }
    public boolean isDead(){
        if(Health<=0){
            return true;
        }
        return false;
    }
    public int getX(){
        return imageX;
    }
    public int getY(){
        return imageY;
    }
    public BufferedImage getImage(){
        return enemy;
    }
    public int getEnemyNumber(){
        return enemyNumber;
    }
}
