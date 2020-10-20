package interaction;

import controller.Server;

import java.util.Scanner;

public class PauseHandler {
    private static final int WAIT_TIME = 15000;

    public static void stop() {

        Server.getProcessF().destroy();
        Server.getProcessG().destroy();
        Server.cancel = false;
        System.exit(0);
    }

    public static void startPrompt() {

        System.out.println("Cancellation Prompt:");
        System.out.println("(1) stop");
        System.out.println("(2) continue");
        System.out.println("System will shut down automatically in 15 seconds");
        Scanner scanner = new Scanner(System.in);

        long promptStartTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - promptStartTime < WAIT_TIME) {
            String userInput = scanner.nextLine();
            if (userInput.equals("1") || userInput.equals("stop")) {
                stop();
            } else if (userInput.equals("2") || userInput.equals("continue")) {

                return;
            } else {
                System.out.println("Invalid input");
            }
        }
        stop();
    }

}