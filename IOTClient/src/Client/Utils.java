package Client;

public class Utils {
    public static String API_URL = "http://127.0.0.1:3000";
    public static String ControlPackage(int id){
        return "{\n\"code\" : \"control\",\n\"id\" : \""+id+"\"\n}";
    }
    public static String SigninPackage(String username, String password) {
        return "{\n\"code\" : \"signin\",\n\"username\" : \""+username+"\" ,\n\"password\" : \""+password+"\"\n}";
    }
    public static String GetInfoPackage(String id){
        return "{\n\"code\" : \"info\",\n\"id\" : \""+id+"\"\n}";
    }
}
