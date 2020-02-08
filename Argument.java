import java.lang.module.ModuleDescriptor.Builder;

import Network.Builder.*;

public class Argument {
    private boolean verbose;
    private String requestType;
    private String header;
    private String data;
    private String file;
    private String URL;
    private String body;
   
    Argument() {
        this.verbose = false;
        this.requestType = "";
        this.URL = "";
        this.header = "";
        this.data = "";
        this.file = "";
           }

    Argument(boolean v, String type, String url, String h, String d, String f ) {
        this.verbose = v;
        this.requestType = type;
        this.URL = url;
        this.header = h;
        this.data = d;
        this.file = f;
        
    }

   
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getHeader() {
        return header;
    }

    public void addHeader(String header) {
        this.header += header;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

