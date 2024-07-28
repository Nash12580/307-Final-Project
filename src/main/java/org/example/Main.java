package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/** @author Grant Robinson **/
/** @author Nashali Vicente **/
/** @author Veer Bhagia **/

public class Main extends JFrame {

    public static void main(String[] args) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Officer.setScreenWidth(screenSize.width);
        Officer.setScreenHeight(screenSize.height);
        CodeParser.initialize(screenSize.width, screenSize.height);
        while (true) {
            String path = JOptionPane.showInputDialog(null, "Enter the file or directory path:", "Input Path", JOptionPane.PLAIN_MESSAGE);

            if (path == null) {
                JOptionPane.showMessageDialog(null, "Path cannot be empty.", "Empty Path", JOptionPane.ERROR_MESSAGE);
            } else {
                path = path.trim();
                File file = new File(path);
                if (file.isDirectory()) {
                    CodeParser.parseJavaDirectory(path);
                    break;
                } else if (file.isFile()) {
                    CodeParser.parseJavaFile(path);
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid path. Please enter a valid file or directory path.", "Invalid Path", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
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
