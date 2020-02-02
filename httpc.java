
public class httpc {

    public String help (String temp){
        switch (temp) {
            case "":
            return " httpc is a curl-like application but supports HTTP protocol only. \nUsage: \n httpc command [arguments] \n The commands are:\n" +
             "get executes a HTTP GET request and prints the response. \n" +
             "post executes a HTTP POST request and prints the response. \n" +
             "help prints this screen. \n " +
             "Use \"httpc help [command]\" for more information about a command."; 
            case "get" :    
            return   "Get executes a HTTP GET request for a given URL.\n"+
            "-v Prints the detail of the response such as protocol, status and headers.\n" +
            "-h key:value Associates headers to HTTP Request with the format 'key:value'.";
                
            default:
                break;
        }
        return null;
    }

public static void main(String[] args) {
    httpc temp = new httpc();
    temp.help("get");
    System.out.println(temp.help("get"));
}


}