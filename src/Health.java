public class Health {
    private int Health;
    public Health(){
        Health = 5;
    }
    public void loseHealth(int damage){
        Health = Health-damage;
    }
    public boolean isHealthZero(){
        if(Health<=0){
            return true;
        }
        return false;
    }
}
