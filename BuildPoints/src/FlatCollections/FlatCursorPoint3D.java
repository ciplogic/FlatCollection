package FlatCollections;

public class FlatCursorPoint3D {
    final ArrayListOfPoint3D _list;
    int _size;
    int _offset;
    final static int ItemCount = 3;

    FlatCursorPoint3D(ArrayListOfPoint3D list, int size){
        _list = list;
        _offset = -ItemCount;
        _size = size*ItemCount;
    }

    public void add(){
        _list.add();
        move();
    }

    public void indexSet(int pos){
        _offset = ItemCount*pos;
    }

    public boolean move(){
        _offset = _offset + ItemCount;
        return _offset < _size;
    }

    public void setX (float value) {
        _list.set(_offset+0,  value);
    }

    public float getX () {
        return _list.get(_offset+0);
    }
    public void setY (float value) {
        _list.set(_offset+1,  value);
    }

    public float getY () {
        return _list.get(_offset+1);
    }
    public void setZ (float value) {
        _list.set(_offset+2,  value);
    }

    public float getZ () {
        return _list.get(_offset+2);
    }

}
