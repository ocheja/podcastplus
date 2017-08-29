
package com.betazoo.podcast.model.Channel;

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
    @SerializedName("atom_link")
    @Expose
    private String atomLink;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("ordering")
    @Expose
    private int ordering;
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Creator<Channel> CREATOR = new Creator<Channel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Channel createFromParcel(Parcel in) {
            Channel instance = new Channel();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.atomLink = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.views = ((int) in.readValue((int.class.getClassLoader())));
            instance.ordering = ((int) in.readValue((int.class.getClassLoader())));
            instance.categoryId = ((int) in.readValue((int.class.getClassLoader())));
            instance.userId = ((int) in.readValue((int.class.getClassLoader())));
            instance.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
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
     * @param updatedAt
     * @param id
     * @param title
     * @param createdAt
     * @param views
     * @param description
     * @param userId
     * @param categoryId
     * @param thumbnailUrl
     * @param ordering
     * @param language
     * @param atomLink
     */
    public Channel(int id, String title, String atomLink, String description, String language, String thumbnailUrl, int views, int ordering, int categoryId, int userId, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.title = title;
        this.atomLink = atomLink;
        this.description = description;
        this.language = language;
        this.thumbnailUrl = thumbnailUrl;
        this.views = views;
        this.ordering = ordering;
        this.categoryId = categoryId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getAtomLink() {
        return atomLink;
    }

    public void setAtomLink(String atomLink) {
        this.atomLink = atomLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(atomLink);
        dest.writeValue(description);
        dest.writeValue(language);
        dest.writeValue(thumbnailUrl);
        dest.writeValue(views);
        dest.writeValue(ordering);
        dest.writeValue(categoryId);
        dest.writeValue(userId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
