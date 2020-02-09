package Network.Builder;

public class POSTRequestBuilder extends RequestBuilder {
   
    public POSTRequestBuilder(String method, String URL, String header, String entityBody) {
        super(method, URL, header, entityBody);
        
    }

    Boolean verifyRequest(){
        
        // THIS MUST BE CODED
        return true;
    }

    @Override
    public String toString() {
        return this.method + this.URL + " " + this.version + "\r\n" +  this.header+ "\r\n" + this.entityBody;
    }
}