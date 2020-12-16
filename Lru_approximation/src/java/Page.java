import java.util.Vector;

public class Page
{
  public int id;
  public int physical;
  public byte R;
  public byte M;
  public int inMemTime;
  public int lastTouchTime;
  public long high;
  public long low;
  public int size;
  public Vector<Integer> bitsRVector;

  public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low ) 
  {
    this.id = id;
    this.physical = physical;
    this.R = R;
    this.M = M;
    this.inMemTime = inMemTime;
    this.lastTouchTime = lastTouchTime;
    this.high = high;
    this.low = low;
    this.bitsRVector = new Vector<Integer>();
  }
  public void changeRbits(int bitValue){
    bitsRVector.add(0, bitValue);
    bitsRVector.remove(size);

  }

  public int valueRbits(){
    StringBuilder bits = new StringBuilder();
    for (int i = 0; i < this.bitsRVector.size(); i++){
      bits.append(String.valueOf(this.bitsRVector.get(i)));
    }
    return Integer.parseInt(bits.toString(), 2);
  }

}
