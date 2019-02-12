package acime;

import java.io.*;
import java.io.File;
import java.io.FileReader;

/**
   Basic WriterFactory
 **/
public class FileReaderFactory implements ReaderFactory {
    public FileReader makeReader(File file) throws IOException {
	return new FileReader(file);
    }
}
