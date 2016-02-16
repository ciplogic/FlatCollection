package FlatCollections;

import java.util.Arrays;

public class ArrayListOfPositionsForToken {
    int[] _data;
    int _size;
    int _capacity;

    final static int ItemCount = 5;

    public ArrayListOfPositionsForToken() {
        setCapacity(10);
    }
    public ArrayListOfPositionsForToken(int capacity) {
        setCapacity(capacity);
    }

    public void setCapacity(int newSize){
        int capacity = newSize*ItemCount;
        if(capacity <= 0) {
            capacity = 10*ItemCount;
        }
        if(_data == null){
            _data = new int[capacity];
        }else {
            _data = Arrays.copyOf(_data, capacity);
        }
        _capacity = newSize;
    }

    public FlatCursorPositionsForToken getCursor(){
        return new FlatCursorPositionsForToken(this, _size);
    }

    public void clear() {
        _size = 0;
    }

    public void add(){
        _size ++;
        if(_capacity < _size){
            setCapacity(2*_capacity);
        }
    }
    public void set(int index, int value){
        _data[index]=value;
    }

    public int get(int index){
        return _data[index];
    }
}
