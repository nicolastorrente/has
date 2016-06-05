package api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.eclipse.jetty.websocket.api.StatusCode;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse implements Serializable{

    public enum Status {OK, ERROR}

    private Status status;
    private Object cause;

    public SimpleResponse(Status status) {
        this.status = status;
    }

    public SimpleResponse(Status status, Object cause) {
        this.status = status;
        this.cause = cause;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getCause() {
        return cause;
    }

    public void setCause(Object cause) {
        this.cause = cause;
    }
}
