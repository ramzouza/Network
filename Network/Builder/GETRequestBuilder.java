package Network.Builder;

import java.util.Scanner;

public class GETRequestBuilder extends RequestBuilder {
   
    public GETRequestBuilder() {}



   public GETRequestBuilder(String method, String URL, String header, String entityBody) {
        super(method, URL, header, entityBody);
        // TODO Auto-generated constructor stub
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
                this.entityBody= "";
                return ;
        }
        else{
            header += temp + "\r\n";
        }
        
       	
    }
}


}