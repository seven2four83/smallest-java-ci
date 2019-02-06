package acime;

import acime.Models.GitHubStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

    public boolean postStatus(){
//        try {
//            URL statusURL = new URL("/https://github.com/repos/jsjolen/smallest-java-ci/statuses/e5e60bcefdd1dccbfd78cf419b539f4a9f04dcdb");
//            URLConnection connection = statusURL.openConnection();
//            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
//            httpsURLConnection.setRequestMethod("POST");
//            httpsURLConnection.setDoInput(true);
//            StringEntity params = new StringEntity()
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            HttpPost request = new HttpPost("https://github.com/repos/jsjolen/smallest-java-ci/statuses/e5e60bcefdd1dccbfd78cf419b539f4a9f04dcdb");
//            StringEntity params =new StringEntity("{\"state\":\"pending\",\"target_url\":\"https://example.com/build/status\",\"description\":\"Trial!\",\"context\":\"continuous-integration/jenkins\"}");
            StringEntity params =new StringEntity("{\"state\":\"pending\",\"description\":\"Trial!\"}");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity resposeEntity = response.getEntity();

            //handle response here...

        }catch (Exception ex) {
            ex.printStackTrace();
            //handle exception here

        } finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }

        return false;

    }
}
