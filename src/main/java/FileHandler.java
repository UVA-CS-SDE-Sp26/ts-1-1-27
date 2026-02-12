import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    // need to figure out how exactly we should do this part
    private static final String DEFAULT_KEY = "default";

    private static File findDataFolder() {
        File data = new File("data");
        if (data.exists() && data.isDirectory()) return data;

        File dataUp = new File("data");
        if (dataUp.exists() && dataUp.isDirectory()) return dataUp;

        return null;
    }

    public static String[] getFileList() {
        File dataFolder = findDataFolder();
        if (dataFolder == null) {
            return new String[0];
        };

        File[] files = dataFolder.listFiles();
        if (files == null) {
            return new String[0];
        }

        ArrayList<String> names = new ArrayList<>();
        for (File f : files) {
            if (f.isFile()){
                names.add(f.getName());
            }
        }

        return names.toArray(new String[0]);
    }

    public static String getFileContents(String fileName) {
        return getFileContents(fileName, DEFAULT_KEY);
    }

    public static String getFileContents(String fileName, String key) {
        File dataFolder = findDataFolder();
        if (dataFolder == null) {
            return "ERROR: data folder not found";
        }

        File file = new File(dataFolder, fileName);
        if (!file.exists() || !file.isFile()) {
            return "ERROR: file not found";
        }

        String text = "";
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                text += sc.nextLine();
                if (sc.hasNextLine()) text += "\n";
            }
        } catch (FileNotFoundException e) {
            return "ERROR: file not found";
        }

        return text;
    }
    public static void main(String[] args) {
        String[] files = getFileList();
        if (files.length == 0) {
            System.out.println("(no files found)");
        } else {
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + files[i]);
            }
        }

        if (files.length > 0) {
            System.out.println(getFileContents(files[0]));
        }
    }
}


