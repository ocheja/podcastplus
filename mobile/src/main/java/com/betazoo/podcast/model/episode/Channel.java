
package com.betazoo.podcast.model.episode;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channel implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("atom_link")
    @Expose
    private String atomLink;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    public final static Creator<Channel> CREATOR = new Creator<Channel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Channel createFromParcel(Parcel in) {
            Channel instance = new Channel();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.atomLink = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Channel[] newArray(int size) {
            return (new Channel[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Channel() {
    }

    /**
     * 
     * @param id
     * @param title
     * @param description
     * @param thumbnailUrl
     * @param atomLink
     */
    public Channel(int id, String title, String description, String atomLink, String thumbnailUrl) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.atomLink = atomLink;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAtomLink() {
        return atomLink;
    }

    public void setAtomLink(String atomLink) {
        this.atomLink = atomLink;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(atomLink);
        dest.writeValue(thumbnailUrl);
    }

    public int describeContents() {
        return  0;
    }

}
