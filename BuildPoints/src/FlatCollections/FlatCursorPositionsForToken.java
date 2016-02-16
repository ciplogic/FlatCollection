package FlatCollections;

public class FlatCursorPositionsForToken {
    final ArrayListOfPositionsForToken _list;
    int _size;
    int _offset;
    final static int ItemCount = 5;

    FlatCursorPositionsForToken(ArrayListOfPositionsForToken list, int size){
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

    public void settag (int value) {
        _list.set(_offset+0,  value);
    }

    public int gettag () {
        return _list.get(_offset+0);
    }
    public void setstart (int value) {
        _list.set(_offset+1,  value);
    }

    public int getstart () {
        return _list.get(_offset+1);
    }
    public void setlength (int value) {
        _list.set(_offset+2,  value);
    }

    public int getlength () {
        return _list.get(_offset+2);
    }
    public void setstartValue (int value) {
        _list.set(_offset+3,  value);
    }

    public int getstartValue () {
        return _list.get(_offset+3);
    }
    public void setlengthValue (int value) {
        _list.set(_offset+4,  value);
    }

    public int getlengthValue () {
        return _list.get(_offset+4);
    }

}
