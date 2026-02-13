import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ProgramControlTest {

    @Test
    void testZeroInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            String[] files = {"File list"};
            mock.when(FileHandler::getFileList).thenReturn(files);

            ProgramControl.getFile();

            mock.verify(FileHandler::getFileList, times(1));
        }
    }

    @Test
    void testInvalidOneInput() {
        int input = FileHandler.getFileList().length+1;
        assertEquals( "Error -- Enter a valid file number please", ProgramControl.getFile(input));
    }

    @Test
    void testValidOneInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            String[] files = {"File list"};
            mock.when(FileHandler::getFileList).thenReturn(files);
            mock.when(() -> FileHandler.getFileContents(anyString())).thenReturn("files");
            ProgramControl.getFile(1);

            mock.verify(() -> FileHandler.getFileContents("File list"), times(1));
        }
    }

    @Test
    void testTwoInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            String[] files = {"File list"};
            mock.when(FileHandler::getFileList).thenReturn(files);
            mock.when(() -> FileHandler.getFileContents(anyString())).thenReturn("foo");

            ProgramControl.getFile(1, "abcdefghijklmnopqrstuvxyz");

            mock.verify(() -> FileHandler.getFileContents("File list", "abcdefghijklmnopqrstuvxyz"), times(1));
        }
    }
}