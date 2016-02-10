import FlatCollections.ArrayListOfPoint;
import FlatCollections.FlatCursorPoint;

import java.awt.*;
import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * Created by Ciprian on 1/24/2016.
 */
public class RunBench {
    public static void main(String[] args) {
        int count = 500000;
        ArrayListOfPoint millionPointsArray = new ArrayListOfPoint();
        IntStream.range(0, 100).forEach(i -> {
                    setupValues(count, millionPointsArray);
                }
        );
        final Point[] points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point();
        }
        IntStream.range(0, 100).forEach(i -> {
                    setupPoints(count, points);
                }
        );
        {
            long t1 = System.currentTimeMillis();

            IntStream.range(0, 10000).forEach(i -> {
                        setupValues(count, millionPointsArray);
                    }
            );

            long t2 = System.currentTimeMillis();
            out.println("Time 1:" + (t2 - t1) + " ms.");
            int[] sumVal = {0};
            IntStream.range(0, 10000).forEach(i -> {
                        sumVal[0] += readPoints(millionPointsArray);
                    }
            );
            long t3 = System.currentTimeMillis();
            out.println("Write time 1:" + (t3 - t2) + " ms. sum: " + sumVal[0]);
        }
        {
            long t1 = System.currentTimeMillis();

            IntStream.range(0, 10000).forEach(i -> {
                        setupPoints(count, points);
                    }
            );
            long t2 = System.currentTimeMillis();
            out.println("Read time 2:" + (t2 - t1) + " ms.  ");

            int[] sumVal = {0};
            IntStream.range(0, 10000).forEach(i -> {

                        sumVal[0] += readPoints(count, points);
                    }
            );
            long t3 = System.currentTimeMillis();
            out.println("Write time 2:" + (t3 - t2) + " ms. sum: " + sumVal[0]);
        }
    }

    private static void setupPoints(int count, Point[] points) {
        for (int i = 0; i < count; i++) {
            points[i] = new Point();
            points[i].x = i;
            points[i].y = i + 2;
        }
    }

    private static void setupValues(int count, ArrayListOfPoint millionPointsArray) {
        millionPointsArray.clear();
        FlatCursorPoint startIterator = millionPointsArray.getCursor();
        for (int i = 0; i < count; i++) {
            startIterator.add();
            startIterator.setx(i);
            startIterator.sety(i + 2);

        }
    }

    private static int readPoints(int count, Point[] points) {
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += points[i].x;
            sum += points[i].y;
        }
        return sum;
    }

    private static int readPoints(ArrayListOfPoint millionPointsArray) {
        FlatCursorPoint startIterator = millionPointsArray.getCursor();
        int sum = 0;
        while (startIterator.move()) {
            sum += startIterator.getx();
            sum += startIterator.gety();
        }
        return sum;
    }
}