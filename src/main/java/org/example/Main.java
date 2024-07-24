package org.example;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private List<Planet> planets = new ArrayList<>();

    public static void main(String[] args) {
        String testFilepath = "C:\\Users\\Nasha\\JavaProjects\\CSC 307\\PaintApp";
        CodeParser.parseJavaDirectory(testFilepath);
        Main universe = new Main();
        universe.setSize(1400, 1000);
        universe.setTitle("Universe");
        universe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universe.setVisible(true);
    }

    public Main() {
        UniversePanel panel = new UniversePanel(planets);
        add(panel);
    }
}