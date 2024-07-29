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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/** @author Veer Bhagia **/
public class GitHubVisualizer extends JFrame {

    public GitHubVisualizer(String title, DefaultCategoryDataset dataset) {
        super(title);
        JFreeChart lineChart = ChartFactory.createLineChart(
                title,
                "Date",
                "Number of Commits",
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

        // Map to count commits per day
        Map<Date, Integer> commitsPerDay = new TreeMap<>();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        for (int i = 0; i < commits.size(); i++) {
            JsonObject commit = commits.get(i).getAsJsonObject();
            String dateStr = commit.get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").getAsString();
            try {
                Date date = inputFormat.parse(dateStr);
                // Normalize the date to remove the time part
                Date normalizedDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
                commitsPerDay.put(normalizedDate, commitsPerDay.getOrDefault(normalizedDate, 0) + 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Add data to dataset
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Date, Integer> entry : commitsPerDay.entrySet()) {
            String formattedDate = outputFormat.format(entry.getKey());
            dataset.addValue(entry.getValue(), "Commits", formattedDate);
        }
        GitHubVisualizer chart = new GitHubVisualizer("Commit History", dataset);
        chart.pack();
        chart.setVisible(true);
    }
}