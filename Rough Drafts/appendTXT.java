import java.io.*;

public class appendTXT {
    public static void main(String args[]) throws IOException {
        FileWriter writer = new FileWriter(fileDoc, true);
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
                IO.println("Appending: " + f.getName());
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + System.lineSeparator());
                }
                reader.close();
            }
        }
        writer.close();
        IO.println("Completed appending files into: " + fileDoc.getName());
    }
}
