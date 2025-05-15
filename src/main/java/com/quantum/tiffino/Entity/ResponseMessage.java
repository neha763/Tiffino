package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON response
public class ResponseMessage {



    private String message;
    private Object data; // Can hold any response object (e.g., DeliveryPerson)
    private boolean success = false; // Default value



    // Constructor for message only
    public ResponseMessage(String message) {
        this.message = message;
    }

    // Constructor for message and success flag
    public ResponseMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Constructor for message, success flag, and additional data
    public ResponseMessage(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "message='" + message + '\'' +
                ", data=" + (data != null ? data.toString() : "null") +
                ", success=" + success +
                '}';
    }
}
