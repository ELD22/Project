import java.util.*;
import java.io.*;

public class test {
    public void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        IO.println("Please provide the directory path for the files you want to append.");
        String path = input.nextLine();
        IO.println("Please provide a name for your new file.");
        String fileName = input.nextLine();
        IO.println("Select a file type for your new file. (.txt/.docx/.odt)");
        String fileType = input.nextLine();

        if (fileType.equals(".txt")) {
            String newDoc = fileName + fileType;
            File fileDoc = new File(path + newDoc);
        }
    }
}
