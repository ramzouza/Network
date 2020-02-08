package Network.Builder;

public class RequestBuilder {
private String method; 
private String URL;
final private String version = "HTTP/1.0"; 
private String header = "";
private String entityBody = "";

    public RequestBuilder(String method, String URL, String header, String entityBody){
        this.method = method;
        this.URL = URL;
        this.header = header;
        this.entityBody = entityBody;
    }

    @Override
    public String toString() {
        return this.method + this.URL + " " + this.version + "\r\n" +  this.header + "\r\n"  + "\r\n" ;

    }



}
