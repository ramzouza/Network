package Network.Server;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;


import Network.Builder.GETRequestBuilder;
import Network.Builder.POSTRequestBuilder;
import Network.Builder.RequestBuilder;
import Network.Client.Response;
import Network.Server.ServerLock;


public class ServerWorker implements Runnable {

    private Socket _socket;
    private RequestBuilder req;
    private Response _response;
    private String _path;
    private String _rootFolder;
    private ServerLock _locks;

    public ServerWorker(ServerLock locks, String rootFolder, Socket socket)
    {
        this._locks = locks;
        this._rootFolder = rootFolder.toLowerCase();
        this._socket = socket;
        System.out.println("client connected");
    }

    public void Process()
    {
        try {
            PrintWriter out = new PrintWriter(this._socket.getOutputStream());
   
            Scanner in = new Scanner(this._socket.getInputStream());
            this.evaluateFirstline( in .nextLine());
            this.req.parseRequest( in );
            System.out.println(this.req.toString());
 
            // process request
            this._response = new Response(this.req);
            this._path = this.req.getUrl();
            if (this.req.getMethod().equals("GET"))
            {
                this.executeGet();
            }
            else
            {
                this.executePost();
            }
            
            // send response
            System.out.println(this._response.verboseToString());
            out.write(this._response.verboseToString());
            out.close();
                
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

    private void executeGet() {
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
               this.ProcessDirectory(f);
           }
           else if (f.isFile())
           {
               this.ProcessFile(f);
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

    private void ProcessDirectory(File f)
    {
        File[] allfiles = f.listFiles();
        this._response.appendEntityBody("<html><body>");
        for (File file : allfiles) {
            this._response.appendEntityBody(this.getFileEntry(file));
        }
        this._response.appendEntityBody("</body></html>");
        this._response.setFileName("list.html");
        this._response.setCode("200");
        this._response.setPhrase("OK");
    }

    private void ProcessFile(File f)
    {
        boolean locked = false;
        try {
            if (this._locks.CanRead(this._path)){
                locked = true;
                if (f.canRead()){
                    this._response.setFileName(this._path);
                    if (this._response.IsBinaryType()){
                        byte[] data = Files.readAllBytes(Paths.get(this._path));
                        this._response.setEntityBody(Base64.getEncoder().encodeToString(data));
                    }
                    else{
                        List<String> lines =  Files.readAllLines(Paths.get(this._path), StandardCharsets.UTF_8);
                        this._response.setEntityBody(String.join(System.lineSeparator(), lines)); 
                    }
                    this._response.setCode("200");
                    this._response.setPhrase("OK");
                }
                else{
                    this._response.setCode("403");
                    this._response.setPhrase("Forbidden");
                }
            }
            else{
                this._response.setCode("403");
                this._response.setPhrase("Forbidden-Locked");
            }
        } catch (Exception e) {
            this._response.setCode("500");
            this._response.setPhrase("Internal Server Error");
        } finally{
            if (locked){
                this._locks.CompleteRead(this._path);
            }
        }
    }

    private String getFileEntry(File file)
    {
        String filePath = file.getAbsolutePath().toLowerCase();
        if (filePath.startsWith(this._rootFolder))
        {
            filePath = filePath.substring(this._rootFolder.length());
        }

        return "<a href='" + filePath + "'>" + file.getName() + "</a></br>";
    }

    private void executePost() {
        if (!this.CheckPath())
        {
            return;
        }
        
        boolean locked = false;
        try {
           File f = new File(Paths.get(this._path).toAbsolutePath().normalize().toString());
           if (f.isDirectory())
           {
               this._response.setCode("400");
               this._response.setPhrase("Bad Request");
               return;
           }

            if (this._locks.CanWrite(this._path)){
                locked = true;
                if (f.exists() && f.isFile()){
                    if (f.canWrite()){
                        Files.write(Paths.get(this._path), this.req.getEntityBody().getBytes());
                        this._response.setCode("200");
                        this._response.setPhrase("OK");
                    }
                    else{
                        this._response.setCode("403");
                        this._response.setPhrase("Forbidden");
                    }
                }
                else {
                        Path p = Paths.get(this._path);
                        this.EnsureDirectory(p.getParent());
                        Files.write(p, this.req.getEntityBody().getBytes());
                        this._response.setCode("201");
                        this._response.setPhrase("Created");
                }
            } 
            else {
                this._response.setCode("403");
                this._response.setPhrase("Forbidden-Locked");
            }
        } catch (Exception e) {
            this._response.setCode("500");
            this._response.setPhrase("Internal Server Error");
        }
        finally{
            if (locked){
                this._locks.CompleteWrite(this._path);
            }
        }

    }

    private boolean CheckPath(){
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

    public void EnsureDirectory(Path p)
    {
        if (p == null)
        {
            return;
        }

        File f =  new File(p.toAbsolutePath().normalize().toString());
        if (!f.exists())
        {
            this.EnsureDirectory(p.getParent());
            f.mkdir();
        }
    }

    @Override
    public void run() {
        this.Process();
    }
}
