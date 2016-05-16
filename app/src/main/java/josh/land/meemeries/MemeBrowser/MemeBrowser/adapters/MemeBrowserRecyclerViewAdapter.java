package josh.land.meemeries.MemeBrowser.MemeBrowser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.MemeBrowser.models.Meme;
import josh.land.meemeries.R;

public class MemeBrowserRecyclerViewAdapter extends RecyclerView.Adapter<MemeBrowserRecyclerViewAdapter.ViewHolder> {
        private List<Meme> memes = new ArrayList<Meme>();

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView memeTitle;
            public TextView memeMetaData;
            public ImageView memeImage;

            public ViewHolder(View v) {
                super(v);
                view = v;
                memeTitle = (TextView) v.findViewById(R.id.meme_title);
                memeMetaData = (TextView) v.findViewById(R.id.meme_metadata);
                memeImage = (ImageView) v.findViewById(R.id.meme_image);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MemeBrowserRecyclerViewAdapter(final List<Meme> availableMemes) {
            memes = availableMemes;
        }

        public void setReceivedMemes(final List<Meme> receivedMemes) {
            if (receivedMemes != null && receivedMemes.size() > 0) {
                this.memes = receivedMemes;
                this.notifyDataSetChanged();
            }
        }

        @Override
        public MemeBrowserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_entry, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Meme meme = memes.get(position);
            if (meme.getTitle() != null && !meme.getTitle().isEmpty()) {
                holder.memeTitle.setVisibility(View.VISIBLE);
                holder.memeTitle.setText(meme.getTitle());
            } else {
                holder.memeTitle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return memes.size();
        }
}
