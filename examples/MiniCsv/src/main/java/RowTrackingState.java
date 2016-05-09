import flatcollections.BuilderOfInt;

public class RowTrackingState {

    public int startPos;
    public BuilderOfInt tokenList = new BuilderOfInt();
    public byte bSeparator;

    public void reset() {
        tokenList.clear();
        startPos = 0;
    }
}
