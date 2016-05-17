package josh.land.meemeries.MemeBrowser.API.models;

public class Meme implements Comparable<Meme> {
    private Double id;
    private String title;
    private String imageUrl;
    private String postedBy;
    /*
         The Memes node is indexed on postedBy - so that querying memes by user is fast.
         These names will all be set to lowercase in the DB.
            "memes": {
                ".indexOn": ["postedBy"]
            }
         */

    private long postDate;

    public Meme() {
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getPostDate() {
        return postDate;
    }

    public void setPostDate(long postDate) {
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

    @Override
    public int compareTo(Meme other) {
        return Long.compare(this.postDate, other.postDate);
    }
}
