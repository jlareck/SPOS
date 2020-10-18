package controller;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 4004;

    private int functionsAmount;
    private List<Integer> args;

    private boolean cancel = false;
    private List<Process> processes;
    private int action;
    private InetSocketAddress address;
    private Process processF;
    private Process processG;

    public Process getProcessF() {
        return processF;
    }

    public Process getProcessG() {
        return processG;
    }


    public Server(String host, int port, int action) {
        this.address = new InetSocketAddress(host, port);
        this.action = action;
        processes = new ArrayList<>();

    }
    public void run() throws IOException{

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        String pathF = "";
        String pathG = "";
        ProcessBuilder builderF = new ProcessBuilder("java", "-jar", pathF, String.valueOf(this.action));
        ProcessBuilder builderG = new ProcessBuilder("java", "-jar", pathG, String.valueOf(this.action));

        processF = builderF.start();
        processG = builderG.start();
        processes.add(processF);
        processes.add(processG);

        while(cancel){
           SocketChannel socketChannel = serverSocketChannel.accept();

           if (socketChannel != null) {




           }

        }


   }


}
