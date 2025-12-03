import java.util.*;
import java.io.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.odftoolkit.simple.TextDocument;

public class TextCompiler {
    //This took a while, but eventually I realized I could copy what I did on 8.3 and the TimeSpan assignment.
    //Initially, I could not figure how to put everything in one program and reused the same variables for multiple methods.
    //Since, my primary objective was to create a path variable for the directory to obtain the files, that would end up being the basis of the entire program
    //That's why fields and constructors come into place and it changed my entire project. Also, a lot of this project was assisted by the autocorrect/helper feature of IntelliJ
    //Because I did not set these variables as final, but the prompt explained why that would help.
    private final Scanner input = new Scanner(System.in);
    private final String path;
    private final String fileName;
    private final String fileType;
    private File fileDoc;

    public TextCompiler() {
        //My first thought was to use scanner to ask the for the file name, then prompt the user to create a file matching name given.
        //Then I'd use the variable to isolate the created file from the folder and then create a variable for that specific file.
        //However, this changed because it wasn't user-friendly at all, which wasn't my intention as this project was meant for me to use
        //and I could create the file anytime and then use the program to append.
        IO.println("Please provide the directory path for the files you want to append.");
        path = input.nextLine();
        IO.println("Please provide a name for your new file.");
        fileName = input.nextLine();
        IO.println("Select a file type for your new file. (.txt/.docx/.odt)");
        fileType = input.nextLine();
    }
    public static void main(String[] args) throws FileNotFoundException {
        //This main goes back to the week 3 mini assignment where we had to use methods that call each other in their parameter
        //Not only did this help me solve the multiple files issue and combining everything into one .java file for ease of use.
        //Another guilty admission is that throughout this project. IntelliJ did recommend a lot of the error catches.
        //While this does not make me a better programmer, it did help me create this project which is going to be extremely useful for my personal use
        TextCompiler append = new TextCompiler();//creating the object
        append.makeNewFile();//calling the other methods
        try {
            append.appendFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void makeNewFile() {
        //This idea came from geeksforgeeks.org, as I wasn't sure how to create the new file using my scanner inputs.
        //It wasn't until I finally added the fields that I could get to this point.
        fileDoc = new File(path + File.separator + fileName + fileType);
        //This was my intention as shown in the original Diagram. To set the path, into creating a file, then checking
        //the file extension to choose the appropriate append method.
        try {
            if (fileDoc.createNewFile()) {//user-friendly feedback
                IO.println("The file has been created.");
            } else {
                IO.println("The file already exists.");
            }
        } catch (IOException e) {
            IO.println("An error occurred while creating the file.");
        }
    }
    public void appendFile() throws IOException {//once main completes the first part and creates the new file, we can now append everything in path.
        File p = new File(path);
        if (!p.exists() || !p.isDirectory()) {
            IO.println("File Not Found");
            return;
        }
        //These are all checks we used in module 5 to avoid errors. I believe the coinCount assignment we did recommended or required these along with a sentinal
        File[] files = p.listFiles();
        if (files != null && p.canRead()) {
            for (File file : files) {
                if (file.isFile()) {
                    IO.println("File: " + file.getName());
                    //In the end I could not include all the services I planned like word count or character count
                    //because each file type would need its own method for it, and it was difficult finding all the right verbiage for the imported libraries
                }
            }
        }
        IO.println("Would you like to append the listed files into a new file? (Y/N)");
        String answer = input.nextLine();
        if (answer.equalsIgnoreCase("Y")) {
            FileWriter writer = new FileWriter(fileDoc, true);
            assert files != null;
            for (File f : files) {
                if (!f.isFile()) continue;
                if (f.equals(fileDoc)) continue;
                //These are more checks to avoid errors. I did have an infinite loop occur prior to adding (f.equals(fileDoc))
                //Because the program would continuously try to append the newly created doc into itself. One of my test gave me a txt file that was 1.8 million kb

                String getFileExtension = f.getName().toLowerCase();
                //Here we can correctly get the extension with getName and match the right append method.
                if (getFileExtension.endsWith(".txt")) {
                    IO.println("Appending TXT file: " + getFileExtension);
                    appendTxt(f, writer);
                }
                else if (getFileExtension.endsWith(".docx")) {
                    IO.println("Appending DOCX file: " + getFileExtension);
                    appendDocx(f, writer);
                }
                else if (getFileExtension.endsWith(".odt")) {
                    IO.println("Appending ODT file: " + getFileExtension);
                    appendOdt(f, writer);
                }
            }
            writer.close();
            IO.println("Completed appending files into: " + fileDoc.getName());
        }else{
            IO.println("Try again later.");
        }
    }
    private void appendTxt(File f, FileWriter writer) throws IOException {
        //This was the easiest to do as there are many tutorials showing how to do this,
        //and the textbook provided me with the information too
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line + System.lineSeparator());
        }
        reader.close();
    }
    private void appendDocx(File f, FileWriter writer) {
        //appendDocx and Odt were insanely hard to figure out because I had no idea what did what for these packages
        //This is honestly where I had to search stackoverflow for answers and I did end up copying and pasting what others did.
        //While I should not admit it, I was going to cut this entirely because of that, but as I mentioned this project was going to be useful for me.
        try (FileInputStream copy = new FileInputStream(f);
             XWPFDocument doc = new XWPFDocument(copy)) {
            for (XWPFParagraph p : doc.getParagraphs()) {
                writer.write(p.getText() + System.lineSeparator());
            }
        } catch (Exception e) {
            IO.println("Error reading DOCX: " + f.getName());
        }
    }
    private void appendOdt(File f, FileWriter writer) {
        try {
            TextDocument odt = TextDocument.loadDocument(f);
            Iterator<org.odftoolkit.simple.text.Paragraph> it = odt.getParagraphIterator();
            while (it.hasNext()) {
                writer.write(it.next().getTextContent() + System.lineSeparator());
            }
            odt.close();
        } catch (Exception e) {
            IO.println("Error reading ODT: " + f.getName());
        }
    }
}
