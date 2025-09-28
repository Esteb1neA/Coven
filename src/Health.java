public class Health {
    private int health=10;
    public void damage(int amount){
        health = Math.max(0,health-amount);
    }
    public int getHealth(){
        return health;
    }
    public boolean isDead(){
        return health<=0;
    }
}
