package acime;

import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.client.*;
import org.eclipse.jetty.client.api.*;
import org.eclipse.jetty.client.util.StringContentProvider;
import java.util.*;
import org.eclipse.jetty.http.HttpHeader;
import java.util.Base64.Encoder;

/**
   Send Github comments to the server.
 **/
public class GithubComment {
    public static boolean sendComment(String owner, String repo,
			       String issueNumber, String body) throws Exception {
	HttpClient httpClient = new HttpClient();
	Encoder encoder = Base64.getEncoder();
	httpClient.start();
	Request req = httpClient.POST("https://api.github.com/repos/"+owner+
				      "/"+repo+"/issues/"+issueNumber+"/comments");
	req.header(HttpHeader.ACCEPT, "application/vnd.github.v3+json");
	req.header(HttpHeader.CONTENT_TYPE, "application/json");
	req.header(HttpHeader.USER_AGENT, "smallestjavaci");
	req.header(HttpHeader.AUTHORIZATION, "Basic c21hbGxlc3RqYXZhY2k6ZXZlcnl0aGluZ2lzaGVyZTEyMw==");
	StringBuilder json = new StringBuilder();
	Map<String, String> m = new HashMap<String,String>();
	m.put("body", body);
	JSON jsonBuilder = new JSON();
	jsonBuilder.appendMap(json, m);
	req.content(new StringContentProvider(json.toString()), "application/json");
	ContentResponse cr = req.send();
	System.out.println(cr.getContentAsString());
	return true;
    } 
}
