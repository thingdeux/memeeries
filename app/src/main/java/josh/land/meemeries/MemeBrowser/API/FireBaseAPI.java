package josh.land.meemeries.MemeBrowser.API;

import android.content.Context;

import com.firebase.client.Firebase;

import josh.land.meemeries.MemeBrowser.API.models.Meme;
import josh.land.meemeries.R;

/**
 * Class for handling interaction with the FireBase DB Engine.
 *
 * Created by Josh on 5/15/16.
 */
public class FireBaseAPI {
    public Firebase firebaseRoot;

    private static final FireBaseAPI instance = new FireBaseAPI();
    public static FireBaseAPI getInstance() {
        return instance;
    }
    private String childMeme;

    public void initFireBaseConnection(Context context) {
        this.firebaseRoot = new Firebase(context.getString(R.string.firebaseroot));
        this.childMeme = context.getString(R.string.firebase_memes_child);
    }

    private FireBaseAPI() {}

    // TODO : Add callback for added
    public void addMeme(Meme meme) {
        if (meme != null && meme.getTitle() != null && meme.getPostedBy() != null && meme.getImageUrl() != null) {
            if (!meme.getPostedBy().isEmpty()) {
                firebaseRoot.child(childMeme).push().setValue(meme);
            }
        }
    }
}
