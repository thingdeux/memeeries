package josh.land.meemeries.MemeBrowser.MemeFinder.singletons;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;

/**
 * Singleton
 *
 * Created by Josh on 5/16/16.
 */
public class ImgurDataManager {
    private List<ImgurGallery> imgurImages = new ArrayList<>();
    private Integer currentlySelectedImage = null;

    private static final ImgurDataManager instance = new ImgurDataManager();
    public static ImgurDataManager getInstance() {
        return instance;
    }
    private ImgurDataManager() {}

    public List<ImgurGallery> getImgurImages() {
        return imgurImages;
    }

    public void setImgurImages(List<ImgurGallery> imgurImages) {
        this.imgurImages = imgurImages;
    }

    public Integer getCurrentlySelectedImage() {
        return currentlySelectedImage;
    }

    public void setCurrentlySelectedImage(Integer currentlySelectedImage) {
        this.currentlySelectedImage = currentlySelectedImage;
    }
}
