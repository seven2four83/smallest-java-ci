# Acime

Acime, the "Awesome CI server" is a small HTTP-server which builds Gradle projects and interacts with Github as a continuous integration server.


# Documentation

The system is documented in this README and should have fairly complete Javadoc strings.
To build the documentation use:
```
gradle javadoc
```

The resulting documentation will be available in build/docs/javadoc/ as HTML files.

# Dependencies

All dependencies are defined in the file build.gradle and will be automatically downloaded when built.
The project uses JUnit for testing and Jetty as a HTTP server.

# Installing and running

Ensure that Gradle (>= 5.1.1) and OpenJDK (>= 1.8) are installed and run the following instructions in your favourite terminal:

```
git clone
cd smallest-java-ci
gradle build
```

To start the server run
```
gradle run
```
and the server will be available at port 8080!

# Structure and contributing

If you want to implement a new feature you probably want to add a new route.
To do this alter the code in ContinuousIntegrationServer\#handle similar to the way that the current code is.

We expect you to at least supply unit tests to any substantial changes to the codebase.

The structure of what you should do for a new feature looks like this:

1. Make a new issue on Github
2. Assign yourself to it
3. Make a branch such as feat\_issuenumber\_shortdescription
4. Commit your code with a message mentioning the issue number
5. Open a pull request referencing the issue number
6. Await code-review

# Contributions

## Johan Sjölén
- Set up Gradle, JUnit, Jetty, Servlets
- Wrote the HTTP routing
- Wrote the Gradle communication interface
- Wrote some JSON parsing and extraction (uses the Jetty JSON parser :-))
- Wrote the file system writer class
- Wrote some basic HTML generation for the indexes (to show a list of builds)
- Did some debugging of others code
- Did some code reviews
- Wrote this README (except the contributions)
- Write some HTTP requests (along with basic Github POSt request)

## Nikhil Modak
- Wrote Git Communication Interface
- Wrote most of Webhook Handler method which invokes the Git + Gradle interfaces
- Troubleshot + Debugged outstanding issues
- Did Code Reviews
