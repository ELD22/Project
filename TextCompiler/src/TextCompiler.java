import java.util.*;
import java.io.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.odftoolkit.simple.TextDocument;

public class TextCompiler {
    private final Scanner input = new Scanner(System.in);
    private final String path;
    private final String fileName;
    private final String fileType;
    private File fileDoc;

    public TextCompiler() {
        IO.println("Please provide the directory path for the files you want to append.");
        path = input.nextLine();
        IO.println("Please provide a name for your new file.");
        fileName = input.nextLine();
        IO.println("Select a file type for your new file. (.txt/.docx/.odt)");
        fileType = input.nextLine();
    }
    public static void main(String[] args) throws FileNotFoundException {
        TextCompiler append = new TextCompiler();
        append.makeNewFile();
        try {
            append.appendFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void makeNewFile() {
        fileDoc = new File(path + File.separator + fileName + fileType);
        try {
            if (fileDoc.createNewFile()) {
                IO.println("The file has been created.");
            } else {
                IO.println("The file already exists.");
            }
        } catch (IOException e) {
            IO.println("An error occurred while creating the file.");
        }
    }
    public void appendFile() throws IOException {
        File p = new File(path);
        if (!p.exists() || !p.isDirectory()) {
            IO.println("File Not Found");
            return;
        }
        File[] files = p.listFiles();
        if (files != null && p.canRead()) {
            for (File file : files) {
                if (file.isFile()) {
                    IO.println("File: " + file.getName());
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

                String getFileExtension = f.getName().toLowerCase();
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
        }
    }
    private void appendTxt(File f, FileWriter writer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line + System.lineSeparator());
        }
        reader.close();
    }
    private void appendDocx(File f, FileWriter writer) {
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
