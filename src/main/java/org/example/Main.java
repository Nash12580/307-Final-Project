package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/** @author Grant Robinson **/

public class Main extends JFrame {

    public static void main(String[] args) throws IOException {
        String testFilepath = "C:\\Users\\Nasha\\JavaProjects\\CSC 307\\PaintApp";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Officer.setScreenWidth(screenSize.width);
        Officer.setScreenHeight(screenSize.height);
        CodeParser.initialize(screenSize.width, screenSize.height);
        CodeParser.parseJavaDirectory(testFilepath);

        Main universe = new Main();
        universe.setSize(screenSize.width, screenSize.height);
        universe.setTitle("Universe");
        universe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universe.setVisible(true);
    }

    public Main() {
        setJMenuBar(new FileMenu());
        UniversePanel universePanel = new UniversePanel();
        MouseNanny mouseNanny = new MouseNanny();
        universePanel.addMouseListener(mouseNanny);
        universePanel.addMouseMotionListener(mouseNanny);
        add(universePanel);
        Officer.setUniversePanel(universePanel);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Officer.tellYourBoss();
            }
        }, 0, 100);
    }
}
