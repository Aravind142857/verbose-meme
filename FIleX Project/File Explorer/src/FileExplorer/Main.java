package FileExplorer;

import javax.swing.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static ArrayList<JFrame> windows = new ArrayList<>();
    private static int windowNumber = 1;
    public final String prompt;
    private String windowName = "";
    private static JFrame curr = new JFrame();
    private final FileX explorer = new FileX();
    public Main() {
        prompt = "BRUH$ |" + explorer.getWorkingDirectory() + "| ";
        //this.windowName = "Window " + windowNumber + " of " + windowNumber;
    }
    public void renameWindows() {
        for (JFrame window: windows) {
            window.setName(window.getName().replaceAll("(of\s[0-9]*)", "of " + String.valueOf(windowNumber)));
        }
    }
    public void addWindow(JFrame frame) {
        windows.add(frame);
        renameWindows();
        curr = windows.get(windows.size() - 1);
    }
    public void deleteWindow(int windowNum) {
        /* Remove windowNum */
        windowNum--;
        /* Rename all windows */
    }

    public String runCommand(String command) {

        //String command;
        String prompter = prompt + "\s";
        System.out.print(prompter + "|" + explorer.getWorkingDirectory() + "| ");
        //Scanner read = new Scanner(System.in);
        //read.useDelimiter("\n");
        //command = read.next();
        if (command.equals("quit()")) {
            return null;
        }
        try {
            return explorer.convert(command);
        } catch (MyException e) {
            return e.message();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    /* public static void main(String[] args) throws InterruptedException {
        Main newWindowCreator = new Main();
        String[] welcomeMessages = new String[]{"Well Hello there, " + System.getProperty("user.name").substring(0,1).toUpperCase() + System.getProperty("user.name").substring(1).toLowerCase() + "!", "Your wish is my Command (literally), " + System.getProperty("user.name").substring(0, 1).toUpperCase() + System.getProperty("user.name").substring(1).toLowerCase() + "!", "Where to? O Captain! My Captain!", "Here we go!", "Issa me, FileExplorer.FileX!", "To the root directory and beyond!", "What's a see colon?!", "BRUH!", "I WILL GET YOU!", "HEHE! There's no escaping me now!", "See you in the next directory!", "UNLIMITED POWER!!", "Your system is being hacked"};
        Random rand = new Random();
        String message = welcomeMessages[rand.nextInt(welcomeMessages.length)];
        if (message.equals("I WILL GET YOU!") || message.equals("Your system is being hacked")) {
            for (String dummy: message.split(" ")) {
                System.out.print(dummy + " ");
                TimeUnit.SECONDS.sleep(1);
            }
            if (message.equals("Your system is being hacked")) {
                for (int i = 0; i < 3; i++) {
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.print("\b\b\b");
                for (int i = 0; i < 3; i++) {
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            System.out.print("\n");
        } else {
            System.out.println(message);
        }
        //System.out.println("Hello there, " + System.getProperty("user.name").substring(0,1).toUpperCase() +System.getProperty("user.name").substring(1).toLowerCase() + "!");
        //String resume = newWindowCreator.runCommand();
        //if (resume == null) {
        //    return "";
        //}
        //else {
        //    return(resume);
        //}
        //return("File Explorer terminated.");
    }

     */

}
