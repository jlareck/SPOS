package controller;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public void menu() throws IOException {

            String action;
            Scanner selector = new Scanner(System.in);

            System.out.println("Choose action: ");
            System.out.println("0 F finishes before G with non value");
            System.out.println("1 G finishes before F with non value");
            System.out.println("2 F finishes zero  G hangs");
            System.out.println("3 G finishes zero F hangs");
            System.out.println("4 F finishes non zero value G hangs");
            System.out.println("5 G finishes non zero value F hangs");
            action = selector.nextLine();
            new Server( action).run();


    }
}
