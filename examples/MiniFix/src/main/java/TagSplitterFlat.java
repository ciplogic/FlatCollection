import PrimitiveBuilders.ByteArrayBuider;
import FlatCollections.ArrayListOfPositionsForToken;
import FlatCollections.FlatCursorPositionsForToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciprian on 2/16/2016.
 */
public class TagSplitterFlat {
    private final ArrayListOfPositionsForToken _tokens = new ArrayListOfPositionsForToken();
    public void parse(ByteArrayBuider rowBytes){
        _tokens.clear();
        final int length = rowBytes.size();
        if(length <=3) {
            return;
        }
        byte[] innerArray = rowBytes.getInnerArray();

        if(innerArray[0] == (byte)'#') {
            return;
        }
        int pos = 1;
        while (true)
        {
            int newPos = FileReaderArray.indexInArray(innerArray,pos, length, (byte) '=');
            if(newPos == -1) {
                break;
            }
            
            int endTag = FileReaderArray.indexInArray(innerArray,newPos, length, (byte) 1);

            addTag(pos, endTag, innerArray, length, newPos);
            pos = endTag+1;
        }
    }

    private void addTag(int pos, int endTag, byte[] innerArray, int length, int newPos) {
        FlatCursorPositionsForToken token = _tokens.getCursor();
        token.add();
        token.setstart(pos);
        token.settag(FileReaderArray.parseInt(innerArray, pos, pos+length));
        token.setstartValue(newPos+1);
        token.setlengthValue(endTag-newPos-1);
    }
}
