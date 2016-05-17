package josh.land.meemeries.MemeBrowser.MemeBrowser.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import josh.land.meemeries.MemeBrowser.API.models.Meme;
import josh.land.meemeries.MemeBrowser.MemeFinder.MemeViewerActivity;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.R;

public class MemeBrowserRecyclerViewAdapter extends RecyclerView.Adapter<MemeBrowserRecyclerViewAdapter.ViewHolder> {
        private List<Meme> memes = new ArrayList<>();
        private Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView memeTitle;
            public TextView memeMetaData;
            public ImageView memeImage;

            public ViewHolder(View v) {
                super(v);
                view = v;
                memeTitle = (TextView) v.findViewById(R.id.meme_title);
                memeMetaData = (TextView) v.findViewById(R.id.meme_postedby);
                memeImage = (ImageView) v.findViewById(R.id.meme_image);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MemeBrowserRecyclerViewAdapter(final List<Meme> availableMemes, Context context) {
            memes = availableMemes;
            this.mContext = context;
        }

        public void setReceivedMemes(final List<Meme> receivedMemes) {
            if (receivedMemes != null && receivedMemes.size() > 0) {
                this.memes = receivedMemes;
                Collections.reverse(this.memes);
                this.notifyDataSetChanged();
            }
        }

        @Override
        public MemeBrowserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_entry, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Meme meme = memes.get(position);
            final Context context = this.mContext;

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = MemeViewerActivity.NewApiMemeView(ImgurGallery.memeToGallery(meme), context);
                    context.startActivity(intent);
                }
            });

            if (meme.getTitle() != null && !meme.getTitle().isEmpty()) {
                holder.memeTitle.setVisibility(View.VISIBLE);
                holder.memeTitle.setText(meme.getTitle());
            } else {
                holder.memeTitle.setVisibility(View.INVISIBLE);
            }

            if (meme.getImageUrl() != null) {
                holder.memeImage.setVisibility(View.VISIBLE);
                Picasso.with(this.mContext)
                        .load(ImgurGallery.getImageThumbnailUrlFromUrl(meme.getImageUrl()))
                        .centerCrop()
                        .resize(160, 160)
                        .noFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.memeImage);
            } else {
                holder.memeImage.setVisibility(View.INVISIBLE);
            }

            if (meme.getPostedBy() != null && !meme.getPostedBy().isEmpty()) {
                holder.memeMetaData.setVisibility(View.VISIBLE);
                holder.memeMetaData.setEnabled(true);
                holder.memeMetaData.setText(meme.getPostedBy());
            } else {
                holder.memeMetaData.setText("Unknown");
                holder.memeMetaData.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return memes.size();
        }
}
