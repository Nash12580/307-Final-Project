package org.example;
import javax.swing.*;
import java.awt.*;
/** @author Nashali Vicente Lopez**/
public class Meteorite {
    private int x, y, size;
    private Color color;
    private double angle, distanceFromPlanet;
    private String methodName, methodParams;
    private JWindow hoverWindow;
    private JLabel hoverLabel;
    public Meteorite(int centerX, int centerY, int size, double distanceFromPlanet, double angle, String methodName, String methodParams, Color color) {
        this.x = (int) (centerX + distanceFromPlanet * Math.cos(Math.toRadians(angle)));
        this.y = (int) (centerY + distanceFromPlanet * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.size = size;
        this.distanceFromPlanet = distanceFromPlanet;
        this.color = color;
        this.methodName = methodName;
        this.methodParams = methodParams;

        hoverWindow = new JWindow();
        hoverLabel = new JLabel();
        hoverLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hoverWindow.add(hoverLabel);
        hoverWindow.setSize(150,50);
    }
    public void updatePosition(int centerX, int centerY, double speed) {
        angle += speed;
        if (angle >= 360) {
            angle -= 360;
        }
        x = (int) (centerX + distanceFromPlanet * Math.cos(Math.toRadians(angle)));
        y = (int) (centerY + distanceFromPlanet * Math.sin(Math.toRadians(angle)));
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(x, y, size, size);
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String text = methodName;
        FontMetrics fm = g2d.getFontMetrics();
        int textHeight = fm.getHeight();
        int textX = x + size + 5;
        int textY = y + (size / 2) + (textHeight / 2);
        g2d.drawString(text, textX, textY);
    }
    public boolean isHovered(int mouseX, int mouseY){
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }
    public void showHoverWindow(int x, int y){
        hoverLabel.setText("<html><body style ='width: 150px;'>" + methodParams + "</body></html>");
        hoverWindow.setLocation(x + 15, y + 15);
        hoverWindow.setVisible(true);
    }
    public void hideHoverWindow(){
        hoverWindow.setVisible(false);
    }
}