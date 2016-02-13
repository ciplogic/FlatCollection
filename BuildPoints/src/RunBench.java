import FlatCollections.ArrayListOfLine;
import FlatCollections.ArrayListOfPoint;
import FlatCollections.FlatCursorLine;
import FlatCollections.FlatCursorPoint;

import java.awt.*;
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

    public static void main(String[] args) {
        //testPoints();
        testLines();
    }

    private static void testLines() {
        int count = 350000;
        ArrayListOfLine millionPointsArray = new ArrayListOfLine();
        IntStream.range(0, 100).forEach(i -> {
                    setupValuesForLines(count, millionPointsArray);
                }
        );
        final Line[] points = new Line[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Line();
        }
        IntStream.range(0, 100).forEach(i -> {
                    setupPointsForLines(count, points);
                }
        );
        {
            long t1 = System.currentTimeMillis();

            IntStream.range(0, 10000).forEach(i -> {
                        setupValuesForLines(count, millionPointsArray);
                    }
            );

            long t2 = System.currentTimeMillis();
            out.println("Setup values 1:" + (t2 - t1) + " ms.");
            int[] sumVal = {0};
            IntStream.range(0, 10000).forEach(i -> {
                        sumVal[0] += readPointsForLines(millionPointsArray);
                    }
            );
            long t3 = System.currentTimeMillis();
            out.println("Read time 1:" + (t3 - t2) + " ms. sum: " + sumVal[0]);
        }
        {
            long t1 = System.currentTimeMillis();

            IntStream.range(0, 10000).forEach(i -> {
                        setupPointsForLines(count, points);
                    }
            );
            long t2 = System.currentTimeMillis();
            out.println("Setup values 2:" + (t2 - t1) + " ms.  ");

            int[] sumVal = {0};
            IntStream.range(0, 10000).forEach(i -> {

                        sumVal[0] += readPointsForLines(points);
                    }
            );
            long t3 = System.currentTimeMillis();
            out.println("Read time 2:" + (t3 - t2) + " ms. sum: " + sumVal[0]);
        }
    }

    private static int readPointsForLines(Line[] points) {
        int sum = 0;
        for(Line line:points){
            sum += line.begin.x;
            sum += line.begin.y;
            sum += line.end.x;
            sum += line.end.y;
        }
        return sum;
    }

    private static int readPointsForLines(ArrayListOfLine millionPointsArray) {
        FlatCursorLine startIterator = millionPointsArray.getCursor();
        int sum = 0;
        while (startIterator.move()) {
            sum += startIterator.getbeginx();
            sum += startIterator.getbeginy();
            sum += startIterator.getendx();
            sum += startIterator.getendy();
        }
        return sum;
    }

    private static void setupPointsForLines(int count, Line[] points) {
        for (int i = 0; i < count; i++) {
            points[i] = new Line();
            points[i].begin.x = i;
            points[i].begin.y = i + 2;
            points[i].end.x = i + 6;
            points[i].end.y = i + 8;
        }
    }

    private static void setupValuesForLines(int count, ArrayListOfLine millionPointsArray) {
        millionPointsArray.clear();
        FlatCursorLine startIterator = millionPointsArray.getCursor();
        for (int i = 0; i < count; i++) {
            startIterator.add();
            startIterator.setbeginx(i);
            startIterator.setbeginy(i + 2);
            startIterator.setendx(i+6);
            startIterator.setendy(i + 8);

        }
    }

    private static void testPoints() {
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