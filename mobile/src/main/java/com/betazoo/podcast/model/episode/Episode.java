
package com.betazoo.podcast.model.episode;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable
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
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("publication_date")
    @Expose
    private String publicationDate;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("ordering")
    @Expose
    private int ordering;
    @SerializedName("channel_id")
    @Expose
    private int channelId;
    @SerializedName("channel")
    @Expose
    private Channel channel;
    @SerializedName("author")
    @Expose
    private String author;

    public final static Creator<Episode> CREATOR = new Creator<Episode>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Episode createFromParcel(Parcel in) {
            Episode instance = new Episode();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.link = ((String) in.readValue((String.class.getClassLoader())));
            instance.publicationDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.views = ((int) in.readValue((int.class.getClassLoader())));
            instance.duration = ((int) in.readValue((int.class.getClassLoader())));
            instance.ordering = ((int) in.readValue((int.class.getClassLoader())));
            instance.channelId = ((int) in.readValue((int.class.getClassLoader())));
            instance.channel = ((Channel) in.readValue((Channel.class.getClassLoader())));
            instance.author =  ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Episode[] newArray(int size) {
            return (new Episode[size]);
        }

    }
    ;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Episode() {
    }

    /**
     *
     * @param id
     * @param duration
     * @param title
     * @param channelId
     * @param views
     * @param description
     * @param link
     * @param ordering
     * @param channel
     * @param publicationDate
     */
    public Episode(int id, String title, String description, String link, String publicationDate, int views, int duration, int ordering, int channelId, Channel channel, String author) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.publicationDate = publicationDate;
        this.views = views;
        this.duration = duration;
        this.ordering = ordering;
        this.channelId = channelId;
        this.channel = channel;
        this.author = author;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(link);
        dest.writeValue(publicationDate);
        dest.writeValue(views);
        dest.writeValue(duration);
        dest.writeValue(ordering);
        dest.writeValue(channelId);
        dest.writeValue(channel);
        dest.writeValue(author);
    }

    public int describeContents() {
        return  0;
    }

}
