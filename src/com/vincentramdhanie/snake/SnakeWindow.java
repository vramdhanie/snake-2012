package com.vincentramdhanie.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The main window in the Game
 * @author Vincent Ramdhanie
 */
public class SnakeWindow extends JFrame{
    // Define constants for the game
    static final int CANVAS_WIDTH = 400;    // width and height of the game screen
    static final int CANVAS_HEIGHT = 300;
    static final int UPDATE_RATE = 4;    // number of game update per second
    static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
    static final long GRID_ROWS = 30;
    static final long GRID_COLS = 40;
    static final long GRID_SIZE = 10;
    // ......

    // Enumeration for the states of the game.
    static enum State {

        INITIALIZED, PLAYING, PAUSED, GAMEOVER, DESTROYED
    }
    static State state;   // current state of the game
    // Define instance variables for the game objects
    // ......
    // ......
    // Handle for the custom drawing panel
    private GameCanvas canvas;

    Snake snake;
    Rat rat;
    long score;
    
    // Constructor to initialize the UI components and game objects
    public SnakeWindow() {
        // Initialize the game objects
        gameInit();

        // UI components
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);

        // Other UI components such as button, score board, if any.
        // ......

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // get the screen size as a java dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        // set the jframe height and width
        //this.setSize(new Dimension(width, height));
        setSize(SnakeWindow.CANVAS_WIDTH + 20, SnakeWindow.CANVAS_HEIGHT + 40);
        this.setTitle("Snake");
        this.setVisible(true);
    }

    // All the game related codes here
    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit() {
        // ...... 
        //snake = new Snake();
        rat = new Rat();
        state = State.INITIALIZED;
        score = 0;
    }

    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
        // ...... 
    }

    // To start and re-start the game.
    public void gameStart() {
        // Create a new thread
        Thread gameThread = new Thread() {
            // Override run() to provide the running behavior of this thread.

            @Override
            public void run() {
                gameLoop();
            }
        };
        // Start the thread. start() calls run(), which in turn calls gameLoop().
        gameThread.start();
    }

    // Run the game loop here.
    private void gameLoop() {
        // Regenerate the game objects for a new game
        snake = new Snake();        
        state = State.PLAYING;

        // Game loop
        long beginTime, timeTaken, timeLeft;
        while (true) {
            beginTime = System.nanoTime();
            if (state == State.GAMEOVER) {
                break;  // break the loop to finish the current play
            }
            if (state == State.PLAYING) {
                // Update the state and position of all the game objects,
                // detect collisions and provide responses.
                
                gameUpdate();
            }
            // Refresh the display
            repaint();
            // Delay timer to provide the necessary delay to meet the target rate
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000L;  // in milliseconds
            if (timeLeft < 10) {
                timeLeft = 10;   // set a minimum
            }
            try {
                // Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) {
            }
        }
    }

    // Update the state and position of all the game objects,
    // detect collisions and provide responses.
    public void gameUpdate() {
        //for each Game object perorm update logic here
        if(snake.bitSelf()){
            state = State.GAMEOVER;
        }
        if(snake.bite(rat)){
            snake.grow();
            score += rat.getValue();
            Random ran = new Random();
            rat = new Rat(ran.nextInt(100)<10?ran.nextInt(93) + 7:7);
        }else{
            snake.move();
        }
    }

    // Refresh the display. Called back via rapaint(), which invoke the paintComponent().
    private void gameDraw(Graphics2D g2d) {
        switch (state) {
            case INITIALIZED:
                g2d.setPaint(new GradientPaint(20, 20, Color.WHITE, 200, 200, Color.ORANGE));
                g2d.fillOval(15, 15, 200, 200);
                g2d.setFont(new Font("Goudy Handtooled BT", Font.PLAIN, 50));
                g2d.setPaint(new GradientPaint(20, 20, Color.WHITE, 300, 300, Color.GREEN));
                g2d.drawString("Snake", 50, 100);
                g2d.setFont(new Font("Goudy Handtooled BT", Font.PLAIN, 25));
                g2d.drawString("Press 's' to start", 50, 120);
                break;
            case PLAYING:
                    g2d.setColor(Color.RED);
                    g2d.setFont(new Font("Goudy Handtooled BT", Font.PLAIN, 15));
                    g2d.drawString(String.format("Score: %d", score), CANVAS_WIDTH - 100, 10);
                    rat.draw(g2d);
                    snake.draw(g2d);       
                break;
            case PAUSED:
                // ......
                break;
            case GAMEOVER:
                g2d.setPaint(new GradientPaint(20, 20, Color.WHITE, CANVAS_WIDTH - 20, CANVAS_HEIGHT - 20, Color.ORANGE));
                g2d.setFont(new Font("Goudy Handtooled BT", Font.PLAIN, 100));
                g2d.drawString("Game Over", 20, 20);
                break;
        }
        // ...... 
    }

    // Process a key-pressed event. Update the current state.
    public void gameKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                snake.head.setDirection(Head.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                snake.head.setDirection(Head.Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                snake.head.setDirection(Head.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                snake.head.setDirection(Head.Direction.RIGHT);
                break;
            case KeyEvent.VK_S:
                gameStart();
                break;
        }
    }

    // Process a key-released event.
    public void gameKeyReleased(int keyCode) {
    }

    // Process a key-typed event.
    public void gameKeyTyped(char keyChar) {
    }

    // Other methods
    // ......
    // Custom drawing panel, written as an inner class.
    class GameCanvas extends JPanel implements KeyListener {
        // Constructor

        public GameCanvas() {
            setFocusable(true);  // so that can receive key-events
            requestFocus();
            addKeyListener(this);
        }

        // Override paintComponent to do custom drawing.
        // Called back by repaint().
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g2d);   // paint background
            setBackground(Color.BLACK);  // may use an image for background

            // Draw the game objects
            gameDraw(g2d);
        }

        // KeyEvent handlers
        @Override
        public void keyPressed(KeyEvent e) {
            gameKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gameKeyReleased(e.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent e) {
            gameKeyTyped(e.getKeyChar());
        }
    }

    // main
    public static void main(String[] args) {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new SnakeWindow();
            }
        });
    }
}
