

import javax.management.timer.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
class Guard {
public String name;                //holds the name of the hero
public int xpos;                //the x position
public int ypos;                //the y position
public int dx;                    //the speed of the hero in the x direction
public int dy;                    //the speed of the hero in the y direction
public int width;
public int height;
public int score;
public boolean isAlive;//a boolean to denote if the hero is alive or dead.
public Rectangle rec;
public boolean isCrashing;
public boolean up;
public boolean down;
public boolean left;
public boolean right;
public boolean timer = false;
public String startingangle;
public Image guardPic;

public Guard(int pXpos, int pYpos, int pDx, int pDy) {
    xpos = pXpos;
    ypos = pYpos;
    dx = pDx;
    dy = pDy;
    width = 60;
    height = 60;
    isAlive = true;
    score = 0;
    rec = new Rectangle(xpos, ypos, width, height);
    isCrashing = false;
} // constructor

   // METHOD DEFINITION SECTION


   // Constructor Definition
   // A constructor builds the object when called and sets variable values.




   //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
   // if you put in a String, an int and an int the program will use this constructor instead of the one abov
   
   public void draw(Graphics g) {
   // with the Point class, note that pos.getX() returns a double, but
   // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
   // this is also where we translate board grid position into a canvas pixel
   // position by multiplying by the tile size.
    g.drawImage(guardPic,xpos, ypos, width,height, null);
    guardPic = Toolkit.getDefaultToolkit().getImage("image/guardPic.png");
  	}


   //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void wrap() {
       if (ypos > 700 - height) {//south wall
           ypos = 10;
        }
       if (xpos > 1000 - width) {//east wall
           xpos = 10;
       }
       if (ypos < 0) {//north wall
           ypos = 700 - height;
       }
       if (xpos < 0) {//west wall
           xpos = 1000 - width;
       }
       xpos = xpos + dx;
       ypos = ypos + dy;
       rec = new Rectangle(xpos, ypos, width, height);
   }
}
