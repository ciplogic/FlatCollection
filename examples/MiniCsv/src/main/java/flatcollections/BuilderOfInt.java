package flatcollections;

import java.util.Arrays;

public class BuilderOfInt {
    int[] _data;
    int _size;
    int _capacity;

    public BuilderOfInt() {
        setCapacity(10);
    }

    public BuilderOfInt(int capacity) {
    setCapacity(capacity);
    }

    public void setCapacity(int newSize){
        int capacity = newSize;
        if(capacity <= 0) {
            capacity = 10;
        }
        if(_data == null){
            _data = new int[capacity];
        }else {
            _data = Arrays.copyOf(_data, capacity);
        }
        _capacity = newSize;
    }

    public void clear() {
        _size = 0;
    }

    public int size() {
        return _size;
    }


private void doubleCapacity() {
        int newCapacity = 2 * _capacity;
        setCapacity(newCapacity);
    }

    public void add(int value){
        _size ++;
        if(_capacity < _size){
            doubleCapacity();
        }
        set(_size-1, value);
    }

    public void set(int index, int value){
        _data[index]=value;
    }

    public int get(int index){
        return _data[index];
    }

    public int[] toArray(){
        return Arrays.copyOf(_data, _size);
    }

    public void forEach(BuilderOfIntIterator acceptor) {
        for(int index = 0; index < _size; index++) {
            acceptor.accept(index, _data[index]);
        }
    }

    public void addRange(int[] source, int start, int end) {
        while (_size+(end-start+1)>= _capacity){
            doubleCapacity();
        }
        System.arraycopy(source, start, _data, _size, end-start);
        _size += end-start;

    }
}
