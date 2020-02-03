package Network.Client;

public class Response {
    private String version;
    private String code;
    private String phrase;
    private String header;
    private String entityBody;

    
    public Response(String version, String code, String phrase, String header, String entityBody) {
        this.version = version;
        this.code = code;
        this.phrase = phrase;
        this.header = header;
        this.entityBody = entityBody;
    }
    @Override
    public String toString() {
       
        return this.version + this.code + " " + this.phrase + "\r\n" +  this.header  + "\r\n" + entityBody ;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getEntityBody() {
        return entityBody;
    }

    public void setEntityBody(String entityBody) {
        this.entityBody = entityBody;
    }
    



}