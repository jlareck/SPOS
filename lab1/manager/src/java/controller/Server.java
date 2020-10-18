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

    private int functionsAmount;
    private List<Integer> args;

    private boolean cancel = true;
    private List<Process> processes;
    private String action;
    private InetSocketAddress address;
    private Process processF;
    private Process processG;

    public Process getProcessF() {
        return processF;
    }

    public Process getProcessG() {
        return processG;
    }


    public Server(int port, String action) {
        this.address = new InetSocketAddress("localhost", port);
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

               int numRead = socketChannel.read(buffer);
               if(numRead == -1) {
                   socketChannel.close();
                   continue;
               }
               //creating byte array for message
               byte[] data = new byte[numRead];
               System.arraycopy(buffer.array(), 0, data, 0, numRead);
               String gotData = new String(data);
               System.out.println(gotData);
//               if (!gotData.isEmpty()){
//                   break;
//               }
             //  handle(socketChannel);
           }

        }


   }
   private void sendMessage(SocketChannel socket){
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            String s = action;
            byteBuffer.put(s.getBytes());
            byteBuffer.flip();
            socket.write(byteBuffer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handle(SocketChannel socket){
        try {
            sendMessage(socket);

            read(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that reading message from client
     *
     * @throws IOException
     */
    private void read(SocketChannel socket) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int numRead = socket.read(byteBuffer);
        if (numRead == -1) {
            socket.close();
            return;
        }
        //creating byte array for message
        byte[] data = new byte[numRead];
        System.arraycopy(byteBuffer.array(), 0, data, 0, numRead);
        String gotData = new String(data);
        System.out.println("Got:" + gotData);

    }
   public static void main(String[] args) throws IOException {
       new Server(9000, "1").run();
   }


}
