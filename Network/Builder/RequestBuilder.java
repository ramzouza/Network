package Network.Builder;

import java.util.Scanner;

public class RequestBuilder {
protected String method; 
protected String URL;
protected String version = "HTTP/1.0"; 
protected String header = "";
protected String entityBody = "";

    public RequestBuilder() {
    }
    public RequestBuilder(String method, String URL, String header, String entityBody){
        this.method = method;
        this.URL = URL;
        this.header = header;
        this.entityBody = entityBody;
    }

	public Boolean verifyRequest(){       
        return true;
    }
    @Override
    public String toString() {
        return this.method + this.URL + " " + this.version + "\r\n" +  this.header+ "\r\n" + this.entityBody;
    }

    public void buildRequest(Scanner in){
       
        String header = "";
        String entity = "";

        while ( in .hasNextLine()) {
        String temp = (String)in.nextLine();   
      
        if (temp.equals(""))
        {
                this.header = header;
                while ( in .hasNextLine()) {
                    entity += (String)in.nextLine() + "\r\n";
                   }
                   this.entityBody = entity;
        }
        else{
            header += temp + "\r\n";
        }
        
       	
    }
}
    public void setURL(String uRL) {
        URL = uRL;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }



}
