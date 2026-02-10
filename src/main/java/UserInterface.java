public class UserInterface {

    public static String parseArgs(String[] args){
        int numArgs = args.length;

        switch(numArgs){
            case 0:
                //No-args case, call for file list and return string
                return ProgramControl.getFile();

            case 1:
                //1-arg case, need to validate as int
                try{
                    int fileNumber = Integer.parseInt(args[0]);
                    return ProgramControl.getFile(fileNumber);
                } catch (NumberFormatException e){
                    return "Invalid argument supplied\nUsage: TopSecret [file_number] [cipher]";
                }

            case 2:
                //2-arg case, int index and cipher
                try{
                    int fileNumber = Integer.parseInt(args[0]);
                    String cipher = args[1];
                    return ProgramControl.getFile(fileNumber, cipher);
                } catch (NumberFormatException e){
                    return "Invalid argument supplied\nUsage: TopSecret [file_number] [cipher]";
                }

            default:
                return "Too many arguments\nUsage: TopSecret [file_number] [cipher]";
        }
    }
}