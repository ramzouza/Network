package Network.Client;

public class Response {
    private String version;
    private String code;
    private String phrase;
    private String header;
    private String entityBody;

    public Response(String version){
        this.version = version;
        this.entityBody = "";
        this.phrase = "";
        this.header = "";
    }
    
    public Response(String version, String code, String phrase, String header, String entityBody) {
        this.version = version;
        this.code = code;
        this.phrase = phrase;
        this.header = header;
        this.entityBody = entityBody;
    }
    
    public String verboseToString() {
       
        return this.version + " " + this.code + " " + this.phrase + "\r\n" +  this.header  + "\r\n" + entityBody ;
    }

    @Override
    public String toString() {
       
        return  entityBody ;
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

    public void appendEntityBody(String entityBody) {
        this.entityBody += entityBody;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}