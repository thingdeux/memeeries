package josh.land.meemeries.MemeBrowser.MemeFinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.R;

public class MemeFinderRecyclerAdapter extends RecyclerView.Adapter<MemeFinderRecyclerAdapter.ViewHolder> {
        List<ImgurGallery> imgurGalleries = new ArrayList<ImgurGallery>();
        Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView memeTitle;
            public ImageView memeImage;

            public ViewHolder(View v) {
                super(v);
                view = v;
                memeTitle = (TextView) v.findViewById(R.id.imgur_title);
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

        @Override
        public MemeFinderRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imgur_entry, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImgurGallery imgurItem = this.imgurGalleries.get(position);
            if (imgurItem.getTitle() != null && !imgurItem.getTitle().isEmpty()) {
                holder.memeTitle.setText(imgurItem.getTitle());
                holder.memeTitle.setVisibility(View.VISIBLE);
            } else {
                holder.memeTitle.setVisibility(View.INVISIBLE);
            }

            if (imgurItem.getLink() != null && !imgurItem.getLink().isEmpty()) {
                Glide
                .with(this.mContext)
                .load(imgurItem.getLink())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.memeImage);
            }
        }

        @Override
        public int getItemCount() {
            return imgurGalleries.size();
        }
}


