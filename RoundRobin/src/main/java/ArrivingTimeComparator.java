import java.util.Comparator;

public class ArrivingTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        if (o1.arrivingTime == o2.arrivingTime) {
            return 0;
        } else if (o1.arrivingTime > o2.arrivingTime) {
            return 1;
        } else {
            return -1;
        }
    }
}
