package josh.land.meemeries.MemeBrowser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import josh.land.meemeries.MemeBrowser.models.Meme;
import josh.land.meemeries.R;

public class MemeBrowserRecyclerViewAdapter extends RecyclerView.Adapter<MemeBrowserRecyclerViewAdapter.ViewHolder> {
        private List<Meme> memes = new ArrayList<Meme>();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public View view;
            public TextView memeTitle;
            public TextView memeMetaData;
            public ImageView memeImage;

            public ViewHolder(View v) {
                super(v);
                view = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MemeBrowserRecyclerViewAdapter(final List<Meme> availableMemes) {
            memes = availableMemes;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MemeBrowserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_entry, parent, false);
            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
//            holder.mTextView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return memes.size();
        }
}
