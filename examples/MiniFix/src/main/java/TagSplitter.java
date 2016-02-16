import PrimitiveBuilders.ByteArrayBuider;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by Ciprian on 2/15/2016.
 */
public class TagSplitter {
    List<PositionsForToken> _tokens = new ArrayList<>();
    public void parse(ByteArrayBuider rowBytes){
        _tokens.clear();
        byte[] innerArray = rowBytes.getInnerArray();
        int length = rowBytes.size();
        int pos = 1;
        while (true)
        {
            int newPos = FileReaderArray.indexInArray(innerArray,pos, length, (byte) '=');
            if(newPos == -1)
                return;
            
            int endTag = FileReaderArray.indexInArray(innerArray,newPos, length, (byte) 1);
            if(endTag == -1)
                return;
            addTag(pos, endTag, innerArray, length, newPos);
            pos = endTag+1;
        }
    }

    public void addTag(int pos, int endTag, byte[] innerArray, int length, int newPos) {
        PositionsForToken token = new PositionsForToken();
        token.start = pos;
        token.length = endTag-pos+1;
        token.tag = FileReaderArray.parseInt(innerArray, pos, pos+length);
        token.startValue = newPos+1;
        token.lengthValue = endTag-newPos-1;
        /*out.println("Tag: "+FileReaderArray.rangeBytesToString(innerArray, token.start, token.length));
        out.println("Value: '"+FileReaderArray.rangeBytesToString(innerArray, token.startValue, token.lengthValue)+"'");
        out.println("Decoded: "+token);*/
        _tokens.add(token);
    }
}
