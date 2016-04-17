
import java.awt.*;
import java.io.IOException;
import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * Created by Ciprian on 1/24/2016.
 */
class Point2i { //8bytes
    int x,y; //8 bytes
}
class Line { //8 bytes
    Point2i begin =new Point2i(); //16  bytes
    Point2i end=new Point2i(); // 16 bytes
}

public class RunBench {

    public static void main(String[] args) throws IOException {
        FileReaderArray fileReaderArray = new FileReaderArray(500);
        int[] rows = {0};
        fileReaderArray.readFromFile("GetTrades.csv", rowByte -> {
            rows[0]++;
        });
        System.out.println("Rows: "+rows[0]);
    }

}

