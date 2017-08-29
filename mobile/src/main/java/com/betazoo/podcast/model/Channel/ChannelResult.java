
package com.betazoo.podcast.model.Channel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChannelResult implements Parcelable
{

    @SerializedName("errors")
    @Expose
    private Object errors;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<ChannelResult> CREATOR = new Creator<ChannelResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ChannelResult createFromParcel(Parcel in) {
            ChannelResult instance = new ChannelResult();
            instance.errors = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.data = ((Data) in.readValue((Data.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ChannelResult[] newArray(int size) {
            return (new ChannelResult[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChannelResult() {
    }

    /**
     * 
     * @param message
     * @param errors
     * @param status
     * @param data
     */
    public ChannelResult(Object errors, Data data, String message, String status) {
        super();
        this.errors = errors;
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
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
