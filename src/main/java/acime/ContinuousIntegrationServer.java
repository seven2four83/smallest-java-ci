package acime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.*;

import acime.Models.GitHubStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ajax.*;

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
        private String htmlPostamble = "</body></html>";
    /**
       String that should be emitted after HTML is emitted.
     **/
	/**
	 * List of identifiers representing each unique build.
	 */
	private List<String> buildIdentifiers = new ArrayList<>();

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
//	    boolean shouldCreateGitHubStatus = true;

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
//	    if(shouldCreateGitHubStatus){
	    	//TODO: Check where SHA is parsed
//			GitHubStatus status = new GitHubStatus.Builder("trial").build();
//			GitHubStatusHandler ghStatusHandler = new GitHubStatusHandler(status);
//			ghStatusHandler.postStatus();
//		}
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
				GitRunner gr = new GitRunner(new BasicRuntimeFactory());
				GradleComm gc = new GradleComm(new BasicRuntimeFactory());
				try {
					PullRequestEvent pre = new PullRequestEvent(body);
					String commitHash = pre.getHeadHash();
					String cloneURL = pre.getCloneURL();
					String timestamp = Long.toString(new Timestamp(System.currentTimeMillis()).getTime());
					String identifier = commitHash+timestamp;
					buildIdentifiers.add(identifier);

					response.getWriter().println(commitHash);
					response.getWriter().println(cloneURL);


					
					File repoContainer = gr.cloneRepo(cloneURL);
					File repository = new File(repoContainer+"/"+repoContainer.list()[0]);
					gr.checkoutCommit(repository, pre.getHeadHash());
					String buildOutput = gc.compileAt(repository);
					String testOutput = gc.testAt(repository);

					File buildLogsDir = new File(System.getProperty("user.dir")+"/builds");
					File testLogsDir = new File(System.getProperty("user.dir")+"/tests");
					if (!buildLogsDir.exists()) buildLogsDir.mkdirs();
					if (!testLogsDir.exists()) testLogsDir.mkdirs();

					LogWriter buildWriter = new LogWriter(buildLogsDir.getPath(), new FileWriterFactory());
					LogWriter testWriter = new LogWriter(testLogsDir.getPath(), new FileWriterFactory());

					buildWriter.log(buildOutput, commitHash, timestamp);
					testWriter.log(testOutput, commitHash, timestamp);
					try {
					    // Hard coding these things.
					    GithubComment.sendComment("jsjolen", "smallest-java-ci",
								      "46", buildOutput);
					    Thread.sleep(5);
					    // Hard coding these things.
					    GithubComment.sendComment("jsjolen", "smallest-java-ci",
								      "46", testOutput);
					}
					catch(Exception e) {
					    System.out.println(e);
					}
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
