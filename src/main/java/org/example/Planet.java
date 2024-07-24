package org.example;

import java.awt.*;

class Planet {
    private String name;
    private int radius;
    private int x;
    private int y;
    private Color mainColor;
    private Color secondaryColor;

    public Planet(String nameIn, int radiusIn, int xIn, int yIn, Color mainColorIn, Color secondaryColorIn) {
        name = nameIn;
        radius = radiusIn;
        x = xIn;
        y = yIn;
        mainColor = mainColorIn;
        secondaryColor = secondaryColorIn;
    }

    public void draw(Graphics2D g) {
        int radius = 50;
        GradientPaint gradient = new GradientPaint(x, y, mainColor, x + radius, y + radius, secondaryColor, true);
        g.setPaint(gradient);
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(Color.WHITE);
        g.drawString(name, x, y);
    }
}
