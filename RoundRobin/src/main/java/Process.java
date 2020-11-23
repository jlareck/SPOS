public class Process {
  public int id;
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;
  public int arrivingTime;

  public Process(int id, int cputime, int ioblocking, int cpudone, int ionext, int numblocked, int arrivingTime) {
    this.id = id;
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.arrivingTime = arrivingTime;
  }
}