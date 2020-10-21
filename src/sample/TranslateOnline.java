package sample;
/** |------------------------------------------------------------------|
 *  | class này có nhiệm vụ là lấy văn bản đã đc dịch từ script URL về |
 *  |------------------------------------------------------------------|
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateOnline  {
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // my script url
        String urlStr = "https://script.google.com/macros/s/AKfycbxK9ijVdOl3KMb-8vH9pnlFS0Ay8DOcUoLSilPZnjELpk77AuA/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
