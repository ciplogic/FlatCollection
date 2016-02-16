package PrimitiveBuilders;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 2/14/2016.
 */
public class IntArrayBuilder {

    int[] innerArray;
    int _size;

    public IntArrayBuilder(int capacity) {
        innerArray = new int[capacity];
    }

    public void add(int it) {
        if (innerArray.length == _size) {
            doubleCapacity();
        }
        innerArray[_size] = it;
        _size++;
    }

    private void doubleCapacity() {
        innerArray = Arrays.copyOf(innerArray, innerArray.length * 2);
    }

    public void addRange(int[] src, int start, int end) {
        while (_size+(end-start+1)>= innerArray.length){
            doubleCapacity();
        }
        System.arraycopy(src, start, innerArray, _size, end-start);
        _size += end-start;

    }

    void clear() {
        _size = 0;
    }

    public int size() {
        return _size;
    }

    int[] Data() {
        int[] arr = new int[_size];
        IntStream.range(0, _size)
                .forEach(i -> arr[i] = innerArray[i]);
        return arr;
    }
}
