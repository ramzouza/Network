public class Argument {
    private boolean verbose;

    Argument (){
        this.verbose = false;
    }

    Argument(boolean v){
        this.verbose = v;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}