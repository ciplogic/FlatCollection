
import java.io.IOException;
import java.util.stream.IntStream;

public class RunBench {

    public static void main(String[] args) throws IOException {
        int[] rows = {0};
        try {
            CsvScanner csvScanner = new CsvScanner();

            csvScanner.scanFile("worldcitiespop.txt", (char) 10, (state, rowBytes) -> {
                int regionInt = state.getInt(3, rowBytes);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        int[] sum = new int[1];
        IntStream.range(0, 10).forEach(i -> {
            CsvScanner csvScanner = new CsvScanner();
            try {

                csvScanner.scanFile("worldcitiespop.txt", (char) 10, (state, rowBytes) -> {
                    int regionInt = state.getInt(3, rowBytes);
                    sum[0] += regionInt;
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        long ms = (end - start);
        System.out.println("Sum: " + sum[0]);
        System.out.println("Time: " + ms + " ms");
    }

}
