import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by Ciprian on 2/20/2016.
 */
public class MutableString {

    private final byte[] _data;
    private final Charset _encoding;
    private int _start, _length;

    public MutableString(byte[] data, Charset encoding) {
        _data = data;
        _encoding = encoding;
    }

    @NotNull
    public static MutableString create(String value) throws UnsupportedEncodingException {
        Charset charSet = Charset.forName("UTF-8");
        return create(value, charSet);
    }

    @NotNull
    private static MutableString create(String value, Charset charSet) {
        byte[] data= value.getBytes(charSet);
        MutableString result = new MutableString(data, charSet);
        result._start = 0;
        result._length = data.length;
        return result;
    }
}
