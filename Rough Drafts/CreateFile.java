import java.util.*;
import java.io.*;
import java.nio.*;

public class CreateFile {
    Scanner input = new Scanner(System.in);
    IO.println("Please provide the directory path for the files you want to append.");
    String path = input.nextLine();
    IO.println("Please provide a name for your new file.");
    String fileName = input.nextLine();
    IO.println("Select a file type for your new file. (.txt/.docx/.odt)");
    String fileType = input.nextLine();

    public void main(String[] args) throws FileNotFoundException {
        File fileDoc = new File(path + fileName + fileType);
        try {
            if (fileDoc.createNewFile()) {
                IO.println("The file has been created.");
            } else {
                IO.println("The file already exists.");
            }
        } catch (IOException e) {
            IO.println("An error occurred.");
        }
    }
    public void createFile() throws FileNotFoundException {
        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            IO.println("File Not Found");
            return;
        }
        File[] files = f.listFiles();
        if (files != null && f.canRead()) {
            for (File file : files) {
                if (file.isFile()) {
                    IO.println("File: " + file.getName());
                } else {
                    IO.println("File Not Found");
                    return;
                }
            }
        }
        IO.println("Would you like to append the listed files into new file? (Y/N)");
        String answer = input.nextLine();
        if (answer.equalsIgnoreCase("Y")) {
            new BufferedReader(new InputStreamReader(fileDoc));

        }
    }
}
