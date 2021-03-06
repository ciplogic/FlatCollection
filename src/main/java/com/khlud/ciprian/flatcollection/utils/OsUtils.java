package com.khlud.ciprian.flatcollection.utils;

import com.google.common.base.Joiner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OsUtils {

    public static String[] getDirectoryFiles(String path, boolean recursive, Predicate<File> isValidFile) {

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

    public static String pathCombine(String... args) {
        return Joiner.on("/").join(args);
    }

    private static String[] GetStringsOfArray(List<String> resultList) {
        String[] result = new String[resultList.size()];

        for (int i = 0; i < resultList.size(); i++) {
            String item = resultList.get(i);
            result[i] = item;
        }
        return result;
    }

    public static String[] getFileLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(fileName), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GetStringsOfArray(lines);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static Document readXmlPlain(String fileName) {
        Document dom = null;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            File file = new File(fileName);
            dom = db.parse(file);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dom;
    }

    public static void writeAllText(String pathName, String generateCode) {
        try (PrintWriter out = new PrintWriter(pathName)) {
            out.print(generateCode);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static List<String> readAllLines(String file) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            return lines;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static String readAllText(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine = br.readLine();
            if (strLine != null) {
                sb.append(strLine);
            }

//Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                sb.append("\n");
                sb.append(strLine);
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static void createPath(String path) {
        new File(path).mkdirs();
    }
}
