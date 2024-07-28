package org.example;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author Grant Robinson **/
class Planet {
    private final String name, filepath;
    private final int radius;
    private int x, y;
    private final Color mainColor, secondaryColor;
    private boolean selected;
    private final List<String> fields;
    private final Map<String, String> methods;
    private final double distanceToSun, speed;
    private double angle;
    public Planet(String nameIn, int radiusIn, double distanceToSunIn, int xIn, int yIn, Color mainColorIn, Color secondaryColorIn, String filapath, List<String> fields, Map<String, String> methods) {
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
        this.methods = methods != null ? methods : new HashMap<>();
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
    public Color getSecondaryColor() {
        return secondaryColor == null ? Color.BLACK : secondaryColor;
    }
    public String getName() {
        return name == null ? "No Name" : name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getRadius() {
        return radius;
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
    public Map<String, String> getMethods() {
        return methods;
    }
}
