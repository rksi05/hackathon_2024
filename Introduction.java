import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Introduction {
    private BufferedImage image;
    public Point introPos;
    public int width = 933;
    public int height = 733;

    public Introduction(int x, int y) {
        // load the assets
        loadImage();
        // initialize the state
        introPos = new Point(0,0);
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/artheist.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
            image, 
            introPos.x, 
            introPos.y, 
            width,
            height,
            observer
        );
    }

    public Point getPos() {
        return introPos;
    }
}


