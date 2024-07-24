package org.example;

import java.awt.*;

class Star {
    private int x;
    private int y;

    public Star () {
        x = (int) (Math.random() * (1400 - 1));
        y = (int) (Math.random() * (1000 - 1));
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 1, 1);
    }
}