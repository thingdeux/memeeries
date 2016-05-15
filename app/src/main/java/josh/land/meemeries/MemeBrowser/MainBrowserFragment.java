package josh.land.meemeries.MemeBrowser;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import josh.land.meemeries.MemeBrowser.adapters.MemeBrowserRecyclerViewAdapter;
import josh.land.meemeries.MemeBrowser.models.Meme;
import josh.land.meemeries.R;

public class MainBrowserFragment extends Fragment {
    private Firebase myFirebaseRef;
    private RecyclerView recyclerView;
    private MemeBrowserRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Meme> receivedMemes = new ArrayList();

    public MainBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFirebaseRef = new Firebase(this.getString(R.string.firebaseroot));

        this.bindToAllMemeEvents();
        View v = inflater.inflate(R.layout.fragment_main_browser_with_pull, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerViewAdapter = new MemeBrowserRecyclerViewAdapter(receivedMemes);
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    private void bindToAllMemeEvents() {
        // Anytime something is "pushed" to memes - this will be called.
        myFirebaseRef.child(this.getString(R.string.firebase_memes_child)).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Registering a listener to memes - when something changes on the server
                // This will be called and provide the latest json blob of Meme Objects.
                receivedMemes.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Meme meme = postSnapshot.getValue(Meme.class);
                    receivedMemes.add(meme);
                }
                recyclerViewAdapter.setReceivedMemes(receivedMemes);
            }

            @Override public void onCancelled(FirebaseError error) {
                Log.e("ERROR", "Connection Error " + error);
            }

        });
    }
}
