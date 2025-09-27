public class BasicEnemy {
    private int Health;
    public BasicEnemy(){
        Health=2;
    }
    public void takeDamage(int damage){
        Health = Health - damage;
    }
    public boolean isDead(){
        if(Health<=0){
            return true;
        }
        return false;
    }
    /*public void destroyEnemy(){
        if(isDead()){
            despawn();
        }
    }*/
}
