package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** @author Grant Robinson **/

class Star {
    private int x;
    private int y;

    public Star (int screenWidth, int screenHeight) {
        x = (int) (Math.random() * (screenWidth));
        y = (int) (Math.random() * (screenHeight));
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 1, 1);
    }

    public static List<Star> createStar(int numStars, int screenWidth, int screenHeight){
        List<Star> stars = new ArrayList<>();
        for (int i = 0; i < numStars; i++) {
            stars.add(new Star(screenWidth, screenHeight));
        }
        return stars;
    }
}