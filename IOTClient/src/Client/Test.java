package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String args[]) throws Exception {

//        String status1 = getStatus("https://www.java2blog.com");
//        System.out.println("Java2blog.com is : " + status1);

//        String status2 = getStatus("http://127.0.0.1:8080/get");
//        System.out.println("Máy bơm : " + status2);
        requestPOST("http://127.0.0.1:3000");

    }

    public static void requestPOST(String inputurl) throws IOException {
        URL url = new URL(inputurl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
//    StringBuffer sb = new StringBuffer();
//    sb.append("{");
//    sb.append("\"id\":\" giatri \"");
        String jsonInputString = Utils.ControlPackage(1) ;
        System.out.println(jsonInputString);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
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
        }
    }
//public static void requestPOST(String inputurl) throws IOException {
//    URL url = new URL(inputurl);
//    HttpURLConnection con = (HttpURLConnection)url.openConnection();
//    con.setRequestMethod("POST");
//    con.setRequestProperty("Content-Type", "application/json; utf-8");
//    con.setRequestProperty("Accept", "application/json");
//    con.setDoOutput(true);
////    StringBuffer sb = new StringBuffer();
////    sb.append("{");
////    sb.append("\"id\":\" giatri \"");
//    String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
//    System.out.println(jsonInputString);
//    try(OutputStream os = con.getOutputStream()) {
//        byte[] input = jsonInputString.getBytes("utf-8");
//        os.write(input, 0, input.length);
//    }
//    try(BufferedReader br = new BufferedReader(
//            new InputStreamReader(con.getInputStream(), "utf-8"))) {
//        StringBuilder response = new StringBuilder();
//        String responseLine = null;
//        while ((responseLine = br.readLine()) != null) {
//            response.append(responseLine.trim());
//        }
//        System.out.println(response.toString());
//    }
//    }
}
