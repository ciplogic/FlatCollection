package FlatCollections;

import java.util.Arrays;

public class ArrayListOfPoint3D {
    float[] _data;
    int _size;
    int _capacity;

    final static int ItemCount = 3;

    public ArrayListOfPoint3D() {
        setCapacity(10);
    }
    public ArrayListOfPoint3D(int capacity) {
        setCapacity(capacity);
    }

    public void setCapacity(int newSize){
        int capacity = newSize*ItemCount;
        if(capacity <= 0) {
            capacity = 10*ItemCount;
        }
        if(_data == null){
            _data = new float[capacity];
        }else {
            _data = Arrays.copyOf(_data, capacity);
        }
        _capacity = newSize;
    }

    public FlatCursorPoint3D getCursor(){
        return new FlatCursorPoint3D(this, _size);
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
    public void set(int index, float value){
        _data[index]=value;
    }

    public float get(int index){
        return _data[index];
    }
}
