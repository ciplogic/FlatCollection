
import java.io.IOException;


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

