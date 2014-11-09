package com.vincentramdhanie.snake;

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author Vincent Ramdhanie
 */
public interface Drawable {
    public void draw(Graphics2D g);
    public Shape shape();
}
