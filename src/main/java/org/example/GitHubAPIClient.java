package org.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/** @author Veer Bhagia **/

public class GitHubAPIClient {
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";

    public static JsonArray fetchRepoCommits(String owner, String repo) throws Exception {
        String url = GITHUB_API_BASE_URL + "/repos/" + owner + "/" + repo + "/commits";
        return fetchJsonArray(url);
    }

    public static JsonArray fetchRepoBranches(String owner, String repo) throws Exception {
        String url = GITHUB_API_BASE_URL + "/repos/" + owner + "/" + repo + "/branches";
        return fetchJsonArray(url);
    }

    private static JsonArray fetchJsonArray(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/vnd.github.v3+json");
        HttpResponse response = client.execute(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        client.close();
        return JsonParser.parseString(stringBuilder.toString()).getAsJsonArray();
    }
}
