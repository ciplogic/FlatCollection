package PrimitiveBuilders;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 2/14/2016.
 */
public class ByteArrayBuider {

    private byte[] innerArray;
    private int _size;
    private int _length;

    public ByteArrayBuider(int capacity) {
        innerArray = new byte[capacity];
        _length = innerArray.length;
    }

    public void add(byte it) {
        if (_length == _size) {
            doubleCapacity();
        }
        innerArray[_size] = it;
        _size++;
    }

    private void doubleCapacity() {
        innerArray = Arrays.copyOf(innerArray, innerArray.length * 2);
        _length = innerArray.length;
    }

    public void addRange(byte[] src, int start, int end) {
        while (_size+(end-start+1)>= _length){
            doubleCapacity();
        }
        System.arraycopy(src, start, innerArray, _size, end-start);
        _size += end-start;

    }

    public void clear() {
        _size = 0;
    }

    public int size() {
        return _size;
    }

    public byte[] ByteData() {
        byte[] arr = new byte[_size];
        IntStream.range(0, _size)
                .forEach(i -> arr[i] = innerArray[i]);
        return arr;
    }

    public byte[] getInnerArray() {
        return innerArray;
    }

}
