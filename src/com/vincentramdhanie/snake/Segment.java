package com.vincentramdhanie.snake;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;

/**
 * A Segment of the Snake
 * @author Vincent Ramdhanie
 */
public class Segment implements Drawable{
    private long x;
    private long y;
    protected Shape shape;
    protected Color colour;
    protected Paint paint;
    
    public Segment(){
        this(0, 0);
    }
    
    public Segment(long x, long y){
        setX(x);
        setY(y);
        shape = new Rectangle2D.Double(0, 0, SnakeWindow.GRID_SIZE,SnakeWindow.GRID_SIZE );
        colour = Color.GREEN;
        paint = new GradientPaint(0, 0, Color.WHITE,SnakeWindow.GRID_SIZE,SnakeWindow.GRID_SIZE, getColour());
    }
    

    @Override
    public void draw(Graphics2D g) {
        AffineTransform t = new AffineTransform();
        t.translate(getX() * SnakeWindow.GRID_SIZE, getY() * SnakeWindow.GRID_SIZE);
        g.transform(t);
        g.setPaint(getPaint());
        g.fill(shape());        
        g.setColor(getColour());
        g.draw(shape());
        try{
            g.transform(t.createInverse());
        }catch(NoninvertibleTransformException e){
            System.out.println("You should not be printing here.");
        }
    }

    @Override
    public Shape shape() {
        return shape;
    }

    /**
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public long getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return the colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * @return the paint
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * @param paint the paint to set
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    
    
    
}
