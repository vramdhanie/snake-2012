package com.vincentramdhanie.snake;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Random;

/**
 *
 * @author Vincent Ramdhanie
 */
public class Rat extends Segment {

    Random ran = new Random();
    private int value;
    int countdown;

    public Rat() {
        this(0, 0);
        setX(ran.nextInt((int) SnakeWindow.GRID_COLS));
        setY(ran.nextInt((int) SnakeWindow.GRID_ROWS));
    }

    public Rat(int value) {
        this();
        setValue(value);
        if (value == 7) {
            colour = Color.RED;
        } else {
            colour = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
            countdown = 10;
            Thread t = new Thread(new Runnable() {

                public void run() {
                    while (countdown > 0) {
                        countdown--;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                    setValue(7);
                    setColour(Color.RED);
                    setPaint(new GradientPaint(0, 0, Color.WHITE, SnakeWindow.GRID_SIZE, SnakeWindow.GRID_SIZE, getColour()));
                }
            });
            t.start();
        }
        paint = new GradientPaint(0, 0, Color.WHITE, SnakeWindow.GRID_SIZE, SnakeWindow.GRID_SIZE, getColour());
    }

    public Rat(long x, long y) {
        setX(x);
        setY(y);
        shape = new Ellipse2D.Double(0, 0, SnakeWindow.GRID_SIZE, SnakeWindow.GRID_SIZE);
        colour = Color.RED;
        paint = new GradientPaint(0, 0, Color.WHITE, SnakeWindow.GRID_SIZE, SnakeWindow.GRID_SIZE, getColour());
        value = 7;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (value > 7) {
            AffineTransform t = new AffineTransform();
            t.translate(getX() * SnakeWindow.GRID_SIZE, getY() * SnakeWindow.GRID_SIZE);
            g.transform(t);
            g.setColor(Color.YELLOW);
            g.drawString(String.format("%d", countdown), 10, 0);
            try {
                g.transform(t.createInverse());
            } catch (NoninvertibleTransformException e) {
                System.out.println("You should not be printing here.");
            }
        }
    }
}
