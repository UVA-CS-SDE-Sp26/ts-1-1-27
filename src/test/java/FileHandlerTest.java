import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @TempDir
    Path tempDir;
    Path dataDir;
    String oldProp;

    // set up a fresh temp data folder and point FileHandler at it
    @BeforeEach
    void setup() throws Exception {
        oldProp = System.getProperty("topsecret.dataDir");
        dataDir = tempDir.resolve("data");
        Files.createDirectory(dataDir);
        System.setProperty("topsecret.dataDir", dataDir.toString());
    }

    // clean up the property so other tests don't get affected
    @AfterEach
    void tearDown() {
        if (oldProp == null) System.clearProperty("topsecret.dataDir");
        else System.setProperty("topsecret.dataDir", oldProp);
    }

    // helper to create a file inside temp data/
    private void writeFile(String name, String contents) throws Exception {
        Files.writeString(dataDir.resolve(name), contents);
    }

    // reads a real file and checks we get the exact contents back
    @Test
    void testGetFileContentsReadsFile() throws Exception {
        writeFile("a.txt", "hello");
        assertEquals("hello", FileHandler.getFileContents("a.txt"));
    }

    // missing file should return the error string (not crash)
    @Test
    void testGetFileContentsMissingFile() {
        assertEquals("ERROR: file not found", FileHandler.getFileContents("missing.txt"));
    }

    // makes sure getFileList actually returns the names of files in data/
    @Test
    void testGetFileListReturnsFiles() throws Exception {
        writeFile("a.txt", "a");
        writeFile("b.txt", "b");

        String[] files = FileHandler.getFileList();
        assertTrue(Arrays.asList(files).contains("a.txt"));
        assertTrue(Arrays.asList(files).contains("b.txt"));
    }

    // if the data folder doesn't exist, we should fail gracefully
    @Test
    void testNoDataFolderBehavior() {
        System.setProperty("topsecret.dataDir", tempDir.resolve("nope").toString());

        assertEquals(0, FileHandler.getFileList().length);
        assertEquals("ERROR: data folder not found", FileHandler.getFileContents("anything.txt"));
    }

    // the key overload should still work even if we ignore the key for now
    @Test
    void testKeyOverloadStillWorks() throws Exception {
        writeFile("a.txt", "hello");
        assertEquals("hello", FileHandler.getFileContents("a.txt", "ignored"));
    }

    // empty file should just return an empty string
    @Test
    void testGetFileContentsEmptyFile() throws Exception {
        writeFile("empty.txt", "");
        assertEquals("", FileHandler.getFileContents("empty.txt"));
    }

    // multi-line file should keep the newlines in the right places
    @Test
    void testGetFileContentsMultiLine() throws Exception {
        writeFile("multi.txt", "line1\nline2\nline3");
        assertEquals("line1\nline2\nline3", FileHandler.getFileContents("multi.txt"));
    }

    // getFileList should ignore subfolders and only return files
    @Test
    void testGetFileListIgnoresDirectories() throws Exception {
        Files.createDirectory(dataDir.resolve("subfolder"));
        writeFile("a.txt", "a");

        String[] files = FileHandler.getFileList();
        assertTrue(Arrays.asList(files).contains("a.txt"));
        assertFalse(Arrays.asList(files).contains("subfolder"));
    }

    // asking for a folder name should behave like "file not found"
    @Test
    void testGetFileContentsDirectoryNameReturnsError() throws Exception {
        Files.createDirectory(dataDir.resolve("folder"));
        assertEquals("ERROR: file not found", FileHandler.getFileContents("folder"));
    }
}
