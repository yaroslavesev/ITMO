package Response;

import java.io.*;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final STATUS status;
    private final String message;
    private final Object object;
    public Response(STATUS status, String message, Object object){
        this.status = status;
        this.message = message;
        this.object = object;
    }
    public Response(STATUS status, String message){
        this.status = status;
        this.message = message;
        this.object = "";
    }
    public Response(STATUS status){
        this.status = status;
        this.message = "";
        this.object = "";
    }
    public STATUS getStatus() {
        return status;
    }
    @Override
    public String toString() {
        if (status.equals(STATUS.ERROR)){
            if (message.equals("Вы ничего не ввели")){
                return "";
            }
            return "Что-то пошло не так...\n" + message;
        }
        return message + "\n" + object.toString();
    }
    public String getMessage(){
        return message;
    }
    public Object getObject(){
        return object;
    }
}
