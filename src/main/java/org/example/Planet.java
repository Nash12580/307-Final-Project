package org.example;

import java.awt.*;

class Planet {
    private String name;
    private int radius;
    private int x;
    private int y;
    private Color mainColor;
    private Color secondaryColor;
    private boolean selected;

    public Planet(String nameIn, int radiusIn, int xIn, int yIn, Color mainColorIn, Color secondaryColorIn) {
        name = nameIn;
        radius = radiusIn;
        x = xIn;
        y = yIn;
        mainColor = mainColorIn;
        secondaryColor = secondaryColorIn;
        selected = false;
    }

    public void draw(Graphics2D g) {
        GradientPaint gradient = new GradientPaint(x, y, mainColor, x + radius, y + radius, secondaryColor, true);
        g.setPaint(gradient);
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(Color.WHITE);
        g.drawString(name, x, y);
    }

    public Color getMainColor() {
        return mainColor == null ? Color.BLACK : mainColor;
    }

    public void setMainColor(Color colorIn) {
        mainColor = colorIn;
    }

    public Color getSecondaryColor() {
        return secondaryColor == null ? Color.BLACK : secondaryColor;
    }

    public void setSecondaryColor(Color colorIn) {
        secondaryColor = colorIn;
    }

    public String getName() {
        return name == null ? "No Name" : name;
    }

    public void setName(String planetName) {
        name = planetName;
    }

    public int getX() {
        return x;
    }

    public void setX(int xIn) {
        x = xIn;
    }

    public int getY() {
        return y;
    }

    public void setY(int yIn) {
        y = yIn;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radiusIn) {
        radius = radiusIn;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }
}

