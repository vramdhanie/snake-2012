/*
 * 
 * 
 */
package com.vincentramdhanie.snake;


import java.awt.geom.Ellipse2D;

/**
 * The Head of the snake.
 * @author Vincent Ramdhanie
 */
public class Head extends Segment{
    
    Direction recent;
    
    public Head(){
        this(0, 0);
    }
    
    public Head(int x, int y){
        setX(x);
        setY(y);
        shape = new Ellipse2D.Double(0, 0, SnakeWindow.GRID_SIZE,SnakeWindow.GRID_SIZE);
        recent = Direction.RIGHT;
    }
    
    public void move(){
        move(recent);
    }
    
    public void move(Direction d){
        setDirection(d);
        switch(recent){
            case UP:moveUp();break;
            case DOWN:moveDown();break;
            case RIGHT:moveRight();break;
            case LEFT:moveLeft();break;    
        }
    }
    
    public void moveUp(){
        setY(getY() == 0?SnakeWindow.GRID_ROWS:getY() - 1);
        
    }
    
    public void moveDown(){
        setY((getY() + 1) % SnakeWindow.GRID_ROWS);
    }
    
    public void moveLeft(){
        setX(getX() == 0?SnakeWindow.GRID_COLS:getX() - 1);
    }
    
    public void moveRight(){
        setX((getX() + 1) % SnakeWindow.GRID_COLS);
    }
    
    
    public void setDirection(Direction d){
        switch(d){
            case UP:if(recent != Direction.DOWN) recent = d;break;
            case DOWN:if(recent != Direction.UP) recent = d;break;
            case LEFT:if(recent != Direction.RIGHT) recent = d;break;
            case RIGHT:if(recent != Direction.LEFT) recent = d;break;    
        }
        
    }
    
    enum Direction{
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
