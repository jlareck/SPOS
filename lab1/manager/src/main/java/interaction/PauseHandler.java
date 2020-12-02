package interaction;

import controller.Server;

import java.util.Scanner;

public class PauseHandler {
    private static final int WAIT_TIME = 15000;

    public static void stop() {

        Server.getProcessF().destroy();
        Server.getProcessG().destroy();
        Server.cancel = false;
        reasonOfCancelation();//System.exit(0);
    }
    private static void reasonOfCancelation() {


        if (Server.fDone && !Server.gDone) {
            System.out.println("STATUS: CANCELLED");
            System.out.println("The process is stopped because function G hangs");
        }
        else if (Server.gDone && !Server.fDone) {
            System.out.println("STATUS: CANCELLED");
            System.out.println("The process is stopped because function F hangs");
        }
        else if(!Server.fDone && !Server.gDone) {
            System.out.println("STATUS: CANCELLED");
            System.out.println("Functions F and G are not calculated");
        }
        System.out.println("Result of calculation is undefined");
    }
    public static void startPrompt() {
      //  String userInput = scanner.nextLine();
        Thread thread = Thread.currentThread();
        long promptStartTime = System.currentTimeMillis();
        new Thread(() -> {
            Thread threadTime = new Thread(() -> {
                while(System.currentTimeMillis() - promptStartTime < WAIT_TIME){}
                if (!Thread.currentThread().isInterrupted()) {
                    stop();
                }
            });
            threadTime.start();
            boolean check = true;
            while (check) {
                System.out.println("Cancellation Prompt:");
                System.out.println("(1) stop");
                System.out.println("(2) continue");
                System.out.println("System will shut down automatically in 15 seconds");
                Scanner scanner = new Scanner(System.in);

                String userInput = scanner.nextLine();
                if (userInput.equals("1") || userInput.equals("stop")) {
                    check = false;
                    stop();
                    return;
                } else if (userInput.equals("2") || userInput.equals("continue")) {
                    threadTime.interrupt();
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }).start();

    }
}