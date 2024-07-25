package org.example;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/** @author Nashali Vicente Lopez **/
public class MetricsPanel extends JPanel {
    private JTextArea metricsArea;

    public  MetricsPanel(){
        setLayout(new BorderLayout());
        metricsArea = new JTextArea();
        metricsArea.setEditable(false);
        metricsArea.setFont(new Font("Arial", Font.PLAIN, 15));
        metricsArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(metricsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(300,600));
    }

    public void updateMetrics(Planet planet){
        try {
            String metrics = Officer.getMetrics(planet.getFilepath());
            StringBuilder sb = new StringBuilder(metrics);

            sb.append("\nFields:\n");
            List<String> fields = planet.getFields();
            if (fields != null) {
                for (String field : fields) {
                    sb.append(field).append("\n");
                }
            } else {
                sb.append("No fields available\n");
            }

            sb.append("\nMethods:\n");
            List<String> methods = planet.getMethods();
            if (methods != null) {
                for (String method : methods) {
                    sb.append(method).append("\n");
                }
            } else {
                sb.append("No methods available.\n");
            }
            metricsArea.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
