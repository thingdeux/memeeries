package josh.land.meemeries.MemeBrowser.MemeFinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.adapters.MemeFinderRecyclerAdapter;
import josh.land.meemeries.MemeBrowser.MemeFinder.interfaces.ImgurService;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGalleryWrapper;
import josh.land.meemeries.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MemeFinderFragment extends Fragment {
    private RecyclerView recyclerView;
    private MemeFinderRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meme_finder_with_pull, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.meme_main_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        recyclerAdapter = new MemeFinderRecyclerAdapter(this.getContext());
        recyclerView.setAdapter(recyclerAdapter);

        getLatestImgurGalleries();

        return v;
    }

    private void getLatestImgurGalleries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImgurService service = retrofit.create(ImgurService.class);

        Call<ImgurGalleryWrapper> images = service.listViralGalleries("0");
        images.enqueue(new Callback<ImgurGalleryWrapper>() {
            @Override
            public void onResponse(Call<ImgurGalleryWrapper> imgurWrapper, Response<ImgurGalleryWrapper> response) {
                if (response.isSuccessful()) {
                    Log.i("ImgurAPI", "Received Imgur Gallery");
                    refreshAdapter(
                        ImgurGallery.stripGalleriesAndNSFW(response.body().getData())
                    );
                } else {
                    Log.e("ImgurAPI", "Error retrieving Imgur");
                }
            }

            @Override
            public void onFailure(Call<ImgurGalleryWrapper> call, Throwable t) {
                Log.e("ImgurAPI", "Error retrieving Imgur: " + t);
            }
        });
    }



    private void refreshAdapter(List<ImgurGallery> images) {
        if (images != null) {
            recyclerAdapter.setImgurGalleries(images);
        }
    }
}
