package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/** @author Grant Robinson **/
/** @author Nashali Vicente Lopez **/
public class MouseNanny implements MouseListener, MouseMotionListener {
    private Planet selectedPlanet = null;
    private List<Meteorite> meteorites = new ArrayList<>();
    public MouseNanny() {}
    public MouseNanny(List<Meteorite> meteorites) {
        this.meteorites = meteorites;
    }
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
    private void switchToZoomPanel(Planet planet, MouseEvent e){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        frame.getContentPane().removeAll();
        frame.add(new ZoomPanel(planet));
        frame.revalidate();
        frame.repaint();
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
                switchToZoomPanel(selectedPlanet, e);
                showClassMetrics(selectedPlanet);
                Officer.tellYourBoss();
            }
        }
        if (selectedPlanet == null) { System.out.println("selected: null"); } else {
            System.out.println("selected: " + selectedPlanet.getName()); }
    }
    private void showClassMetrics(Planet planet){
        try{
            String metrics = Officer.getMetrics(planet.getFilepath());
            JTextArea textArea = new JTextArea(metrics);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JFrame metricsFrame = new JFrame("Class Metrics");
            metricsFrame.setSize(250, 500);
            metricsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            metricsFrame.add(scrollPane);
            metricsFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        boolean found = false;
        for(Meteorite meteorite: meteorites){
            if(meteorite.isHovered(e.getX(), e.getY())){
                meteorite.showHoverWindow(e.getXOnScreen(), e.getYOnScreen());
                found = true;
                break;
            }
        }
        if(!found){
            for(Meteorite meteorite: meteorites){
                meteorite.hideHoverWindow();
            }
        }
    }
    public void mouseDragged(MouseEvent e) {}
}
