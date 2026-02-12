import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

class FileHandlerTest {

    private void writeFile(String name, String contents) throws Exception {
        File data = new File("data");
        data.mkdir();

        File f = new File(data, name);
        FileWriter fw = new FileWriter(f);
        fw.write(contents);
        fw.close();
    }

    private void cleanupDataFolder() {
        File data = new File("data");
        if (data.exists() && data.isDirectory()) {
            File[] files = data.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
            data.delete();
        }
    }

    @Test
    public void testGetFileContentsReadsFile() throws Exception {
        cleanupDataFolder();
        writeFile("a.txt", "hello");

        String result = FileHandler.getFileContents("a.txt");
        assertEquals("hello", result);

        cleanupDataFolder();
    }

    @Test
    public void testGetFileContentsMissingFile() throws Exception {
        cleanupDataFolder();
        new File("data").mkdir();

        String result = FileHandler.getFileContents("missing.txt");
        assertEquals("ERROR: file not found", result);

        cleanupDataFolder();
    }

    @Test
    public void testGetFileListReturnsFiles() throws Exception {
        cleanupDataFolder();
        writeFile("a.txt", "a");
        writeFile("b.txt", "b");

        String[] files = FileHandler.getFileList();

        // don’t assume order; just check they’re in there
        boolean hasA = false;
        boolean hasB = false;
        for (String s : files) {
            if (s.equals("a.txt")) hasA = true;
            if (s.equals("b.txt")) hasB = true;
        }

        assertTrue(hasA);
        assertTrue(hasB);

        cleanupDataFolder();
    }

    @Test
    public void testNoDataFolderBehavior() throws Exception {
        cleanupDataFolder(); // ensure no data folder exists

        String[] files = FileHandler.getFileList();
        assertEquals(0, files.length);

        String result = FileHandler.getFileContents("anything.txt");
        assertEquals("ERROR: data folder not found", result);
    }
}
