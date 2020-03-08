package Network.Builder;

import java.util.Scanner;
import java.net.URL;

public abstract class RequestBuilder {
    protected String URL;
    protected String version = "HTTP/1.0"; 
    protected String header = "";
    protected String entityBody = "";

    protected RequestBuilder() {
    }
    
    protected RequestBuilder(String URL, String header, String entityBody){
        try
        {
            URL url = new URL(URL);
            this.URL = url.getFile();

            this.header = header;
            this.entityBody = entityBody;
        }
        catch(Exception e)
        {
        }
    }

	public Boolean verifyRequest(){       
        return true;
    }
    @Override
    public String toString() {
        return this.getMethod() + " " + this.URL + " " + this.version + "\r\n" +  this.header+ "\r\n" + this.entityBody;
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
    
    public String getUrl()
    {
        return this.URL;
    }


    public abstract String getMethod();

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return this.version;
    }

}
