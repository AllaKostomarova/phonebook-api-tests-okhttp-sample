package api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ApiDebugUtil {
    public static String createUniqueFileName (String fileName){
        return fileName + "_"+ System.currentTimeMillis() + ".txt";
    }

    public static String saveToFile (String fileName, String bodyText){
        String uniqName = createUniqueFileName(fileName);
        File file = new File("build/api-dumps/", uniqName);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write((bodyText == null) ? "Body is null" : bodyText);
            return file.getPath();
        } catch (IOException e){
            throw new UncheckedIOException("Failed to write file: " + file.getAbsolutePath(), e);
        }
    }


}
