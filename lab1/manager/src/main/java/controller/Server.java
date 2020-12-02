package controller;

import interaction.KeyInteractor;
import interaction.PauseHandler;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9000;

    public static boolean cancel = true;
    private boolean check = false;
    private List<Integer> results;
    private String action;
    private InetSocketAddress address;

    public static Process getProcessF() {
        return processF;
    }

    public static Process getProcessG() {
        return processG;
    }

    private static Process processF;
    private static Process processG;

    public static boolean fDone = false;
    public static boolean gDone = false;

    private int count = 0;
    List<String> resultStr;
    public Server(String action) {
        this.address = new InetSocketAddress("localhost", PORT);
        this.action = action;
        results = new ArrayList<>();
        resultStr = new ArrayList<>();
    }
    public void run() throws IOException {
        new Thread( ( ) -> {
            KeyInteractor keyHandler = new KeyInteractor();
            keyHandler.start();
        } ).start();
        try {
            long start, end;
            start = System.nanoTime();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(address);
            serverSocketChannel.configureBlocking(false);
            String pathF = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionF_jar/functionF.jar";
            String pathG = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionG_jar/functionG.jar";
            ProcessBuilder builderF = new ProcessBuilder("java", "-jar", pathF, String.valueOf(action));
            ProcessBuilder builderG = new ProcessBuilder("java", "-jar", pathG, String.valueOf(action));
            if (action.equals("0") || action.equals("2") || action.equals("4")) {
                processF = builderF.start();
                processG = builderG.start();
            }
            else {
                processG = builderG.start();
                processF = builderF.start();
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(cancel) {
                SocketChannel socketChannel = serverSocketChannel.accept();
               // System.out.println("Block");
                if (socketChannel != null) {

                    buffer.clear();
                    buffer.put(action.getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.clear();
                    int size = socketChannel.read(buffer);
                    if (size == -1) {
                        socketChannel.close();
                        continue;
                    }
                    byte[] data = new byte[size];
                    buffer.flip();
                    buffer.get(data);
                    buffer.clear();
                    String gotData = new String(data);


                    String[] parsedData = gotData.split(" ");
                    //String functionResult = "The result of function " + parsedData[0] + " is: " + parsedData[1];

                    //resultStr.add(functionResult);

                    if (parsedData[0].equals("F")) {
                        System.out.println("Server received result from function F: "+ parsedData[1]);
                        fDone = true;
                    }

                    else if (parsedData[0].equals("G")) {
                        System.out.println("Server received result from function G " + parsedData[1]);
                        gDone = true;
                    }
                    if (parsedData[1].equals("0")) {
                        System.out.println("The function " + parsedData[0] + " returned zero");
                        if(parsedData[0].equals("F")) {
                            System.out.println("STATUS: function G hangs");

                        }
                        else {
                            System.out.println("STATUS: function F hangs");
                        }
                        System.out.println("Result of calculation is zero");
                        end = System.nanoTime();
                        System.out.println("Time of execution: " + (end - start));

                       // PauseHandler.stop();
                        break;
                    }



                    results.add(Integer.parseInt(parsedData[1]));

                    if (fDone && gDone) {
                        System.out.println("STATUS: SUCCESS");
                        int res = 1;
                        for (Integer value: results) {
                            res *= value;
                        }
                        resultStr.add("Multiplication result: " + res);

                        break;
                    }
                }
            }
        } catch (IOException error) {
            serverSocketChannel.close();
            PauseHandler.stop();
        }
        destroyProcesses();
        for (String str: resultStr) {
            System.out.println(str);
        }
        System.exit(0);
    }
    private static void destroyProcesses() {
        processF.destroy();
        processG.destroy();
    }

    public static void main(String[] args) throws IOException {
         new Menu().menu();
    }
}
