
public class Health {
    private int Health;
    public Health(){
        Health = 5;
    }
    public void loseHealth(){
        Health = Health-1;
    }
    public boolean isHealthZero(){
        if(Health<=0){
            return true;
        }
        return false;
    }
}
