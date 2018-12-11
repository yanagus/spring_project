package work.controller;

public class ResponseMsg {

    private String error;

    public ResponseMsg(String error) {
        this.error = error;
    }

    public String getMessage() {
        return error;
    }

    public void setMessage(String error) {
        this.error = error;
    }
}
