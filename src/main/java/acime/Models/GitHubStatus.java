package acime.Models;

import javax.swing.plaf.ButtonUI;

/**
 * A model class for GitHub status(https://developer.github.com/v3/repos/statuses/)
 *
 * Uses builder pattern for ease of construction.
 */

public class GitHubStatus {

    String state, target_url, description, context;

    private GitHubStatus(){
        //private constructor
    }

    public static class Builder {
        String state, target_url, description, context;

        public Builder(String state){
            this.state = state;
        }

        public Builder withTargetURL(String targetURL){
            this.target_url = targetURL;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public  Builder withContext(String context){
            this.context = context;
            return this;
        }

//        public  Builder withcommitURL(String commitURL){
//            this.commitURL = commitURL;
//            return this;
//        }

        /**
         * Function that composes all variables of GHStatus from the counterparts in the builder
         * @return a GitHubStatus object with values as set in the Builder object
         */
        public GitHubStatus build(){
            GitHubStatus status = new GitHubStatus();
            status.state = this.state;
            status.context = this.context;
            status.description = this.description;
            status.target_url = this.target_url;
//            status.commitURL = this.commitURL;
            return status;
        }

    }
}

