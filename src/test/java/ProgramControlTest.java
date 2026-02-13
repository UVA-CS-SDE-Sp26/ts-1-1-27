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
        assertEquals( "Error -- File number too large", ProgramControl.getFile(input));
    }

    @Test
    void testValidOneInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            ProgramControl.getFile(0);

            mock.verify(() -> FileHandler.getFileContents("Sample"), times(1));
        }
    }

    @Test
    void testTwoInput() {
        try (MockedStatic<FileHandler> mock = mockStatic(FileHandler.class)){
            mock.when(() -> FileHandler.getFileContents("Sample")).thenReturn(true);

            ProgramControl.getFile(0, "abcdefghijklmnopqrstuvxyz");

            mock.verify(() -> FileHandler.getFileContents("Sample", "abcdefghijklmnopqrstuvxyz"), times(1));
        }
    }
}