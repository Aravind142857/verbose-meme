package FileExplorer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class FileX implements myExplorer {
    private String currPath;
    private String currDirectory;
    private String error1 = "File is not a directory";
    private String error2 = "File not found";
    public FileX() {
        currPath = System.getProperty("user.home");
        currDirectory = "~";
    }

    /**
     * Returns the Current Working Directory
     */
    @Override
    public String getWorkingPath() {
        return currPath;
    }

    @Override
    public String getWorkingDirectory() {
        return currDirectory;
    }
    /**
     * Changes the current working directory to
     *
     * @param newPath
     */
    @Override
    public void changeWorkingDirectory(String newPath){
            currPath = newPath;
            if (newPath.equals(System.getProperty("user.home"))) {
                currDirectory = "~";
            }
            else {
                currDirectory = newPath.substring(newPath.lastIndexOf("/") + 1);
                System.out.println(currDirectory);
            }

    }

    /**
     * Moves inward and sets current Working Directory to @param inLoc
     *
     * @param inLoc
     */
    @Override
    public void moveInward(String inLoc) throws MyException {
        if (inLoc.charAt(0) == '/') {
            inLoc = inLoc.substring(1);
        }
        String newCurr = getWorkingPath() + "/" + inLoc;
        newCurr = (newCurr.lastIndexOf("~") >= 0) ? (newCurr.substring(newCurr.lastIndexOf("~")).replaceAll("~", System.getProperty("user.home"))):(newCurr);
        File fp = new File(newCurr);
        if (fp.isDirectory()) {
            if (inLoc.charAt(0) == '~') {
                inLoc = inLoc.substring(2);
                toRoot();
            }
            changeWorkingDirectory(getWorkingPath() + "/" + inLoc.replaceAll("[.][/]", ""));
        }
        else if (fp.exists()){
            throw new MyException("cd", error1, fp);
        }
        else {
            throw new MyException("cd", error2, fp);
        }
    }

    /**
     * Moves outward and sets current Working Directory to inLoc
     *
     */
    @Override
    public void moveOutward() {
        changeWorkingDirectory(currPath.substring(0, currPath.lastIndexOf("/")));
    }

    /**
     * Return a list of all files in the current directory
     * @return
     */
    @Override
    public File[] getFiles(String currPath) {
        File currFile = new File(currPath);
        File[] toReturn = currFile.listFiles();
        if (toReturn != null) {
            Arrays.sort(toReturn);
        }
        return toReturn;
    }

    /**
     * Prints all files in the current directory
     *
     * @param currentPath
     * @return
     */
    @Override
    public String print(String currentPath) throws IOException {
        File[] list = getFiles(currentPath);
        String toReturn = "";
        for (File f: list) {
            toReturn += format(f) + "\n";
        }
        toReturn = toReturn.substring(0, toReturn.length() - 1);
        return toReturn;
    }

    /**
     * Prints all files in current and sub-directories
     *  @param beginning
     * @param currentPath Current Path
     * @return
     */
    @Override
    public String printAll(String beginning, String currentPath) throws IOException {
        String toReturn = "";
        File[] files = getFiles(currentPath);
        if (files != null) {
            for (File temp : files) {
                toReturn += format(beginning, temp) + "\n";
                if (temp.isDirectory() && !temp.isHidden()) {
                    toReturn += printAll(beginning + "\t", String.valueOf(temp));
                }
            }
        }
       /*
        if (toReturn.length() > 1) {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
            return toReturn;
        }
        */
        return toReturn;
    }

    /**
     * Formats each String while outputting
     *
     * @param f
     */
    private String format(File f) throws IOException {
        return format("", f);
    }

    private String format(String start, File f) throws IOException {
        String ANSI_RESET = "u001B[0m";

        String ANSI_BLACK = "u001B[30m";
        String ANSI_RED = "u001B[31m";
        String ANSI_GREEN = "u001B[32m";
        String ANSI_YELLOW = "u001B[33m";
        String ANSI_BLUE = "u001B[34m";
        String ANSI_PURPLE = "u001B[35m";
        String ANSI_CYAN = "u001B[36m";
        String ANSI_WHITE = "u001B[37m";

        String BRIGHT_BLACK = "u001B[1;30m";
        String BRIGHT_RED = "u001B[1;31m";
        String BRIGHT_GREEN = "u001B[1;32m";
        String BRIGHT_YELLOW = "u001B[1;33m";
        String BRIGHT_BLUE = "u001B[1;34m";
        String BRIGHT_PURPLE = "u001B[1;35m";
        String BRIGHT_CYAN = "u001B[1;36m";
        String BRIGHT_WHITE = "u001B[1;37m";

        if (f.isHidden()) {
            return (start + ANSI_RED + f + ANSI_RESET);
        }

        else if (f.isDirectory()) {
            return (start + BRIGHT_BLUE + f.getCanonicalFile() + ANSI_RESET );
        }

        else if (f.isFile()) {
            return (start + BRIGHT_GREEN + f.getName() + ANSI_RESET);
        }
        else {
            return "";
        }
    }

    @Override
    public void toRoot() {
        changeWorkingDirectory(System.getProperty("user.home"));
    }

    @Override
    public String convert(String cmd) throws MyException, IOException {
        if (cmd.endsWith("/")) {
            cmd = cmd.substring(0, cmd.length() - 1);
        }
        if (cmd.matches("[c][d]\s.+")) {
            /* Call moveInward or changeWorkingDirectory or toRoot() or moveOutward*/
            if (cmd.substring(2).replaceAll("\s", "").equals("..")) {
                moveOutward();
            }
            else if (cmd.substring(2).replaceAll("\s", "").equals("~")) {
                toRoot();
            }
            else if (cmd.substring(2).replaceAll("\s", "").matches("\\.\\..*")) {
                moveOutward();
                moveInward(cmd.substring(5));
            }
            else { /* Move Inward */
                moveInward(cmd.substring(3));
            }
        }
        else if (cmd.matches("pwd")) {
            /* Call getWorkingDirectory() */
            //System.out.println(getWorkingPath());
            return ("u001B[1;35m" + getWorkingPath());
        }
        else if (cmd.replaceAll("\s", "").equals("ls")) {
            /* Call print(getWorkingDirectory()) */
            return print(getWorkingPath());

        }
        else if (cmd.equals("ls -aR")) {
            /* Call printAll(getWorkingPath()) */
            return printAll("\t", getWorkingPath());
        }
        else {
            // TODO: New Exception -- Invalid command (command not found: <Input>)
        }
        return "";
    }
}
/**
 * TODO:    Remove (rm).
 *          Tab to autocomplete (\t)
 *          Pipe to Compose methods (|)
 *          Grep to fetch (grep)
 *          Last Login, Save cache
 *          Save last N lines
 *
 *          Enable compiling java program to run a file within File Explorer from within terminal
 *
 *
 *          start making Web Browser
 *          Selenium?
 *          https://www.youtube.com/watch?v=3Zbn94NgjsA
 *          https://www.youtube.com/watch?v=Sl_Ub5m_zBw
 *
 */