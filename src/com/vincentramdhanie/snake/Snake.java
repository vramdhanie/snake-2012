package com.vincentramdhanie.snake;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 *
 * @author Vincent Ramdhanie
 */
public class Snake {
    Head head;
    LinkedList<Segment> segments;
    
    public Snake(){
        head = new Head(20, 20);
        segments = new LinkedList<Segment>();
        segments.add(new Segment(19, 20));
        segments.add(new Segment(18, 20));
        segments.add(new Segment(17, 20));
        segments.add(new Segment(16, 20));
    }
    
    public void grow(){        
        Segment s = new Segment(head.getX(), head.getY());
        segments.addFirst(s);
        head.move();
    }
    
    public void move(){
        Segment s = segments.removeLast();
        s.setX(head.getX());
        s.setY(head.getY());
        segments.addFirst(s);
        head.move();
    }
    
    public void draw(Graphics2D g){
        head.draw(g);
        for(Segment s: segments){
            s.draw(g);
        }
    }
    
    public boolean bitSelf(){
        for(Segment s: segments){
            if(head.getX() == s.getX() && head.getY() == s.getY()){
                return true;
            }
        }
        return false;
    }
    
    public boolean bite(Rat r){
        return head.getX() == r.getX() && head.getY() == r.getY();
    }
}
