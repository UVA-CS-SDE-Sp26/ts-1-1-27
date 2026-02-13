public class ProgramControl {

    public static String getFile() {
        String[] files = FileHandler.getFileList();

        if (files.length == 0){
            return "No files found. Sorry :(";
        }

        StringBuilder build = new StringBuilder();

        for (int i = 0; i < files.length; i++){
            build.append(String.format("%02d %s%n", i+1, files[i]));
        }

        return build.toString();
    }

    public static String getFile(int fileNumber) {
        String[] files = FileHandler.getFileList();

        if (files == null || fileNumber >= files.length){
            return "Error -- File number too large";
        }

        return FileHandler.getFileContents(files[fileNumber-1]);
    }

    public static String getFile(int fileNumber, String cipher) {
        String[] files = FileHandler.getFileList();

        if (fileNumber >= files.length){
            return "Error -- File number too large";
        }

        return FileHandler.getFileContents(files[fileNumber-1], cipher);
    }
}