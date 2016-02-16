import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Ciprian on 2/15/2016.
 */
public class TestBed {
    public List<ByteArrayInputStream> fileContents = new ArrayList<>();
    public List<String> fileList = new ArrayList<>();

    static byte[] readFileContents(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    private static String[] GetStringsOfArray(List<String> resultList) {
        String[] result = new String[resultList.size()];

        for (int i = 0; i < resultList.size(); i++) {
            String item = resultList.get(i);
            result[i] = item;
        }
        return result;
    }

    private static String[] getDirectoryFiles(String path, boolean recursive, Predicate<File> isValidFile) {

        ArrayList<String> resultList = new ArrayList<>();

        if (!recursive) {
            File dir = new File(path);
            File[] contents = dir.listFiles();
            for (File itemFile : contents) {

                if (!isValidFile.test(itemFile)) {
                    continue;
                }
                resultList.add(itemFile.getPath());
            }
            String[] result = GetStringsOfArray(resultList);
            return result;
        }

        try {
            Files.walk(Paths.get(path)).forEach(filePath -> {

                if (Files.isRegularFile(filePath)) {
                    if (!isValidFile.test(filePath.toFile())) {
                        return;
                    }
                    String filePathName = filePath.toString();
                    resultList.add(filePathName);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GetStringsOfArray(resultList);
    }

    public void scanDirectory(String path) {
        fileContents.clear();
        String[] fileNames = getDirectoryFiles(path, true, (file -> file.getName().endsWith(".def")));
        Arrays.stream(fileNames).forEach(file -> {
            byte[] data = readFileContents(file);
            if (data != null)
            {
                ByteArrayInputStream stream = new ByteArrayInputStream(data);
                fileContents.add(stream);
                fileList.add(file);
            }
        });

    }
}

