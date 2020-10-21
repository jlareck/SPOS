import spos.lab1.demo.IntOps;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class FunctionG {

    private SocketChannel socketChannel;
    public FunctionG() throws IOException  {

    }
    void run(String messageStr) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        byte[] message = new byte[socketChannel.read(buffer)];
//        buffer.flip();
//        buffer.get(message);
//        String messageStr = new String(message);
//        buffer.clear();
      //  Thread.sleep(5000);
        int value = IntOps.funcG(Integer.parseInt(messageStr));
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9000));
        System.out.println(value);
        String result = "G "+ value;
        buffer.put(result.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new FunctionG().run(args[0]);
    }
}
