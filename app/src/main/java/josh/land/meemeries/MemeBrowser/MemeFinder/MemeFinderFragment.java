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

import josh.land.meemeries.MemeBrowser.MemeFinder.adapters.MemeFinderPaginateListener;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerAdapter = new MemeFinderRecyclerAdapter(this.getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnScrollListener(new MemeFinderPaginateListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getLatestImgurGalleries("" + (current_page - 1), true);
            }
        });

        getLatestImgurGalleries("0", false);

        return v;
    }

    private void getLatestImgurGalleries(String page, final boolean isPaginating) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImgurService service = retrofit.create(ImgurService.class);

        Call<ImgurGalleryWrapper> images = service.listMemeGalleries(page);
        images.enqueue(new Callback<ImgurGalleryWrapper>() {
            @Override
            public void onResponse(Call<ImgurGalleryWrapper> imgurWrapper, Response<ImgurGalleryWrapper> response) {
                if (response.isSuccessful()) {
                    refreshAdapter(ImgurGallery.stripGalleriesAndNSFW(response.body().getData()), isPaginating);
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

    private void refreshAdapter(List<ImgurGallery> images, boolean isPaginating) {
        if (images != null) {
            if (isPaginating) {
                recyclerAdapter.addImgurGalleries(images);
            } else {
                recyclerAdapter.setImgurGalleries(images);
            }
        }
    }
}
