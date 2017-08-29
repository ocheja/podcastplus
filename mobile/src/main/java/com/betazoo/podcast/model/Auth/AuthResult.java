
package com.betazoo.podcast.model.Auth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthResult implements Parcelable
{

    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<AuthResult> CREATOR = new Creator<AuthResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AuthResult createFromParcel(Parcel in) {
            AuthResult instance = new AuthResult();
            instance.errors = ((Errors) in.readValue((Errors.class.getClassLoader())));
            instance.data = ((Data) in.readValue((Data.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public AuthResult[] newArray(int size) {
            return (new AuthResult[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthResult() {
    }

    /**
     * 
     * @param message
     * @param errors
     * @param status
     * @param data
     */
    public AuthResult(Errors errors, Data data, String message, String status) {
        super();
        this.errors = errors;
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(errors);
        dest.writeValue(data);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
