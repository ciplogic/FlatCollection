import flatcollections.BuilderOfByte;
import flatcollections.BuilderOfInt;

import java.io.IOException;

/**
 * Created by Ciprian on 5/9/2016.
 */
public class CsvScanner {

    private final byte _bSeparator;
    private int bufferLength;

    public CsvScanner(char separator){
        _bSeparator = (byte) separator;
        bufferLength = 500;
    }

    public CsvScanner(){
        _bSeparator = ',';
        bufferLength = 500;

    }


    public interface OnRowScanned {
        void trackRow(RowTrackingState rowState, BuilderOfByte rowBytes);
    }
    

    public void scanFile(String fileName, char eolnSeparator, OnRowScanned rowScanned) throws IOException {
        
        FileReaderArray fileReaderArray = new FileReaderArray();
        fileReaderArray.setEoln((byte) eolnSeparator);
        final RowTrackingState trackingState = new RowTrackingState();
        
        fileReaderArray.readFromFile(fileName, rowByte -> {
            handleRow(rowByte, trackingState);
            if(trackingState.rows>1)
            {
                rowScanned.trackRow(trackingState, rowByte);
            }
        });
    }


    public void scanFile(String fileName, OnRowScanned rowScanned) throws IOException {

        FileReaderArray fileReaderArray = new FileReaderArray();
        final RowTrackingState trackingState = new RowTrackingState();

        fileReaderArray.readFromFile(fileName, rowByte -> {
            handleRow(rowByte, trackingState);
            if(trackingState.rows>1)
            {
                rowScanned.trackRow(trackingState, rowByte);
            }
        });
    }
    private void handleRow(BuilderOfByte rowByte, RowTrackingState trackingState) {
        trackingState.rows ++;
        trackingState.reset();
        rowByte.forEach((index, ch)-> updateTokens(trackingState, index, ch));
        trackingState.tokenList.add(rowByte.size());
    }


    private void updateTokens(RowTrackingState trackingState, int index, byte ch) {
        if(ch==_bSeparator)
            trackingState.tokenList.add(index);
    }

    private static void addToken(RowTrackingState trackingState, int index) {
        trackingState.tokenList.add(index);
    }
    static int parseIntFromArray(byte[]data, int pos, int maxLen){
        if(data[pos] != '-')
            return sum(data, pos, maxLen);

        return -sum(data, pos, maxLen);
    }


    static int sum(byte[]data, int pos, int endRange){
        int result = 0;
        for(int i=0;i<endRange;i++){
            int offset = data[pos+i] - '0';
            if(offset<0)
                return result;
            if(offset>=10)
                return result;
            result = result*10+offset;

        }
        return result;
    }

     class RowTrackingState {

         public int rows;
        public int startPos;
        public BuilderOfInt tokenList = new BuilderOfInt();

         public RowTrackingState() {

         }

         public void reset() {
            tokenList.clear();
            startPos = 0;
        }

         public int getInt(int column, BuilderOfByte rowData) {
             int start = 0;
             int length = tokenList.get(column);
             if(column>0) {
                 start = tokenList.get(column-1)+1;
                 length -= tokenList.get(column-1);
                 length--;
             }
             int result = parseIntFromArray(rowData.getData(), start, length);
             return result;
         }
     }

}
