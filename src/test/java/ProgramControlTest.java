import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ProgramControlTest {

    @Test
    void testZeroInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            mock.when(FileHandler::getFileList).thenReturn(true);

            ProgramControl.getFile();

            mock.verify(ProgramControl::getFile, times(1));
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
            ProgramControl.getFile(1);

            mock.verify(() -> FileHandler.getFileContents("test.txt"), times(1));
        }
    }

    @Test
    void testTwoInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            ProgramControl.getFile(1, "abcdefghijklmnopqrstuvxyz");

            mock.verify(() -> FileHandler.getFileContents("Sample", "abcdefghijklmnopqrstuvxyz"), times(1));
        }
    }
}