import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * Created by Ciprian on 2/14/2016.
 */
public class Program {

    private static long ITERATION_TIME = 14000;
    public static void main(String[] args) {

        TestBed testBed = new TestBed();
        testBed.scanDirectory("definitions");
        {
            long start = currentTimeMillis();
            FileReaderArray readerArray = new FileReaderArray(5000);
            List<ByteArrayInputStream> fileContents = testBed.fileContents;
            long end = 0;
            int count = 0;
            TagSplitter splitter = new TagSplitter();
            while (true) {
                for (int i = 0; i < fileContents.size(); i++) {
                    //out.println("Reading file: "+testBed.fileList.get(i));
                    ByteArrayInputStream item = fileContents.get(i);

                    try {
                        readerArray.readFromByteArray(item, rowByte -> {
                            //String s = new String(rowByte.ByteData(), "UTF-8");
                            //out.println("Row: " + s);
                            splitter.parse(rowByte);

                        });
                    } catch (Exception e) {
                        out.println("Error on file: " + testBed.fileList.get(i));
                        e.printStackTrace();
                    }
                }

                end = currentTimeMillis();
                count++;
                if (end - start > ITERATION_TIME)
                    break;
            }
            out.println("Iterations: " + (count * 1000 / (end - start)) + " iterations");
        }
        {
            long start = currentTimeMillis();
            FileReaderArray readerArray = new FileReaderArray(5000);
            List<ByteArrayInputStream> fileContents = testBed.fileContents;
            long end = 0;
            int count = 0;
            TagSplitterFlat splitter = new TagSplitterFlat();
            while (true) {
                for (int i = 0; i < fileContents.size(); i++) {
                    ByteArrayInputStream item = fileContents.get(i);

                    try {
                        readerArray.readFromByteArray(item, rowByte -> {
                            //String s = new String(rowByte.ByteData(), "UTF-8");
                            //out.println("Row: " + s);
                            splitter.parse(rowByte);

                        });
                    } catch (Exception e) {
                        out.println("Error on file: " + testBed.fileList.get(i));
                        e.printStackTrace();
                    }
                }

                end = currentTimeMillis();
                count++;
                if (end - start > ITERATION_TIME)
                    break;
            }
            out.println("Iterations flat collections: " + (count * 1000 / (end - start)) + " iterations");
        }
        /*

        try {
            readerArray.readFromFile("normal.def", rowByte -> {
                String s = new String(rowByte.ByteData(), "UTF-8");
                out.println("Row: "+s);
                TagSplitter splitter = new TagSplitter();
                splitter.parse(rowByte);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
