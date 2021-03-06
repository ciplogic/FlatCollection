import PrimitiveBuilders.ByteArrayBuider;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Ciprian on 2/14/2016.
 */


class FileReaderArray {
    byte[] bytes;
    // Create the byte array to hold the data
    ByteArrayBuider rowBytes = new ByteArrayBuider(120);

    interface IOnRow {
        void consume(ByteArrayBuider rowByte) throws UnsupportedEncodingException;
    }
    FileReaderArray (int _bufferLength) {
        bytes = new  byte[_bufferLength];
    }


    void readFromFile(String fileName, IOnRow onRow) throws IOException {
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);

        getBytesFromFile(fileInputStream,onRow);
    }

    void readFromByteArray(ByteArrayInputStream fileInputStream, IOnRow onRow) throws IOException {
       
        fileInputStream.reset();

        getBytesFromFile(fileInputStream,onRow);
    }

    static int indexInArray(byte[] data, int pos, byte toSearch){
        return indexInArray(data, pos, data.length, toSearch);
    }
    static int parseInt(byte[]data, int pos, int maxLen){
        if(data[pos] != '-')
            return sum(data, pos, maxLen);

        return -sum(data, pos, maxLen);
    }
    @org.jetbrains.annotations.Contract(pure = true)
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
    static String rangeBytesToString(byte[] data, int pos, int len){
        byte[] copy = new byte[len];
        IntStream.range(0, len).forEach(i->copy[i] = data[i+pos]);
        try {
            return new String(copy, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    static int indexInArray(byte[] data, int pos, int length, byte toSearch){
        for (int i = pos; i < length; i++) {
            byte it = data[i];
            if(it==toSearch)
                return i;
        }
        return -1;
    }

    void getBytesFromFile(InputStream fileInputStream, IOnRow onRow) throws IOException {
        // Read in the bytes
        rowBytes.clear();

        try {
            while (true) {
                int bytesRead = fileInputStream.read(bytes, 0, bytes.length);
                if (bytesRead < 0) {
                    break;
                }

                int pos = 0;
                while (pos < bytesRead) {
                    byte eoln  = 10;
                    int nextPos = indexInArray(bytes, pos, bytesRead, eoln);
                    if (nextPos!= -1) {
                        rowBytes.addRange(bytes, pos, nextPos);
                        onRow.consume(rowBytes);
                        rowBytes.clear();
                        pos  = nextPos+1;
                    }else {
                        rowBytes.addRange(bytes, pos, bytesRead);
                        break;
                    }
                }
            }
        } finally {
            fileInputStream.close();
        }

    }

}
