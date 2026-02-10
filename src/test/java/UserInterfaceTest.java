import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;

class UserInterfaceTest {

    @Test
    public void testNoArguments() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            mockedPC.when(() -> ProgramControl.getFile())
                    .thenReturn("File list");

            String[] args = {};
            UserInterface.parseArgs(args);

            // Verify the method was called exactly once
            mockedPC.verify(() -> ProgramControl.getFile(), times(1));
        }
    }

    @Test
    public void testFileNumberCallsCorrectly() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            mockedPC.when(() -> ProgramControl.getFile(anyInt()))
                    .thenReturn("File contents");

            String[] args = {"3"};
            UserInterface.parseArgs(args);

            // Verify it was called with 3 as arg
            mockedPC.verify(() -> ProgramControl.getFile(3), times(1));
        }
    }

    @Test
    public void testFileNumberCallsCorrectlyWithLeadingZero() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            mockedPC.when(() -> ProgramControl.getFile(anyInt()))
                    .thenReturn("File contents");

            String[] args = {"03"};
            UserInterface.parseArgs(args);

            // Verify it was called with 3 as arg
            mockedPC.verify(() -> ProgramControl.getFile(3), times(1));
        }
    }

    @Test
    public void testTwoArgumentsCallsGetFileWithBothParameters() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            mockedPC.when(() -> ProgramControl.getFile(anyInt(), anyString()))
                    .thenReturn("Decrypted contents");

            String[] args = {"5", "caesar"};
            UserInterface.parseArgs(args);

            // Verify it was called with correct parameters
            mockedPC.verify(() -> ProgramControl.getFile(5, "caesar"), times(1));
        }
    }

    @Test
    public void testInvalidArgumentDoesNotCallGetFile() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            String[] args = {"notANumber"};
            UserInterface.parseArgs(args);

            // Verify getFile was never called
            mockedPC.verifyNoInteractions();
        }
    }

    @Test
    public void testInvalidDoubleArgumentDoesNotCallGetFile() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            String[] args = {"notANumber", "caesar"};
            UserInterface.parseArgs(args);

            // Verify getFile was never called
            mockedPC.verifyNoInteractions();
        }
    }

    @Test
    public void testTooManyArgumentsDoesNotCallGetFile() {
        try (MockedStatic<ProgramControl> mockedPC = mockStatic(ProgramControl.class)) {
            String[] args = {"1", "caesar", "extra"};
            UserInterface.parseArgs(args);

            // Verify no methods were called
            mockedPC.verifyNoInteractions();
        }
    }
}