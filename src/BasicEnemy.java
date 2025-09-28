public class BasicEnemy{
    private int x,y;
    private int health;
    private final int SPEED = 2;
    private boolean alive = true;
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
    public boolean isNear(int px, int py, int range){
        int dx = px - x;
        int dy = py - y;
        return dx * dx + dy * dy < range * range;
    }
}