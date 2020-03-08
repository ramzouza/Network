package Network.Server;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

import Network.Builder.GETRequestBuilder;
import Network.Builder.POSTRequestBuilder;
import Network.Builder.RequestBuilder;
import Network.Client.Response;


public class ServerWorker {

    private Socket _socket;
    private RequestBuilder req;
    private Response _response;
    private String _path;
    private String _rootFolder;

    public ServerWorker(String rootFolder, Socket socket)
    {
        this._rootFolder = rootFolder;
        this._socket = socket;
        System.out.println("client connected");
    }

    public void Process()
    {
        try {
            PrintWriter out = new PrintWriter(this._socket.getOutputStream());
   
            // parse incoming request
            Scanner in = new Scanner(this._socket.getInputStream());
            this.evaluateFirstline( in .nextLine());
            this.req.buildRequest( in );

            // process request
            this._response = new Response(this.req.getVersion());
            this._path = this.req.getUrl();
            if (this.req.getMethod().equals("GET"))
            {
                this.executeGet();
            }
            
            // send response
            System.out.println(this._response.verboseToString());
            out.write(this._response.verboseToString());
            out.close();
            System.out.println(this.req.toString());
                
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void evaluateFirstline(String content) {
		String[] values = content.split(" ");
		
		if (values[0].equals("GET")) {
			req = new GETRequestBuilder();
		}   
	   else if(values[0].equals("POST")){
	     req = new POSTRequestBuilder();
	   }
	   req.setURL(values[1]);
	   req.setVersion(values[2]);
    }

    public void executeGet() {
        if (!this.CheckPath())
        {
            return;
        }
        
        try {
           File f = new File(Paths.get(this._path).toAbsolutePath().normalize().toString());
           if (!f.exists())
           {
                this._response.setCode("404");
                this._response.setPhrase("Not Found");
                return;
           }

           if (f.isDirectory())
           {
               File[] allfiles = f.listFiles();
               for (File file : allfiles) {
                   this._response.appendEntityBody(file.getName());
               }
               this._response.setCode("200");
               this._response.setPhrase("OK");
            }
           else if (f.isFile())
           {
               if (f.canRead())
               {
                    List<String> lines =  Files.readAllLines(Paths.get(this._path), StandardCharsets.UTF_8);
                    this._response.setEntityBody(String.join(System.lineSeparator(), lines));          
                    this._response.setCode("200");
                    this._response.setPhrase("OK");
                }
                else
                {
                    this._response.setCode("403");
                    this._response.setPhrase("Forbidden");
                }
           }
           else
           {
                this._response.setCode("400");
                this._response.setPhrase("Bad Request");
           }
        } catch (Exception e) {
            this._response.setCode("500");
            this._response.setPhrase("Internal Server Error");
        }
    }

    public boolean CheckPath(){
        if (this._path ==  null)
        {
            this._response.setCode("400");
            this._response.setPhrase("Bad Request");
            return false;
        }

        if (this._path.contains(".."))
        {
            this._response.setCode("403");
            this._response.setPhrase("Forbidden");
            return false;
        }

        this._path = this._path.replace("/", "\\");

        Path p = Paths.get(this._path);
        if (p.isAbsolute())
        {
            this._response.setCode("403");
            this._response.setPhrase("Forbidden");
            return false;
        }

        if (this._path.startsWith("\\")){
            this._path = this._rootFolder + this._path;
        }
        else {
            this._path = this._rootFolder + "\\" + this._path;
        }

        return true;
    }


}
