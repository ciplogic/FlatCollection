package FlatCollections;

public class FlatCursorPoint3D{ 
ListOfPoint3D _list;
int _size;
int _offset;
static final int ItemCount=3;
 public  FlatCursorPoint3D(ListOfPoint3D list, int size) {
            _list = list;
            _offset = -ItemCount;
            _size = size*ItemCount;
        }
 public void add() {
            _list.add();
            move();
        }
 public void indexSet(int pos) {
            _offset = ItemCount*pos;
        }
 public boolean move() {
            _offset = _offset + ItemCount;
            return _offset < _size;
        }
}
