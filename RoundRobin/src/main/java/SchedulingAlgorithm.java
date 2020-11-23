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
  static int currentCompletedNumberOfProcesses = 0;
  static int quantum = Scheduling.quantum;
  static String resultsFile = "Summary-Processes";
  static Process process;
  static Vector processVector;
  static PrintStream out;

  public static Results run(int runtime, Vector _processVector, Results result) {
    processVector = _processVector;
    totalNumberOfProcesses = processVector.size();
    Queue<Process> queue = new LinkedList<Process>(processVector);
    result.schedulingType = "Batch (Preemptive)";
    result.schedulingName = "Round Robin Scheduling algorithm";
    try {
      out = new PrintStream(new FileOutputStream(resultsFile));
      process = queue.peek();
      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")" + " Quantum " +quantum);

      while (comptime < runtime) {
        if (process.cpudone == process.cputime) {

          currentCompletedNumberOfProcesses++;
          out.println("Process: " + processVector.indexOf(process) + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);
          if (currentCompletedNumberOfProcesses == totalNumberOfProcesses) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          queue.poll();
          process = queue.peek();
          quantum = Scheduling.quantum;
          out.println("\nProcess: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

        }
        if (quantum > 0) {
          if (process.ioblocking == process.ionext) {
            process.numblocked++;
            out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);
            process.ionext = 0;
            queue.add(queue.poll());
            process = queue.peek();
            quantum = Scheduling.quantum;
            out.println("\nProcess: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

          }
          if (process.ioblocking > 0) {
            process.ionext++;
          }
          process.cpudone++;
          quantum--;
          comptime++;
        } else {
          out.println("Process: " + currentProcess + " stopped because of quantum... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " + quantum);
          queue.add(queue.poll());
          process = queue.peek();
          quantum = Scheduling.quantum;
          out.println("\nProcess: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")"  + " Quantum " +quantum);

        }
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }
}
