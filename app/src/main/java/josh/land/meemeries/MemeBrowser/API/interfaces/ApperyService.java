package josh.land.meemeries.MemeBrowser.API.interfaces;

import java.util.List;

import josh.land.meemeries.MemeBrowser.API.models.ApperyUser;
import josh.land.meemeries.MemeBrowser.API.models.Meme;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Appery.io retrofit interface declarations
 * Expected BaseURL: https://api.appery.io/rest/1/
 *
 *
 * Created by Josh on 5/19/16.
 */
public interface ApperyService {
    // TOOD: Need to pull the header out to envars

    @Headers({"X-Appery-Database-Id: 573ddb02e4b08a80ca8e4c16"})
    @GET("db/collections/memes/")
    Call<List<Meme>> listAllMemes();

    @Headers({"X-Appery-Database-Id: 573ddb02e4b08a80ca8e4c16"})
    @POST("db/collections/memes/")
    Call<Meme> postMeme(@Body Meme meme);

    @Headers({"X-Appery-Database-Id: 573ddb02e4b08a80ca8e4c16"})
    @POST("db/collections/memes/")
    Call<ApperyUser> createUser(@Body ApperyUser user);
}
