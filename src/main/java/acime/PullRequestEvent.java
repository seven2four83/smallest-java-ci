package acime;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import org.eclipse.jetty.util.ajax.JSON;


/**
   Represents the PullRequestEvents sent from Github
   to the server.
 **/
public class PullRequestEvent {
    /**
       The JSON object after parsing (untyped).
     **/
    Object jsonObject;
    /**
       Throws if it is discovered that the object is incorrect.
     **/
    public PullRequestEvent(String body) throws Exception {
	this.jsonObject = JSON.parse(body);
	if(!(this.jsonObject instanceof Map)) {
	    throw new Exception("Top-object is not JSON dictionary.");
	}
    }
    /**
       Get the hash of the HEAD of the branch that the pull request originates from.
     **/
    public String getHeadHash() {
	Object pullReq = ((Map<String, String>)jsonObject).get("pull_request");
	Object head = ((Map<String, Object>)pullReq).get("head");
	String hash = ((Map<String, String>)head).get("sha");
	return hash;
    }
    /**
       Get the clone_url of the repository that the pull request originates from.
     **/
    public String getCloneURL() {
        Object repository = ((Map<String, String>)jsonObject).get("repository");
        String clone_url = ((Map<String, String>)repository).get("clone_url");
        return clone_url;
    }
    
}
