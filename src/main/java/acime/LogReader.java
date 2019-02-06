package acime;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Read tests from log file
 */
public class LogReader {
    private String directory;
    private ReaderFactory readerFactory;

    /**
       @param directory The directory where all logs will be read from
       @param readerFactory The factory which will provide FileReaders
     */
    public LogReader(String directory, ReaderFactory readerFactory) {
        this.directory = directory;
        this.readerFactory = readerFactory;
    }

    /**
     @param fileName The file name of the log file to be read
     @return A string containing all text data from the log file
     */
    public String read(String fileName) {

        try {
            File f = new File(this.directory, fileName);
            FileReader fr = this.readerFactory.makeReader(f);

            char[] charData = new char[(int) f.length()];
            fr.read(charData);

            return String.copyValueOf(charData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ERROR! File: " + fileName + " could not be found/read!";
    }

    /**
       @param hash The git hash of the log file to be read
       @param date The date of the log file to be read
       @return A string containing all text data from the log file
     */
    public String read(String hash, String date) {
        return read(hash+date);
    }

    /**
       @return A string containing the text of the latest created log file
     */
    public String readFirst() {
        File dir = new File(this.directory);
        File newest = null;
        long lastMod = Long.MIN_VALUE;

        File[] files = dir.listFiles(File::isFile);

        if(files == null) {
            return "ERROR! no log files in folder";
        }

        for(File file : files) {
            if(file.lastModified() > lastMod) {
                lastMod = file.lastModified();
                newest = file;
            }
        }

        if(newest != null) {
            return read(newest.getName());
        }
        return "ERROR! Newest file could not be found/read!";
    }

    /**
     @return A string array containing the text of the all log file ordered from newest to oldest
     */
    public String[] readAll() {
        File dir = new File(this.directory);
        File[] files = dir.listFiles(File::isFile);

        if(files == null) {
            return new String[0];
        }

        String[] output = new String[files.length];
        HashSet<File> ordered = new HashSet<>();

        while(ordered.size() < files.length) {
            File newest = null;
            long lastMod = Long.MIN_VALUE;

            for (File file : files) {
                if (file.lastModified() > lastMod && !ordered.contains(file)) {
                    lastMod = file.lastModified();
                    newest = file;
                }
            }

            if(newest == null) {
                break;
            }

            output[ordered.size()] = read(newest.getName());
            ordered.add(newest);
        }

        return output;
    }
    /**
       List the logs of the directory.
     **/
    public ArrayList<String> listDirectory() {
	File f = new File(this.directory);
	File[] files = f.listFiles();
	ArrayList<String> filenames = new ArrayList<String>();
	for(File file : files) {
	    if(file.isFile()) {
		filenames.add(file.getName());
	    }
	}
	return filenames;
    }
}
