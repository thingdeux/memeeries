package josh.land.meemeries.MemeBrowser.MemeBrowser;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.API.Appery;
import josh.land.meemeries.MemeBrowser.API.BackendLess;
import josh.land.meemeries.MemeBrowser.API.FireBaseAPI;
import josh.land.meemeries.MemeBrowser.API.interfaces.IBackendLess;
import josh.land.meemeries.MemeBrowser.MemeBrowser.adapters.MemeBrowserRecyclerViewAdapter;
import josh.land.meemeries.MemeBrowser.API.models.Meme;
import josh.land.meemeries.MemeBrowser.MemeBrowser.events.ApiChoiceModified;
import josh.land.meemeries.MemeBrowser.Utils.SharedPrefManager;
import josh.land.meemeries.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainBrowserFragment extends Fragment {
    private SharedPrefManager.ApiType currentlySelectedAPI;
    private RecyclerView recyclerView;
    private MemeBrowserRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Meme> receivedMemes = new ArrayList();

    public MainBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_main_browser_with_pull, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerViewAdapter = new MemeBrowserRecyclerViewAdapter(receivedMemes, this.getContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        currentlySelectedAPI = SharedPrefManager.getApiType(this.getContext());
        this.loadData();
    }

    private void loadData() {
        switch (currentlySelectedAPI) {
            case ApperyIO:
                this.getApeeryMemes();
                break;
            case Backendless:
                this.getBackendlessMemes();
                break;
            default:
                this.bindToAllMemeEvents();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void getBackendlessMemes() {
        BackendLess.getAllMemes(new IBackendLess() {
            @Override
            public void onSuccess(List<Meme> memes) {
                if (memes != null) {
                    receivedMemes.addAll(memes);
                }
                recyclerViewAdapter.setReceivedMemes(receivedMemes);
            }

            @Override
            public void onFailure() {
                // Intentionally empty
            }
        });
    }

    private void getApeeryMemes() {
        Appery.getAllMemes(this.getContext(), new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
                receivedMemes.clear();
                if (response.body() != null) {
                    receivedMemes.addAll(response.body());
                }
                recyclerViewAdapter.setReceivedMemes(receivedMemes);
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {
            }
        });
    }

    private void bindToAllMemeEvents() {
        // Anytime something is "pushed" to memes - this will be called.
        FireBaseAPI.getInstance().firebaseRoot.child(this.getString(R.string.firebase_memes_child))
                .orderByChild("postDate")
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Registering a listener to memes - when something changes on the server
                // This will be called and provide the latest json blob of Meme Objects.
                receivedMemes.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Meme meme = postSnapshot.getValue(Meme.class);
                    receivedMemes.add(meme);
                }
                // TODO : Take note of the last received meme and only notify recycler from that moment on.
                recyclerViewAdapter.setReceivedMemes(receivedMemes);
            }

            @Override public void onCancelled(FirebaseError error) {
                Log.e("ERROR", "Connection Error " + error);
            }
        });
    }

    @Subscribe
    public void onEvent(ApiChoiceModified event) {
        this.receivedMemes.clear();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            // Let the fragment handle the click
            this.loadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
