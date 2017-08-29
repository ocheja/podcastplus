
package com.betazoo.podcast.model.Auth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Errors implements Parcelable
{

    @SerializedName("mobile")
    @Expose
    private List<String> mobile = null;
    public final static Creator<Errors> CREATOR = new Creator<Errors>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Errors createFromParcel(Parcel in) {
            Errors instance = new Errors();
            in.readList(instance.mobile, (String.class.getClassLoader()));
            return instance;
        }

        public Errors[] newArray(int size) {
            return (new Errors[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Errors() {
    }

    /**
     * 
     * @param mobile
     */
    public Errors(List<String> mobile) {
        super();
        this.mobile = mobile;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mobile);
    }

    public int describeContents() {
        return  0;
    }

}
