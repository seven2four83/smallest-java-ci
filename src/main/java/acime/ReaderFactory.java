package acime;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
   ReadFactory produces new FileReaders for reading from files.
   Pro-tip:
   Can be used for creating mock FileReaders.
 **/
public interface ReaderFactory {
    /**
       Return reader for file.
       @param file File to open and read
     **/
    public FileReader makeReader(File file) throws IOException;
}
