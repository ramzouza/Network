package Network.Builder;

public class RequestBuilder {
protected String method; 
protected String URL;
protected final String version = "HTTP/1.0"; 
protected String header = "";
protected String entityBody = "";

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


    public void setURL(String uRL) {
        URL = uRL;
    }



}
