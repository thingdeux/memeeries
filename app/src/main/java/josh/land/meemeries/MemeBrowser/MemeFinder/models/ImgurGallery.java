package josh.land.meemeries.MemeBrowser.MemeFinder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.API.models.Meme;

public class ImgurGallery {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("datetime")
    @Expose
    public Integer datetime;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("animated")
    @Expose
    public Boolean animated;
    @SerializedName("views")
    @Expose
    public Integer views;
    @SerializedName("nsfw")
    @Expose
    public Boolean nsfw;
    @SerializedName("section")
    @Expose
    public String section;
    @SerializedName("in_gallery")
    @Expose
    public Boolean inGallery;
    @SerializedName("topic")
    @Expose
    public String topic;
    @SerializedName("topic_id")
    @Expose
    public Integer topicId;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("comment_count")
    @Expose
    public Integer commentCount;
    @SerializedName("ups")
    @Expose
    public Integer ups;
    @SerializedName("downs")
    @Expose
    public Integer downs;
    @SerializedName("points")
    @Expose
    public Integer points;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("is_album")
    @Expose
    public Boolean isAlbum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getDatetime() {
        return datetime;
    }

    public void setDatetime(Integer datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAnimated() {
        return animated;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean getInGallery() {
        return inGallery;
    }

    public void setInGallery(Boolean inGallery) {
        this.inGallery = inGallery;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getAlbum() {
        return isAlbum;
    }

    public void setAlbum(Boolean album) {
        isAlbum = album;
    }

    public static List<ImgurGallery> stripGalleriesAndNSFW(final List<ImgurGallery> images) {
        if (images != null) {
            ArrayList<ImgurGallery> purgedList = new ArrayList<>();
            for (ImgurGallery image: images) {
                // Simply bail if we can't determine if the object is in a gallery or NSFW
                if (image != null && image.getInGallery() != null && image.getNsfw() != null) {
                    if (!image.isAlbum && !image.getNsfw()) {
                        purgedList.add(image);
                    }
                }
            }
            return purgedList;
        }
        return null;
    }

    public static String getImageThumbnailUrlFromUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.indexOf(".") > 0 && !imageUrl.endsWith("gif")) {
            String extension = imageUrl.substring(imageUrl.lastIndexOf("."), imageUrl.length());
            return imageUrl.substring(0, imageUrl.lastIndexOf(".")) + "m" + extension;
        } else {
            return imageUrl;
        }
    }

    public static ImgurGallery memeToGallery(Meme meme) {
        ImgurGallery newGallery = new ImgurGallery();
        if (meme.getTitle() != null && meme.getImageUrl() != null) {
            newGallery.setTitle(meme.getTitle());
            newGallery.setLink(meme.getImageUrl());
            return newGallery;
        }
        return null;
    }
}
