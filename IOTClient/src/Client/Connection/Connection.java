package Client.Connection;

import Client.Utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    public static void main(String args[]) throws Exception {

//        requestPOST(Utils.SigninPackage("nhom14","nhom14"));
//        System.out.println(Utils.ControlPackage(1));
//        System.out.println(Utils.SigninPackage("a","b"));
//        System.out.println(Utils.GetInfoPackage("auysgdyua67s76gyrsdf"));
    }

    public static String requestPOST(String json) throws IOException {
        URL url = new URL(Utils.API_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        System.out.println(json);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            return response.toString();
        }
    }
}
