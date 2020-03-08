package Network.Builder;

import java.util.Scanner;

public class POSTRequestBuilder extends RequestBuilder {
   
    public POSTRequestBuilder() {}

    
    public POSTRequestBuilder(String method, String URL, String header, String entityBody) {
        super(method, URL, header, entityBody);
        
    }


public Boolean verifyRequest(){
        
        // THIS MUST BE CODED
        return true;
    }

    @Override
    public String toString() {
        return this.method + this.URL + " " + this.version + "\r\n" +  this.header+ "Content-Length: " +entityBody.length() +"\r\n\r\n" + this.entityBody;
    }
    @Override
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
}