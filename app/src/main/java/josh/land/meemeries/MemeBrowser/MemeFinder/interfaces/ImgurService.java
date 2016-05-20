package josh.land.meemeries.MemeBrowser.MemeFinder.interfaces;

import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGalleryWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Retro fit interface declaration for Imgur
 *
 * Created by Josh on 5/15/16.
 */
public interface ImgurService {

    @GET("gallery/hot/viral/{page}.json")
    Call<ImgurGalleryWrapper> listViralGalleries(@Path("page") String pageNumber, @Header("Authorization") String clientId);

    @GET("g/memes/{page}.json")
    Call<ImgurGalleryWrapper> listMemeGalleries(@Path("page") String pageNumber, @Header("Authorization") String clientId);

}
