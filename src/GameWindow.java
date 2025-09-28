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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class GameWindow extends JPanel implements KeyListener, ActionListener {
    private static BufferedImage player;
    private static BufferedImage enemyImg;
    private static int imageX;
    private static int imageY;
    private final int MOVE_SPEED = 8;
    private Set<Integer> pressedKeys = new HashSet<>();
    private static Timer gameTimer;
    private Health playerHealth = new Health();
    private ArrayList<BasicEnemy> enemies = new ArrayList<>();
    private Random rand = new Random();
    private boolean gameOver = false;
    private int enemiesDefeated=0;
    private final int WIN_THRESHOLD=20;
    private boolean playerWon=false;
    private long lastPlayerAttackTime = 0;
    private final long PLAYER_ATTACK_COOLDOWN_MS = 500;
    public GameWindow(){

        try{
            player = ImageIO.read(getClass().getResource("player/player-character.png"));
            enemyImg = ImageIO.read(getClass().getResource("enemy/damage-orb.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageX = (1600 - player.getWidth())/2;
        imageY = (1000 - player.getHeight())/2;
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(1600,1000));
        for(int i=0;i<5;i++){
            spawnEnemy();
        }
        gameTimer = new Timer(20,this);
        gameTimer.start();
    }
    private void spawnEnemy(){
        int x;
        int y;
        final int SAFE_DISTANCE = 150;
        boolean tooClose;
        do{
            x = rand.nextInt(1600-37);
            y = rand.nextInt(1000-37);
            int dx = x - imageX;
            int dy = y -imageY;
            double distance = Math.sqrt((dx*dx+dy*dy));
            tooClose = (distance < SAFE_DISTANCE);
        } while(tooClose);
        enemies.add(new BasicEnemy(x,y,2));
    }
    public static void main(String[]args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GameWindow panel = new GameWindow();
                frame.add(panel);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(gameOver || playerWon){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(playerWon ? Color.GREEN : Color.RED);
            String message = playerWon ? "YOU WIN!" : "GAME OVER!";
            g.drawString(message, getWidth() / 2 - 150, getHeight() / 2 - 40);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press R to Restart or ESC to Quit", getWidth() / 2 - 180, getHeight() / 2 + 40);
            return;
        }
        if(player != null){
            g.drawImage(player,imageX, imageY, this);
        }
        for(BasicEnemy enemy:enemies){
            if(enemy.isAlive()){
                g.drawImage(enemyImg,enemy.getX(),enemy.getY(),this);
            }
        }
        g.setColor(Color.RED);
        g.fillRect(10,10,playerHealth.getHealth()*20,20);
        g.setColor(Color.BLACK);
        g.drawRect(10,10,200,20);
        g.drawString("Player HP: " + playerHealth.getHealth(),10,45);
        int barWidth = 150;
        int barHeight = 20;
        int barX = getWidth() - barWidth - 20;
        int barY = getHeight() - barHeight - 20;
        long timeSinceLastAttack = System.currentTimeMillis() - lastPlayerAttackTime;
        float cooldownRatio = Math.min(1f, (float) timeSinceLastAttack / PLAYER_ATTACK_COOLDOWN_MS);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);
        g.setColor(cooldownRatio >= 1f ? Color.GREEN : Color.YELLOW);
        g.fillRect(barX, barY, (int)(barWidth * cooldownRatio), barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Attack Cooldown", barX + 20, barY - 5);
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        pressedKeys.add(key);
        if(gameOver){
            if(key == KeyEvent.VK_R){
                restartGame();
            } else if (key==KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        pressedKeys.remove(e.getKeyCode());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(gameOver)return;
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
        for(BasicEnemy enemy:enemies){
            if(!enemy.isAlive())continue;
            enemy.moveTowards(imageX,imageY);
            if(enemy.collidesWith(imageX,imageY,player.getWidth(),player.getHeight())){
                if(enemy.tryDamagePlayer()) {
                    playerHealth.damage(1);
                    enemy.registerDamage();
                }
            }
        }
        long currentTime = System.currentTimeMillis();
        if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
            if (currentTime - lastPlayerAttackTime >= PLAYER_ATTACK_COOLDOWN_MS) {
                lastPlayerAttackTime = currentTime;
                for (BasicEnemy enemy : enemies) {
                    if (!enemy.isAlive()) continue;
                    if (enemy.isNear(imageX, imageY, 150)) {
                        if (enemy.canTakeDamage()) {
                            boolean wasAlive = enemy.isAlive();
                            enemy.damage(1);
                            enemy.registerHitByPlayer();
                            if (wasAlive && !enemy.isAlive()) {
                                enemiesDefeated++;
                                if (enemiesDefeated >= WIN_THRESHOLD) {
                                    playerWon = true;
                                    gameTimer.stop();
                                }
                            }
                        }
                    }
                }
            }
        }
        if(playerHealth.isDead()){
            gameOver=true;
            gameTimer.stop();
        }
        if(enemies.stream().filter(BasicEnemy::isAlive).count()<3){
            spawnEnemy();
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    private void restartGame(){
        playerHealth = new Health();
        imageX=400;
        imageY=400;
        enemies.clear();
        for(int i = 0; i < 5; i++) {
            spawnEnemy();
        }
        enemiesDefeated=0;
        gameOver = false;
        gameTimer.start();
        repaint();
    }
}