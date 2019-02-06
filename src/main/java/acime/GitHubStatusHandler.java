package acime;

import acime.Models.GitHubStatus;

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
        return false;
    }
}
