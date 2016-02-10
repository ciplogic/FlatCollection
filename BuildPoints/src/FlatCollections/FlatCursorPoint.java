package FlatCollections;

public class FlatCursorPoint {
final ArrayListOfPoint _list;
int _size;
int _offset;
final static int ItemCount = 2;

FlatCursorPoint(ArrayListOfPoint list, int size){
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

public void setx (int value) {
_list.set(_offset+0,  value);
}

public int getx () {
return _list.get(_offset+0);
}
public void sety (int value) {
_list.set(_offset+1,  value);
}

public int gety () {
return _list.get(_offset+1);
}

}
