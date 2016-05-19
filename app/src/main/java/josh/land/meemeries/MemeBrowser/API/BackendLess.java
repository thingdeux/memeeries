package josh.land.meemeries.MemeBrowser.API;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import josh.land.meemeries.MemeBrowser.API.interfaces.IBackendLess;
import josh.land.meemeries.MemeBrowser.API.models.Meme;

public abstract class BackendLess {
    public static void getAllMemes(final IBackendLess callback) {
        Backendless.Persistence.of(Meme.class).find(new AsyncCallback<BackendlessCollection<Meme>>() {
            @Override
            public void handleResponse(BackendlessCollection<Meme> response) {
                if (response.getData() != null && callback != null) {
                    callback.onSuccess(response.getData());
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Backendless", "Error Receiving Backendless Response");
            }
        });
    }

    public static void createMeme(Meme meme) {
        Backendless.Persistence.save( meme, new AsyncCallback<Meme>() {
            public void handleResponse( Meme response ) {
                Log.i("Backendless", "Meme Saved");
            }

            public void handleFault( BackendlessFault fault ) {
                Log.e("Backendless", "Error Saving Backendless object to server");
            }
        });
    }

}
