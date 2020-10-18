package controller;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9000;


    private boolean cancel = true;
    private List<Process> processes;
    private String action;
    private InetSocketAddress address;
    private Process processF;
    private Process processG;
    private int count = 0;


    public Process getProcessF() {
        return processF;
    }

    public Process getProcessG() {
        return processG;
    }


    public Server(String action) {
        this.address = new InetSocketAddress("localhost", PORT);
        this.action = action;
        processes = new ArrayList<>();

    }
    public void run() throws IOException{

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        String pathF = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionF_jar/functionF.jar";
        String pathG = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionG_jar/functionG.jar";
        ProcessBuilder builderF = new ProcessBuilder("java", "-jar", pathF);
        ProcessBuilder builderG = new ProcessBuilder("java", "-jar", pathG);

        processF = builderF.start();
        processG = builderG.start();
        processes.add(processF);
        processes.add(processG);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(cancel){
           SocketChannel socketChannel = serverSocketChannel.accept();

           if (socketChannel != null) {
               buffer.clear();
               buffer.put(action.getBytes());
               buffer.flip();
               socketChannel.write(buffer);
               buffer.clear();
               int size = socketChannel.read(buffer);
               if(size == -1) {
                   socketChannel.close();
                   continue;
               }
               byte[] data = new byte[size];
               buffer.flip();
               buffer.get(data);
               String gotData = new String(data);
               System.out.println(gotData);
               count++;
               if (count == 2) {
                   cancel = false;
               }

           }

        }
        processF.destroy();
        processG.destroy();


   }

   public static void main(String[] args) throws IOException {
       new Server( "1").run();
   }


}
