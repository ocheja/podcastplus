
package com.betazoo.podcast.model.Auth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_login")
    @Expose
    private Object lastLogin;
    @SerializedName("id")
    @Expose
    private int id;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            User instance = new User();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastLogin = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param id
     * @param lastLogin
     * @param name
     */
    public User(String name, Object lastLogin, int id) {
        super();
        this.name = name;
        this.lastLogin = lastLogin;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(lastLogin);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
