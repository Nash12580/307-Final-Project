package org.example;

import java.awt.*;

/** @author Grant Robinson **/

class Star {
    private int x;
    private int y;

    public Star () {
        x = (int) (Math.random() * (Officer.getScreenWidth() - 1));
        y = (int) (Math.random() * (Officer.getScreenHeight() - 1));
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 1, 1);
    }
}