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

import josh.land.meemeries.MemeBrowser.adapters.MemeBrowserRecyclerViewAdapter;
import josh.land.meemeries.R;

public class MainBrowserFragment extends Fragment {
    Firebase myFirebaseRef;
    RecyclerView recyclerView;
    MemeBrowserRecyclerViewAdapter recyclerViewAdapter;


    public MainBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.myFirebaseRef = new Firebase("https://crackling-inferno-3202.firebaseIO.com/");
        this.connectToAllMemeEvents();
        View v = inflater.inflate(R.layout.fragment_main_browser_with_pull, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewAdapter = (MemeBrowserRecyclerViewAdapter) new MemeBrowserRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    private void connectToAllMemeEvents() {
        // Anytime something is "pushed" to memes - this will be called.
        myFirebaseRef.child("memes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Registering a listener to memes - when something changes probably
                // Update the adapter here.
                Log.i("onDataChange", "Receied Event: " + snapshot.getValue());
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override public void onCancelled(FirebaseError error) {
                Log.e("ERROR", "Connection Error " + error);
            }

        });
    }
}
