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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.MemeViewerActivity;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.R;

public class MemeFinderRecyclerAdapter extends RecyclerView.Adapter<MemeFinderRecyclerAdapter.ViewHolder> {
        List<ImgurGallery> imgurGalleries = new ArrayList<ImgurGallery>();
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
            this.imgurGalleries = galleries;
            this.notifyDataSetChanged();
        }

        public void addImgurGalleries(List<ImgurGallery> galleries) {
            this.imgurGalleries.addAll(galleries);
            this.notifyDataSetChanged();
        }

        @Override
        public MemeFinderRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imgur_entry, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImgurGallery imgurItem = this.imgurGalleries.get(position);

            if (imgurItem.getLink() != null && !imgurItem.getLink().isEmpty()) {

                Picasso.with(this.mContext)
                        .load(this.getImageThumbnailUrlFromUrl(imgurItem.getLink()))
                        .centerCrop()
                        .resize(160, 160)
                        .noFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.memeImage);


                this.bindImage(holder.memeImage, imgurItem);
            } else {
                Log.i("API_ERROR", "Empty Image Url");
            }
        }

        private void bindImage(View v, final ImgurGallery image) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("CLICKED", image.getTitle());
//                    Intent intent = new Intent(this, MemeViewerActivity.class);
//                    intent.putExtra(MemeViewerActivity.EXTRA_CONTACT, contact);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation(this, (View)ivProfile, "profile");
//
//                    startActivity(intent, options.toBundle());
                }
            });
        }

        private String getImageThumbnailUrlFromUrl(String imageUrl) {
            if (imageUrl != null && imageUrl.indexOf(".") > 0 && !imageUrl.endsWith("gif")) {
                String extension = imageUrl.substring(imageUrl.lastIndexOf("."), imageUrl.length());
                String newURL = imageUrl.substring(0, imageUrl.lastIndexOf(".")) + "m" + extension;
                return newURL;
            } else {
                return imageUrl;
            }

        }

        @Override
        public int getItemCount() {
            return imgurGalleries.size();
        }
}


