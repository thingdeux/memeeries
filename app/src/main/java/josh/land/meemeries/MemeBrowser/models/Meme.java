package josh.land.meemeries.MemeBrowser.models;

public class Meme {
    public static String FireBaseMemesRoot = "memes";

    private double id;
    private String title;
    private String imageUrl;
    private MemeUser postedBy;
    private String postDate;

    public Meme() {
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MemeUser getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(MemeUser postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /*
      {
                "id": 1
                "title": "My favorite meme",
                "image_url": "http://imgur.com/13j3j3",
                "comment": "This is great!",
                "posted_by": {
                    "id": 1234,
                    "name": "joshjosherson"
                },
                "post_date": "2015-08-05T08:40:51.620Z",
            },
     */
}
