package org.example;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
/** @author Veer Bhagia **/

public class GitHubVisualizer extends JFrame {
    public GitHubVisualizer(String title, DefaultCategoryDataset dataset) {
        super(title);
        JFreeChart lineChart = ChartFactory.createLineChart(
                title,
                "Time",
                "Commits",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public static void displayCommitHistory(String owner, String repo) throws Exception {
        JsonArray commits = GitHubAPIClient.fetchRepoCommits(owner, repo);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < commits.size(); i++) {
            JsonObject commit = commits.get(i).getAsJsonObject();
            String date = commit.get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").getAsString();
            dataset.addValue(i, "Commits", date);
        }
        GitHubVisualizer chart = new GitHubVisualizer("Commit History", dataset);
        chart.pack();
        chart.setVisible(true);
    }
}
