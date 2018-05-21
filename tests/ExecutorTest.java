import org.teemo.Server;
import org.teemo.api.Executor;
import org.teemo.api.MacPing;

public class ExecutorTest {


    public static void main(String[] args) {
        Executor.execute(new MacPing(), Server.LAN, (ping) ->{
            System.out.println(ping+" ms");
        });
    }
}
