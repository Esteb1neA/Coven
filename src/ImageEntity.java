import java.awt.image.BufferedImage;

public class ImageEntity {
    private BufferedImage img;
    private int x,y;
    private String id;
    public ImageEntity(BufferedImage img, int x, int y, String id){
        this.img=img;
        this.x=x;
        this.y=y;
        this.id=id;
    }
    public BufferedImage getImage(){
        return img;
    }
    public String getId(){
        return id;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean contains(int mx, int  my){
        return mx >=x && mx <= x + img.getWidth(null) && my >=y && my <= y + img.getHeight(null);
    }
}
