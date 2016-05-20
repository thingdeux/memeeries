package josh.land.meemeries.MemeBrowser.API;

import android.content.Context;
import android.util.Log;

import java.util.List;

import josh.land.meemeries.MemeBrowser.API.interfaces.ApperyService;
import josh.land.meemeries.MemeBrowser.API.models.ApperyUser;
import josh.land.meemeries.MemeBrowser.API.models.Meme;

import josh.land.meemeries.R;
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
    public static void createMeme(Meme meme, final Context context, final Callback<Meme> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.appery.io/rest/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApperyService service = retrofit.create(ApperyService.class);

        Call<Meme> memeSubmission = service.postMeme(meme, context.getString(R.string.appery_database_id));

        memeSubmission.enqueue(new Callback<Meme>() {
            @Override
            public void onResponse(Call<Meme> meme, Response<Meme> response) {
                // NOTE - WARNING: This does not return a full meme object ... appery returns a "light" object
                // Which is simply _id and _createdAt wrapping this in callback to allow for error
                // Catching and driving UI - not to receive objects after post.
                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onResponse(meme, response);
                    }
                } else {
                    Log.e("ApperyAPI", "Error retrieving from Appery.io");
                }
            }

            @Override
            public void onFailure(Call<Meme> call, Throwable t) {
                Log.e("ApperyAPI", "Error retrieving Appery.io: " + t);
                if (callback != null) {
                    callback.onFailure(call, t);
                }
            }
        });
    }

    public static void getAllMemes(final Context context, final Callback<List<Meme>> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.appery.io/rest/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApperyService service = retrofit.create(ApperyService.class);

        Call<List<Meme>> allMemes = service.listAllMemes(context.getString(R.string.appery_database_id));

        allMemes.enqueue(new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> memes, Response<List<Meme>> response) {
                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onResponse(memes, response);
                    }
                } else {
                    Log.e("ApperyAPI", "Error retrieving from Appery.io");
                }
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {
                Log.e("ApperyAPI", "Error retrieving Appery.io: " + t);
                if (callback != null) {
                    callback.onFailure(call, t);
                }
            }
        });
    }

    public static void createUser(ApperyUser user, final Context context, final Callback<ApperyUser> callback) {
        // relative URI - db/users/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.appery.io/rest/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApperyService service = retrofit.create(ApperyService.class);

        Call<ApperyUser> userReq = service.createUser(user, context.getString(R.string.appery_database_id));

        userReq.enqueue(new Callback<ApperyUser>() {
            @Override
            public void onResponse(Call<ApperyUser> user, Response<ApperyUser> response) {
                if (response.code() == 400) {
                    if (callback != null) {
                        callback.onResponse(user, null);
                    }
                }

                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onResponse(user, response);
                    }
                } else {
                    Log.e("ApperyAPI", "Error retrieving from Appery.io");
                }
            }

            @Override
            public void onFailure(Call<ApperyUser> call, Throwable t) {
                Log.e("ApperyAPI", "Error retrieving Appery.io: " + t);
                if (callback != null) {
                    callback.onFailure(call, t);
                }
            }
        });
    }

}
