# Essay Combiner Project
A Java program that can append all Docx, Odt, and Txt files into a new one.

The .java file is located in the src folder of TextCompiler.
For this program to work, you will need to answer 3 prompts and mispells may cause errors..

        IO.println("Please provide the directory path for the files you want to append.");
        IO.println("Please provide a name for your new file.");
        IO.println("Select a file type for your new file. (.txt/.docx/.odt)");

The first prompt needs to include the complete directory path and not a shorten version like \Documents.
Additionally, I did not check if the directory path works for Linux or Mac. I don't think it should be an issue, but for Windows it should include C:\
The second prompt can be spelled in any way.
The third prompt needs to include the period and is not cap-sensitive.

As long as that is done correctly, the program will append any .txt, .odt, and .docx files in a folder into your newly created one.
