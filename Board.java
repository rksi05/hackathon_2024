import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    public BufferStrategy bufferStrategy;

    public boolean coindisplayed;
    // controls the delay between each tick in ms
    private final int DELAY = 25;
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;
    // controls how many coins appear on the board
    public static final int NUM_COINS = 5;
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;
    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    // objects that appear on the game board
    private Player player;
    public Door door;
    public Introduction introduction;
    public Background background;
    public Smithsonian smithsonian;
    public House house;
    private ArrayList<Coin> coins;
    private ArrayList<Biden> bidens;
    public int level = 0;
    public int counter = 0;
    public Image louvre;
    public Image background2;
    public Image intro;
    public Image whitehouse;
    public ArrayList<Coin> coinList;
    public ArrayList<Biden> bidenList;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(933, 733));
        // set the game board background color
      

        // initialize the game state
        introduction = new Introduction(0,0);
        background = new Background(0,0);
        smithsonian = new Smithsonian(0,0);
        door = new Door(700, 300);
        player = new Player();
        coins = populateCoins();

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // prevent the player from disappearing off the board
        player.tick();

        // give the player points for collecting coins
        collectCoins();

        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver 
        // because Component implements the ImageObserver interface, and JPanel 
        // extends from Component. So "this" Board instance, as a Component, can 
        // react to imageUpdate() events triggered by g.drawImage()
        if (level == 0){
            introduction.draw(g,this);
        }
        
        if (level == 1){
            background.draw(g,this);
    
            for (Coin coin : coins) {
            // System.out.println("drawing coin!");
            coin.draw(g, this);
            }
            
            door.draw(g, this);
            player.draw(g, this);
        }
        if (level == 2){
            smithsonian.draw(g,this);
            for (Coin coin : coins) {
                System.out.println("drawing coin!");
                coin.draw(g, this);
            }
           
            door.draw(g, this);
            player.draw(g, this);
        }

        if (level == 3){
            house.draw(g,this);
            for (Biden biden: bidens){
                biden.draw(g,this);
            }
        }
       // drawScore(g);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    private ArrayList<Coin> populateCoins() {
        coinList = new ArrayList<>();
        Random rand = new Random();

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < NUM_COINS; i++) {
            int coinX = rand.nextInt(18);
            int coinY = rand.nextInt(15);
            coinList.add(new Coin(coinX, coinY));
        }

        return coinList;
    }

    private ArrayList<Biden> populateBidens() {
        bidenList = new ArrayList<>();
        Random rand = new Random();

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < 6; i++) {
            int bidenX = rand.nextInt(18);
            int bidenY = rand.nextInt(15);
            bidenList.add(new Biden(bidenX, bidenY));
        }

        return bidenList;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            level = 1;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    /* private void drawScore(Graphics g) {
        // set the text to be displayed
        String text = "$" + player.getScore();
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(207, 165, 255));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        // here I've sized it to be the entire bottom row of board tiles
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(text, x, y);
    } */

    private void collectCoins() {
        // allow player to pickup coins
        ArrayList<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            // if the player is on the same tile as a coin, collect it

            if (player.rec.intersects(coin.rec)) {
                // give the player some points for picking this up
                player.addScore(100);
                collectedCoins.add(coin);
                counter += 1;
                System.out.print("counter " + counter);
            }
            
        }
        // remove collected coins from the board
        coins.removeAll(collectedCoins);
        if (counter == 5){
            nextLevel();
        }

        if (counter >= 10){
            nextNextLevel();
        }

    }

    private void collectBidens() {
        // allow player to pickup coins
        ArrayList<Biden> collectedBidens = new ArrayList<>();
        for (Biden biden : bidens) {
            // if the player is on the same tile as a coin, collect it

            if (player.rec.intersects(biden.rec)) {
                // give the player some points for picking this up
                player.addScore(100);
                collectedBidens.add(biden);
                System.out.print("counter " + counter);
            }
            
        }
        // remove collected coins from the board
        bidens.removeAll(collectedBidens);
    }
        



    private void nextLevel(){
        if (player.rec.intersects(door.rec)){
            level = 2;
            player.pos.x = 0;
            player.pos.y = 0;
            System.out.print("level "+level);
            for (int i = 0; i < 6; i++) {
                int coinX = (int)(Math.random()*18);
                int coinY = (int)(Math.random()*15);
                coinList.add(new Coin(coinX, coinY));
            }
        }
    }

    private void nextNextLevel(){
        if (player.rec.intersects(door.rec)){
            level = 3;
            player.pos.x = 0;
            player.pos.y = 0;
            System.out.print("level "+level);
            for (int i = 0; i < 6; i++) {
                int coinX = (int)(Math.random()*18);
                int coinY = (int)(Math.random()*15);
                coinList.add(new Coin(coinX, coinY));
            }
        }
    }
}
