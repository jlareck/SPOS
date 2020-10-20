import spos.lab1.demo.IntOps;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.apache.commons.lang3.SerializationUtils;
public class FunctionG {

    private SocketChannel socketChannel;
    public FunctionG() throws IOException  {
        InetSocketAddress address = new InetSocketAddress("localhost", 9000);
        socketChannel = SocketChannel.open(address);
    }
    void run(String messageStr) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        byte[] message = new byte[socketChannel.read(buffer)];
//        buffer.flip();
//        buffer.get(message);
//        String messageStr = new String(message);
//        buffer.clear();

        int value = IntOps.funcG(Integer.parseInt(messageStr));
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