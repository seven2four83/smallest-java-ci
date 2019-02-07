package acime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.*;

import acime.Models.GitHubStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ajax.JSON;

/** 
    Skeleton of a ContinuousIntegrationServer which acts as webhook
    See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    /**
       String that should be emitted before HTML is emitted.
     **/
    private String htmlPreamble = "<html><body>";
    /**
       String that should be emitted after HTML is emitted.
     **/
    private String htmlPostamble = "</body></html>";
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
	{
	    response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    baseRequest.setHandled(true);
	    // Jagan: not sure if there will be a request where GH status(notif) need not be created.
		// If exists, set this bool to false in appropriate if block
	    boolean shouldCreateGitHubStatus = true;

	    System.out.println(target);
	    /**
	       Dispatch on target.
	    **/
	    if(target.matches("/webhook")) {
		this.handleWebhook(target, baseRequest, request, response);
	    }
	    // Given a test/someSHA1Hash
	    // Dispatch to handleTestSingle
	    else if(target.matches("/tests/[0-9a-zA-Z]+")) {
		this.handleTestSingle(target, baseRequest, request, response);
	    }
	    // Given only test just present last 100 builds
	    else if(target.matches("/tests")) {
		this.handleTest(target, baseRequest, request, response);
	    }
	    // Given a build/someSHA1Hash
	    // Dispatch to handleBuildSingle
	    else if(target.matches("/builds/[0-9a-zA-Z]+")) {
		this.handleBuildSingle(target, baseRequest, request, response);
	    }
	    // Given only build just present last 100 builds
	    else if(target.matches("/builds")) {
		this.handleBuild(target, baseRequest, request, response);
	    }
	    else {
		response.getWriter().println("Nothing to see here.");
	    }
	    if(shouldCreateGitHubStatus){
	    	//TODO: Check where SHA is parsed
			GitHubStatus status = new GitHubStatus.Builder("trial").build();
			GitHubStatusHandler ghStatusHandler = new GitHubStatusHandler(status);
			ghStatusHandler.postStatus();
		}
	}
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
	{
	    Server server = new Server(8080);
	    server.setHandler(new ContinuousIntegrationServer()); 
	    server.start();
	    server.join();
	}
   
    /**
       Serves as an entry-point to Github's webhook mechanism.
       Checks the event and dispatches it to the appropriate class and method.
    **/
    void handleWebhook(String target, Request baseRequest,
		       HttpServletRequest request,
		       HttpServletResponse response)
	throws IOException, ServletException
	{
	    if(request.getMethod().equals("POST")) {
		// Check if JSON
		if(request.getHeader("Content-Type").equals("application/json")) {
		    // Collect parameters
		    String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		    try {
			PullRequestEvent pre = new PullRequestEvent(body);
			response.getWriter().println(pre.getHeadHash());
		    }
		    catch(Exception e) {
		    }
		}
	    }
	    else {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	    }
	}
    /**
       Serves as an entry-point to test presentation, returns an HTML representation of the test run defined by URL.
       
    **/
    void handleTestSingle(String target, Request baseRequest,
		    HttpServletRequest request,
		    HttpServletResponse response)
	throws IOException, ServletException
	{
	    String[] parts = target.split("/");
	    response.getWriter().println(htmlPreamble);
	    response.getWriter().println("Welcome to test, hash is: "+parts[parts.length-1]);
	    response.getWriter().println(htmlPostamble);
	}
    /**
       Serves as an entry-point to build presentation, returns an HTML representation of the build run defined by URL.
    **/
    void handleBuildSingle(String target, Request baseRequest,
		     HttpServletRequest request,
		     HttpServletResponse response)
	throws IOException, ServletException
	{
	    String[] parts = target.split("/");
	    response.getWriter().println(htmlPreamble);
	    response.getWriter().println("Welcome to build. hash is: "+parts[parts.length-1]);
	    response.getWriter().println(htmlPostamble);
	}
    /**
       Serves as an entry-point to build presentation, returns an HTML representation of the last 100 build runs.
    **/
    void handleBuild(String target, Request baseRequest,
		     HttpServletRequest request,
		     HttpServletResponse response)
	throws IOException, ServletException
	{
	    response.getWriter().println(htmlPreamble);
	    try {
		response.getWriter().println(this.presentFolderIndex(target));
	    }
	    catch(Exception e) {
		response.getWriter().println("Failed to load logs");
	    }
	    response.getWriter().println(htmlPostamble);
	}
    /**
       Serves as an entry-point to build presentation, returns an HTML representation of the last 100 test runs.
    **/
    void handleTest(String target, Request baseRequest,
		    HttpServletRequest request,
		    HttpServletResponse response)
	throws IOException, ServletException
	{
	    response.getWriter().println(htmlPreamble);
	    try {
		response.getWriter().println(this.presentFolderIndex(target));
	    }
	    catch(Exception e) {
		response.getWriter().println("Failed to load logs");
	    }
	    response.getWriter().println(htmlPostamble);
	}

    /**
       Given a target which is either "/builds" or "/tests", read all the logs from it
       and return the information as a string.
     **/
    public String presentFolderIndex(String target)
	throws IOException, Exception
	{
	    if(!target.equals("/builds") && !target.equals("/tests")) {
		throw new Exception("Wrong directory!");
	    }
	    FileReaderFactory frf = new FileReaderFactory();
	    LogReader logReader = new LogReader(System.getProperty("user.dir")+target, frf);
	    ArrayList<String> filenames = logReader.listDirectory();
	    IndexHtml builder = new IndexHtml();
	    return builder.indexBuild(filenames, target+"/");
	}
}
