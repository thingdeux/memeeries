package josh.land.meemeries.MemeBrowser.MemeFinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.MemeFinderActivity;
import josh.land.meemeries.MemeBrowser.MemeFinder.MemeFinderFragment;
import josh.land.meemeries.MemeBrowser.MemeFinder.MemeViewerActivity;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.MemeBrowser.MemeFinder.singletons.ImgurDataManager;
import josh.land.meemeries.R;

public class MemeFinderRecyclerAdapter extends RecyclerView.Adapter<MemeFinderRecyclerAdapter.ViewHolder> {
        Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public ImageView memeImage;

            public ViewHolder(View v) {
                super(v);
                view = v;
                memeImage = (ImageView) v.findViewById(R.id.imgur_image);
            }
        }

        public MemeFinderRecyclerAdapter(Context context) {
            this.mContext = context;
        }

        public void setImgurGalleries(List<ImgurGallery> galleries) {
            ImgurDataManager.getInstance().setImgurImages(galleries);
            this.notifyDataSetChanged();
        }

        public void addImgurGalleries(List<ImgurGallery> galleries) {
            ImgurDataManager.getInstance().getImgurImages().addAll(galleries);
            this.notifyDataSetChanged();
        }

        @Override
        public MemeFinderRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imgur_entry, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImgurGallery imgurItem = ImgurDataManager.getInstance().getImgurImages().get(position);

            if (imgurItem.getLink() != null && !imgurItem.getLink().isEmpty()) {

                Picasso.with(this.mContext)
                        .load(ImgurGallery.getImageThumbnailUrlFromUrl(imgurItem.getLink()))
                        .centerCrop()
                        .resize(160, 160)
                        .noFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.memeImage);


                this.bindImage(holder.memeImage, this.mContext, position);
            } else {
                Log.i("API_ERROR", "Empty Image Url");
            }
        }

        private void bindImage(View v, final Context context, final int position) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MemeViewerActivity.class);
                    intent.putExtra(MemeViewerActivity.IMGUR_IMAGE, position);
                    // Set to be used w/ API add
                    ImgurDataManager.getInstance().setCurrentlySelectedImage(position);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return ImgurDataManager.getInstance().getImgurImages().size();
        }
}


