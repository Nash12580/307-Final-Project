package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** @author Grant Robinson **/

class Planet {
    private String name;
    private int radius;
    private int x;
    private int y;
    private Color mainColor;
    private Color secondaryColor;
    private boolean selected;
    private String filepath;
    private List<String> fields;
    private List<String> methods;
    private double distanceToSun;
    private double angle;
    private double speed;

    public Planet(String nameIn, int radiusIn, double distanceToSunIn, int xIn, int yIn, Color mainColorIn, Color secondaryColorIn, String filapath, List<String> fields, List<String> methods) {
        name = nameIn;
        radius = radiusIn;
        x = xIn;
        y = yIn;
        distanceToSun = distanceToSunIn;
        mainColor = mainColorIn;
        secondaryColor = secondaryColorIn;
        selected = false;
        filepath = filapath;
        this.fields = fields != null ? fields : new ArrayList<>();
        this.methods = methods != null ? methods : new ArrayList<>();
        angle = (Math.random()*(360));
        speed = Math.random() + .01;
    }

    public void draw(Graphics2D g) {
        GradientPaint gradient = new GradientPaint(x, y, mainColor, x + radius, y + radius, secondaryColor, true);
        g.setPaint(gradient);
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(Color.WHITE);
        g.drawString(name, x, y);
    }

    public void updatePosition(double centerX, double centerY, double speed) {
        angle += speed;
        if (angle >= 360) {
            angle -= 360;
        }
        x = (int) (centerX - radius + distanceToSun * Math.cos(Math.toRadians(angle)));
        y = (int) (centerY - radius + distanceToSun * Math.sin(Math.toRadians(angle)));
    }
    public double getSpeed() {
        return speed;
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
    public String getFilepath() {
        return filepath;
    }
    public List<String> getFields() {
        return fields;
    }
    public List<String> getMethods() {
        return methods;
    }
}
