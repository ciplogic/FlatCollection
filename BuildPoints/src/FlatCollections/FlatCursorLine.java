package FlatCollections;

public class FlatCursorLine {
    final ArrayListOfLine _list;
    int _size;
    int _offset;
    final static int ItemCount = 4;

    FlatCursorLine(ArrayListOfLine list, int size){
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

    public void setBeginX (int value) {
        _list.set(_offset+0,  value);
    }

    public int getBeginX () {
        return _list.get(_offset+0);
    }
    public void setBeginY (int value) {
        _list.set(_offset+1,  value);
    }

    public int getBeginY () {
        return _list.get(_offset+1);
    }
    public void setEndX (int value) {
        _list.set(_offset+2,  value);
    }

    public int getEndX () {
        return _list.get(_offset+2);
    }
    public void setEndY (int value) {
        _list.set(_offset+3,  value);
    }

    public int getEndY () {
        return _list.get(_offset+3);
    }

}