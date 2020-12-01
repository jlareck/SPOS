// run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {
  static int comptime = 0;
  static int currentProcess = 0;
  static int totalNumberOfProcesses;
  static int currentNumberOfCompletedProcesses = 0;
  static int quantum = Scheduling.quantum;
  static String resultsFile = "Summary-Processes";
  static Process process;
  static Vector<Process> processVector;
  static PrintStream out;
  static int lastProcess = 0;
  static Queue<Process> queue;
  public static Results run(int runtime, Vector _processVector, Results result) {
    processVector = _processVector;
    processVector.sort(new ArrivingTimeComparator());

    totalNumberOfProcesses = processVector.size();

    queue = new LinkedList<Process>();
    result.schedulingType = "Batch (Preemptive)";
    result.schedulingName = "Round Robin Scheduling algorithm";
    try {
      out = new PrintStream(new FileOutputStream(resultsFile));
//      process = queue.peek();
//      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")" + " Quantum " +quantum);

      while (comptime < runtime) {
        if (lastProcess < processVector.size()) {
          while (comptime == processVector.get(lastProcess).arrivingTime) {
            Process processCopy = processVector.get(lastProcess);
            queue.add(processCopy);
            process = processCopy;
            out.println("Process: " + process.id + " joined... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")" );

            lastProcess++;
            if (lastProcess >= processVector.size()) {
                break;
            }
          }
        }
        if (process == null && !queue.isEmpty()) {
          process = queue.peek();
          out.println("Process: " + process.id + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")" + " Quantum " +quantum);
        }
        if (process !=null && process.cpudone == process.cputime) {

          currentNumberOfCompletedProcesses++;
          out.println("Process: " + processVector.indexOf(process) + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);
          if (currentNumberOfCompletedProcesses == totalNumberOfProcesses) {
            result.compuTime = comptime;
            out.close();
            return result;
          }

          queue.poll();
          if (!queue.isEmpty()) {
            process = queue.peek();
            quantum = Scheduling.quantum;
            out.println("\nProcess: " + process.id + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

          }
          else {
            process = null;
            quantum = Scheduling.quantum;
          }

        }
        if (process != null && quantum > 0) {
          if (process.ioblocking == process.ionext) {
            process.numblocked++;
            out.println("Process: " + process.id + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);
            process.ionext = 0;
            getNextProcess();
            out.println("\nProcess: " + process.id + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

          }
          if (process.ioblocking > 0) {
            process.ionext++;
          }
          process.cpudone++;
          quantum--;
          comptime++;
        } else if (process != null) {
          out.println("Process: " + process.id + " stopped because quantum is zero... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " + quantum);
          getNextProcess();
          out.println("\nProcess: " + process.id + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

        } else {
          comptime++;
        }
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }
  public static void getNextProcess() {
    queue.add(queue.poll());
    process = queue.peek();
    quantum = Scheduling.quantum;
  }
}
