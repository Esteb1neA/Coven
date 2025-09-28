public class BasicEnemy{
    private int x,y;
    private int health;
    private final int SPEED = 4;
    private boolean alive = true;
    private final int WIDTH = 37;
    private final int HEIGHT = 37;
    private long lastDamageTime = 0;
    private final long DAMAGE_COOLDOWN_MS = 2000;
    private long lastTimeDamagedByPlayer = 0;
    private final long HIT_COOLDOWN_MS = 1000;
    public BasicEnemy(int x, int y, int health){
        this.x=x;
        this.y=y;
        this.health=health;
    }
    public void moveTowards(int targetX, int targetY){
        if(!alive) return;
        int dx = targetX - x;
        int dy = targetY - y;
        double distance = Math.sqrt(dx*dx+dy*dy);
        if(distance>0){
            x+=(int) (SPEED*dx/distance);
            y+=(int) (SPEED*dy/distance);
        }
    }
    public boolean collidesWith(int px, int py, int pw, int ph){
        return alive && x < px + pw && x + WIDTH > px && y < py + ph && y + HEIGHT > py;
    }
    public boolean isNear(int px, int py, int range){
        int dx = px - x;
        int dy = py - y;
        return dx * dx + dy * dy < range * range;
    }
    public void registerDamage(){
        lastDamageTime = System.currentTimeMillis();
    }
    public boolean tryDamagePlayer(){
        long now = System.currentTimeMillis();
        if(now - lastDamageTime >= DAMAGE_COOLDOWN_MS){
            lastDamageTime = now;
            return true;
        }
        return false;
    }
    public boolean canTakeDamage(){
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastDamageTime >= HIT_COOLDOWN_MS);
    }
    public void registerHitByPlayer(){
        lastTimeDamagedByPlayer = System.currentTimeMillis();
    }
    public void damage(int amount){
        health -= amount;
        if (health<=0){
            alive = false;
        }
    }
    public boolean isAlive(){
        return alive;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}