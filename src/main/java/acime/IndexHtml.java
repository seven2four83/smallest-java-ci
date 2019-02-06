package acime;
import java.util.*;

/**
   Build HTML for the index of build and the index of test.
**/
public class IndexHtml {
    /**
       Given a list of identifiers (hash+date is the typical identifier in our application)
       and an offset route (such as build/) generate a HTML index list.
       @param identifiers The list of identifiers to create indices for
       @param route The route the indices will be based upon.
     **/
    public String indexBuild(List<String> identifiers, String route) {
	StringBuilder html = new StringBuilder("<ul>");
	for(String id : identifiers) {
	    html.append("<li>"+"<a href="+route+id+">"+id+"</a></li>");
	}
	html.append("</ul>");
	return html.toString();
    }
}
