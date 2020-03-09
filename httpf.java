import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import Network.Server.ServerWorker;

import Network.Server.Server;

public class httpf {
    static serverArgument parameter = new serverArgument();
    static String DefaultWorkingDirectory = "C:\\Github\\Network\\Network\\Files";
    public static void main(String[] args) {
//        args = new String[] { "-v" , "-p","200"};

        initArgument(args);
        Server server = new Server(Integer.parseInt(parameter.getPort()), "localhost");
        server.initSocket();
        while (true) {
            Socket socket = server.accept();
            if (socket != null)
            {
                ServerWorker worker = new ServerWorker(parameter.getPath(), socket);
                worker.Process();
            }
        }

    }

    public static void initArgument(String[] args) {

        parameter.setPath(httpf.DefaultWorkingDirectory);

        if (contains(args, "-v")) {
            parameter.setVerbose(true);
        }

        if(contains(args, "-p")){
            setPort(args);
        }

        if(contains(args, "-d")){
            setPath(args);
            if (!isSecure()) {
                System.out.println("path is not secure or not found trying to access another directory");
                System.exit(0);
                // return error with bad request 
            }    
        }
    }


    public static boolean isSecure() {
        if (parameter.getPath() == null)
        {
            parameter.setPath(".");
            return true;
        }

        File wdir = new File(Paths.get(parameter.getPath()).toAbsolutePath().normalize().toString());
        if (!wdir.exists() || !wdir.isDirectory())
        {
            return false;
        }

        parameter.setPath(wdir.getPath());
        return true;
    }


    private static void setPath(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equalsIgnoreCase("-d")) {
                parameter.setPath(arguments[i + 1]);
                return;
            }
        }

        parameter.setPath(httpf.DefaultWorkingDirectory);
    }

    private static void setPort(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equalsIgnoreCase("-p")) {
                parameter.setPort(arguments[i + 1]);
                return;
            }
        }

        parameter.setPort("8080");
    }

    public static Boolean contains(String[] arguments, String character) {
        for (String element: arguments) {
            if (character.equals(element)) {
                return true;
            }
        }
        return false;
    }


}