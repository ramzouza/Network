import Network.Builder.RequestBuilder;
import Network.Client.Client;

public class httpc {
   
    static Argument parameter = new Argument();

    public static void main(String[] args) {
        args = new String[] { "POST","-h", "Content-Type:application/json" ,"http://httpbin.org/post?course=networking&assignment=1" };

        for (int i = 0; i < args.length; i++) {
            if (args[0].equals("help")) {
                try {
                    args[1] = args[1];
                } catch (Exception e) {
                    System.out.println(help(""));
                    System.exit(0);
                }
                System.out.println(help(args[1]));
                System.exit(0);
            }

        }
        initArgument(args);
        RequestBuilder req = new RequestBuilder(parameter.getRequestType(), parameter.getURL(), parameter.getHeader(), parameter.getBody());
        Client net = new Client(req, parameter.getURL());
        net.request();
        
        if (parameter.isVerbose()) {
            System.out.println(net.getRes().verboseToString());    
        }
        else{
            System.out.println(net.getRes().toString());            
        }

    }

    public static void initArgument(String[] args) {
        setHeaders(args);
        System.out.println(parameter.getHeader());
        if (contains(args, "-v")) {
            parameter.setVerbose(true);
        }

        setURL(args);
        
        

    }

    public static String help(String temp) {
        switch (temp) {
        case "":
            return "\n\nusage: httpc get [-v] [-h key:value] URL"
                    + "httpc is a curl-like application but supports HTTP protocol only. \nUsage: \nhttpc command [arguments] \nThe commands are:\n"
                    + "get executes a HTTP GET request and prints the response. \n"
                    + "post executes a HTTP POST request and prints the response. \n" + "help prints this screen. \n"
                    + "Use \"httpc help [command]\" for more information about a command.";
        case "get":
            return "Get executes a HTTP GET request for a given URL.\n"
                    + "-v Prints the detail of the response such as protocol, status and headers.\n"
                    + "-h key:value Associates headers to HTTP Request with the format 'key:value'.";
        case "post":
            return "usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL \n"
                    + "Post executes a HTTP POST request for a given URL with inline data or from file. \n"
                    + "-v Prints the detail of the response such as protocol, status and headers. \n"
                    + "-h key:value Associates headers to HTTP Request with the format 'key:value'.\n"
                    + "-d string Associates an inline data to the body HTTP POST request. -f file Associates the content of a file to the body HTTP POST request.\n"
                    + "Either [-d] or [-f] can be used but not both.";
        default:
            return " The help you are trying to get does not exist";
        }
    }

    public static Boolean contains(String[] arguments, String character) {
        for (String element : arguments) {
            if (character.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static void setHeaders(String[] arguments)
    {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments [i].equalsIgnoreCase("-h")){
                parameter.addHeader(arguments[i+1 ] + "\r\n");
            }
        }
    }

    public static void setURL(String[] arguments)
    {
        parameter.setRequestType(arguments[0] + " ");
        parameter.setURL(arguments[arguments.length-1]);
    }
    }






   