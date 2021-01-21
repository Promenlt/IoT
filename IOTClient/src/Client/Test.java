package Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String args[]) throws Exception {

//        String status1 = getStatus("https://www.java2blog.com");
//        System.out.println("Java2blog.com is : " + status1);

        String status2 = getStatus("http://127.0.0.1:8080/get");
        System.out.println("Máy bơm : " + status2);

    }

    public static String getStatus(String url) throws IOException {

        String result = "";
        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            // Set connection timeout
            con.setConnectTimeout(3000);
            con.connect();

            int code = con.getResponseCode();
            if (code == 200) {
                result = "On";
            } else {
                result="Off";
            }
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
