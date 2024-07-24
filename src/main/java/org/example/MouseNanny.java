package org.example;

import java.awt.event.*;
import java.util.Stack;

/** @author Nashali Vicente Lopez **/
/** @author Grant Robinson **/

public class MouseNanny implements MouseListener, MouseMotionListener {
    private Planet selectedPlanet = null;

    private Planet getPlanetAt(int x, int y){
        Stack<Planet> planets = Officer.getPlanetStack();
        for(int i = planets.size() - 1; i >= 0; i--){
            Planet planet = planets.get(i);
            if (planetContains(planet, x, y)){
                return planet;
            }
        }
        return null;
    }

    private boolean planetContains(Planet planet, int x, int y){
        return x >= planet.getX() && x <= planet.getX() + planet.getRadius() * 2 &&
                y >= planet.getY() && y <= planet.getY() + planet.getRadius() * 2;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            selectedPlanet = getPlanetAt(e.getX(), e.getY());
            if (selectedPlanet != null) {
                selectedPlanet.setSelected(!selectedPlanet.isSelected());
                for (Planet planet : Officer.getPlanetStack()) {
                    if (planet.isSelected() && planet != selectedPlanet) {
                        planet.setSelected(false);
                    }
                }
                selectedPlanet.setSelected(!selectedPlanet.isSelected());
                Officer.tellYourBoss();
            }
        }
        if (selectedPlanet == null) { System.out.println("selected: null"); } else {
            System.out.println("selected: " + selectedPlanet.getName()); }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}
