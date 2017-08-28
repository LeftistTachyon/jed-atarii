package jedatarii;

import java.io.*;
import java.nio.file.*;
import java.util.Collection;

/**
 * This class is a one-stop shop for Java File I/O.
 * @author GuiGuy
 * @version 1.0.3
 */

public class WriterFile {
    @SuppressWarnings("CallToPrintStackTrace")
    /**
     * File location.
     */
    private String fileName;
    /**
     * Whether <code>Exception</code>s are printed using the <code>printStackTrace()</code> method or not.
     */
    private boolean suppressed;
    /**
     * The number of lines in the file being read.
     */
    private int lines;
    /**
     * The file's path.
     */
    Path path = null;
    /**
     * An constructor for the WriterFile class.
     * @param file The file's location
     */
    public WriterFile(String file)
    {
        path = Paths.get(file);
        fileName = file;
    }
    /**
     * Write the specified string to the file, first clearing the file.
     * @param toWrite The string to write
     * @throws java.io.IOException
     */
    @SuppressWarnings({"ConvertToTryWithResources", "null"})
    public void writeToFile(String toWrite) throws IOException
    {
        File f = new File(FileName());
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(toWrite);
            bw.newLine();
        } finally {
            bw.close();
            fos.close();
        }
    }
    /**
     * Writes all of the specified strings in the <code>Collection</code>, first clearing the file.
     * @param writes The collection of strings to write
     * @throws java.io.IOException
     */
    @SuppressWarnings("null")
    public void loopWriteToFile(Collection writes) throws IOException{
        File f = new File(FileName());
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(Object o:writes){
                if (o == null) continue;
                bw.write(o.toString());
                bw.newLine();
            }
        } finally {
            bw.close();
            fos.close();
        }
    }
    /**
     * Writes to the file the specified String.
     * @param toWrite the String to write
     * @deprecated Only writes the String and nothing else.
     */
    @Deprecated
    public void writeFile(String toWrite) throws IOException
    {
        int i = readLength();
        String s = readFile();
        for(int a = 0;a!=i;a++)
            s += "\n";
        s += toWrite;
        writeToFile(s);
    }
    /**
     * Reads the file and returns all of the content of the file.
     * @return All of the contents of the file.
     * @throws java.io.IOException
     */
    @SuppressWarnings("UnusedAssignment")
    public String readFile() throws IOException
    {
        FileReader reader = null;
        BufferedReader bReader = null;
        String allText = "";
        try {
            reader = new FileReader(FileName());
            bReader = new BufferedReader(reader);
            String line;
            while((line = bReader.readLine())!= null)
            {
                allText += line + "\n";
            }
            return allText;
        } finally {
            if(reader != null)
                reader.close();
            if(bReader != null)
                bReader.close();
        }
    }
    /**
     * Returns all of the contents of the file as an array.
     * @return All of the contents of the file as an array.
     * @throws java.io.IOException
     */
    public String[] readFileLines() throws IOException {
        return readFile().split("\n");
    }
    /**
     * Reads the file and returns how many lines there are.
     * @return the length of the file, in lines
     * @throws java.io.IOException
     */
    @SuppressWarnings("UnusedAssignment")
    public int readLength() throws IOException{
        FileReader reader = null;
        BufferedReader bReader = null;
        String allText = "";
        try {
            reader = new FileReader(FileName());
            bReader = new BufferedReader(reader);
            String line;
            while((line = bReader.readLine())!= null)
            {
                allText += line + "\n";
                lines++;
            }
            return lines;
        } finally{
            if(reader != null)
                reader.close();
            if(bReader != null)
                bReader.close();
        }
    }
    /**
     * Creates a file at the current path.
     * @throws java.io.IOException
     */
    public void createFile() throws IOException
    {
        Files.createFile(path);
    }
    /**
     * Creates a new file at the specified Path.
     * @param p the specified Path
     * @deprecated errors with the <code>!Files.exists(p)</code>
     */
    @Deprecated
    public void createFile(Path p)
    {
        if(!Files.exists(p))
        {
            try{
                Files.createFile(p);
            } catch (IOException e){
                if(!suppressed)
                    e.printStackTrace();
            }
        }
    }
    /**
     * Deletes the file at the specified path, if any.
     * @throws java.io.IOException
     */
    public void deleteFile() throws IOException
    {
        Files.deleteIfExists(path);
    }
    /**
     * Moves a file from one Path to the destination Path.
     * @param destination the destination
     * @throws java.io.IOException
     */
    public void moveFile(String destination) throws IOException
    {
        Files.move(path, Paths.get(destination));
    }
    /**
     * Returns the file's path.
     * @return the file's path
     */
    public String FileName()
    {
        return fileName;
    }
    /**
     * Sets the internal Path and String Path with the specified String.
     * @param s the new, specified Path
     */
    public void setFile(String s)
    {
        fileName = s;
        path = Paths.get(s);
    }
    /**
     * Returns if the file exists.
     * @return if the file exists
     * @deprecated replaced with <code>Files.exists(Path p)</code>
     */
    @Deprecated
    public boolean fileIsExists()
    {
        boolean b;
        try{
            createFile();
            b = false;
        }
        catch(Exception e){
            b = true;
        }
        finally{
            try {
                Files.deleteIfExists(path);
            } catch (IOException ex) {
                if(!suppressed){
                    System.out.println("?");
                    ex.printStackTrace();
                }
            }
        }
        return b;
    }
    /**
     * Returns if the file exists.
     * @return if the file exists
     */
    public boolean fileExists(){
        return Files.exists(path);
    }
    /**
     * Returns if a file at the specified path exists.
     * @param p the specified Path
     * @return if the file exists
     */
    public boolean fileExists(Path p){
        return Files.exists(p);
    }
    /**
     * Returns if a file at the specified String path exists.
     * @param s the specified Path
     * @return if the file exists
     */
    public boolean fileExists(String s){
        return Files.exists(Paths.get(s));
    }
    /**
     * Returns if a specified File exists.
     * @param f the specified File
     * @return if the File exists
     */
    public boolean fileExists(File f){
        return f.exists();
    }
    /**
     * Returns if the file is writable.
     * @return if the file is writable
     */
    public boolean isWritable(){
        return Files.isWritable(path);
    }
    /**
     * Suppresses the <code>Exeption</code>s thrown by the <code>printStackTrace()</code> method.
     */
    public void suppress(){
        suppressed = true;
    }
    /**
     * Stops suppressing <code>Exeption</code>s thrown by the <code>printStackTrace()</code> method.
     */
    public void depress(){
        suppressed = false;
    }
    
    public boolean isEmpty() throws IOException {
        return Files.size(path) == 0;
    }
    /**
     * Closes the object.
     */
    public void close() {
        path = null;
    }
}
//C:\\Users\\Yan Yan New\\Desktop\\DATA.txt