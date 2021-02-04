import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/** Establish a SSL connection to a host and port, writes a byte and
 * prints the response. See
 * http://confluence.atlassian.com/display/JIRA/Connecting+to+SSL+services
 */
public class SSLPoke {
    public static void main(String[] args) {

        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("maps.googleapis.com", 443);

            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();

            // Write a test byte to get a reaction :)
            out.write(1);

            while (in.available() > 0) {
                System.out.print(in.read());
            }
            System.out.println("KDS Successfully connected");

        } catch (Exception exception) {
            System.out.println("KDS Failed connecting");
            exception.printStackTrace();
        }
        try{
            String testUrl = "https://maps.googleapis.com/";
            System.out.println("KDS Testing URL: " + testUrl);
            String pageText = null;
            URLConnection conn = new URL(testUrl).openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                pageText = reader.lines().collect(Collectors.joining("\n"));
            }
            if(pageText != null){
                System.out.println("KDS pageText: " + pageText);
            }else{
                System.out.println("KDS pageText: null!!!");
            }
        }catch(Exception e){
            System.out.println("KDS Error in SmokeTest connecting to URL: " + e.getMessage());
            e.printStackTrace();

        }

    }
}
