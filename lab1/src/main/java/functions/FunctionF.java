package functions;
import spos.lab1.demo.IntOps;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;

public class FunctionF {
    private SocketChannel socket;
    public FunctionF(int port) throws IOException  {
        InetSocketAddress address = new InetSocketAddress("localhost", port);
        socket = SocketChannel.open(address);
    }
    void run() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        socket.read(buffer);
        buffer.flip();
        byte[] messageByte = new byte[1000];
        buffer.get(messageByte);
        String message = new String(messageByte);
        buffer.clear();
        int action = Integer.parseInt(message);
        Integer fValue = IntOps.funcF(action);

        buffer.clear();
        buffer.putInt(fValue);
        socket.write(buffer);

    }
    public static void main(String[] args) {

    }
}
