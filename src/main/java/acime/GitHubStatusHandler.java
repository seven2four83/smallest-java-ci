package acime;

import acime.Models.GitHubStatus;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.ajax.JSON;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages HitHub status.
 * Actions include
 *  -forming a post request with appropriate body as mentioned in https://developer.github.com/v3/repos/statuses/
 *  -handling response of the post request
 */


public class GitHubStatusHandler {
    private GitHubStatus status;

    /**
     *
     * @param status the GitHubStatus object to be posted to GitHub
     */
    GitHubStatusHandler(GitHubStatus status){
        this.status = status;
    }

    /**
     * Create a post request and send to GitHub
     *
     * @return a boolean if the the status was successfully posted
     */

    public boolean postStatus(String repoOwner, String repo, String hash, String user, String pass) throws Exception{
        org.eclipse.jetty.client.HttpClient httpClient = new HttpClient();
        httpClient.start();
        Request req = httpClient.POST("https://api.github.com/repos/"+repoOwner+"/"+repo+"/statuses/"+hash);
        req.header(HttpHeader.ACCEPT, "application/vnd.github.v3+json");
        req.header(HttpHeader.CONTENT_TYPE, "application/json");
        req.header(HttpHeader.USER_AGENT, user);
        req.header(HttpHeader.AUTHORIZATION, "Basic "+pass);
        String statusJson = new Gson().toJson(status);
        req.content(new StringContentProvider(statusJson), "application/json");
        ContentResponse cr = req.send();
        System.out.println(cr.getContentAsString());
        return true;
    }
}
