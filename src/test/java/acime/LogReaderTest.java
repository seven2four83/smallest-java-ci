package acime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class MockFactory implements ReaderFactory {
    File f;

    public FileReader makeReader(File f) throws IOException {
        this.f = f;
        return new FileReader(f);
    }
}

public class LogReaderTest {

    private static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

    /**
       Check that filename is the hash + date.
     **/
    @Test
    void correctFilename() {
        String hash = "abc";
        String date = "123";
        File file = new File(".", hash+date);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MockFactory mf = new MockFactory();
        LogReader lr = new LogReader(".", mf);
        lr.read(hash, date);
        Assertions.assertEquals(hash+date, mf.f.getName());
        file.delete();
    }

    /**
       Check that file read is in correct directory.
     **/
    @Test
    void correctDirectory() {
        File dir = new File(".", "testFolder");
        File file = new File(dir.getPath(), "irrelevant");

        try {
            dir.mkdir();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MockFactory mf = new MockFactory();
        LogReader lr = new LogReader(dir.getPath(), mf);
        lr.read("irrelevant");
        Assertions.assertEquals("./" + dir.getName(), mf.f.getParent());
        Assertions.assertTrue(deleteDirectory(dir));
    }

    /**
        Check that the content in specific file is right
     **/
    @Test
    void correctContentFile() {
        String dir = ".";
        String testString = "testingtesting";
        File file = new File( dir, "irrelevant");

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(testString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MockFactory mf = new MockFactory();
        LogReader lr = new LogReader(dir, mf);
        Assertions.assertEquals(testString, lr.read("irrelevant"));
        Assertions.assertEquals(dir, mf.f.getParent());
        Assertions.assertTrue(file.delete());
    }

    /**
     Check that the content in specific file is right
     **/
    @Test
    void correctContentFirst() {
        File dir = new File(".", "testFolder");
        File file = new File( dir, "irrelevant");
        String testString = "testingtesting";
        try {
            dir.mkdir();
            FileWriter writer = new FileWriter(file);
            writer.write(testString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MockFactory mf = new MockFactory();
        LogReader lr = new LogReader("./"+dir.getName(), mf);
        Assertions.assertEquals(testString, lr.readFirst());
        Assertions.assertEquals(dir.getPath(), mf.f.getParent());
        Assertions.assertTrue(deleteDirectory(dir));
    }

    /**
       Check that the content in all files is right
     **/
    @Test
    void correctContentAll() throws IOException {
        File dir = new File(".", "testFolder");
        File file1 = new File( dir, "nr1");
        File file2 = new File( dir, "nr2");
        File file3 = new File( dir, "nr3");

        try {
            dir.mkdir();
            FileWriter writer;

            writer = new FileWriter(file1);
            writer.write("1");
            writer.close();

            writer = new FileWriter(file2);
            writer.write("2");
            writer.close();

            writer = new FileWriter(file3);
            writer.write("3");
            writer.close();
        } catch (IOException e) {
	    throw e;
        }


        MockFactory mf = new MockFactory();
        LogReader lr = new LogReader(dir.getPath(), mf);
        String[] result = lr.readAll();
/*
        Assertions.assertEquals(3, result.length);
        //Assertions.assertEquals("3", result[0]);
        //Assertions.assertEquals("2", result[1]);
        //Assertions.assertEquals("1", result[2]);
        Assertions.assertEquals(dir.getPath(), mf.f.getParent());
        Assertions.assertTrue(deleteDirectory(dir));
*/
    }
}
