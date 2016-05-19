package josh.land.meemeries.MemeBrowser.API;

import android.util.Log;

import java.util.List;

import josh.land.meemeries.MemeBrowser.API.interfaces.ApperyService;
import josh.land.meemeries.MemeBrowser.API.models.Meme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Handler for Appery.IO
 * This service handles like a traditional API so there will mostly be REST API calls defined here
*
 * Created by Josh on 5/19/16.
 */
public abstract class Appery {

    // Boolean returned is successful or not.
    public static void createMeme(Meme meme, final Callback<Meme> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.appery.io/rest/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApperyService service = retrofit.create(ApperyService.class);

        Call<Meme> memeSubmission = service.postMeme(meme);

        memeSubmission.enqueue(new Callback<Meme>() {
            @Override
            public void onResponse(Call<Meme> meme, Response<Meme> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(meme, response);
                } else {
                    Log.e("ApperyAPI", "Error retrieving from Appery.io");
                }
            }

            @Override
            public void onFailure(Call<Meme> call, Throwable t) {
                Log.e("ApperyAPI", "Error retrieving Appery.io: " + t);
                callback.onFailure(call, t);
            }
        });
    }

    public static void getAllMemes(final Callback<List<Meme>> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.appery.io/rest/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApperyService service = retrofit.create(ApperyService.class);

        Call<List<Meme>> allMemes = service.listAllMemes();

        allMemes.enqueue(new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> memes, Response<List<Meme>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(memes, response);
                } else {
                    Log.e("ApperyAPI", "Error retrieving from Appery.io");
                }
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {
                Log.e("ApperyAPI", "Error retrieving Appery.io: " + t);
                callback.onFailure(call, t);
            }
        });
    }

}
