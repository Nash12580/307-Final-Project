package org.example;
import java.awt.*;
import java.io.IOException;
import java.util.Stack;
import java.util.List;
import java.util.Map;

/** @author Grant Robinson **/
/** @author Nashali Vicente Lopez **/
public class Officer {
    private static UniversePanel universePanel;
    private static final Stack<Planet> planets = new Stack<>();
    private static int screenWidth;
    private static int screenHeight;
    public static void setUniversePanel(UniversePanel d) {
        universePanel = d;
    }

    public static void tellYourBoss() {
        universePanel.repaint();
    }

    public static Stack<Planet> getPlanetStack() {
        return planets;
    }

    public static Color[] generateColors(String fileName) {
        int hash = fileName.hashCode();
        Color color1 = new Color((hash & 0xFF0000) >> 16, (hash & 0x00FF00) >> 8, hash & 0x0000FF);
        int hash2 = Integer.rotateLeft(hash, 16);
        Color color2 = new Color((hash2 & 0xFF0000) >> 16, (hash2 & 0x00FF00) >> 8, hash2 & 0x0000FF);
        return new Color[]{color1, color2};
    }

    public static void setScreenWidth(int width) {
        screenWidth = width;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenHeight(int height) {
        screenHeight = height;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static String getMetrics(String filepath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("File: ").append(filepath).append("\n");
        sb.append("Total Lines: ").append(CodeMetrics.countTotalLines(filepath)).append("\n");
        sb.append("LOC: ").append(CodeMetrics.countLOC(filepath)).append("\n");
        sb.append("eLOC: ").append(CodeMetrics.counteLOC(filepath)).append("\n");
        sb.append("lLOC: ").append(CodeMetrics.countlLOC(filepath)).append("\n");
        sb.append("Abstractness: ").append(CodeMetrics.calculateAbstractness(filepath)).append("\n");
        Planet planet = getPlanetByFilepath(filepath);
        if (planet != null) {
            sb.append("\n\nFields:\n");
            sb.append("----------------\n");
            List<String> fields = planet.getFields();
            if (fields != null) {
                for (String field : fields) {
                    sb.append(field).append("\n");
                }
            } else {
                sb.append("No fields available\n");
            }
            sb.append("\nMethods:\n");
            sb.append("----------------\n");
            Map<String, String> methods = planet.getMethods();
            if (methods != null) {
                for (Map.Entry<String, String> method : methods.entrySet()) {
                    sb.append(method.getKey()).append("\n");
                }
            } else {
                sb.append("No methods available.\n");
            }
        }
        return sb.toString();
    }
    private static Planet getPlanetByFilepath(String filepath) {
        for (Planet planet : planets) {
            if (planet.getFilepath().equals(filepath)) {
                return planet;
            }
        }
        return null;
    }
    public static Map<String, Boolean> getMethodUsage(String filepath) throws IOException{
        return CodeMetrics.analyseMethods(filepath);
    }
}
