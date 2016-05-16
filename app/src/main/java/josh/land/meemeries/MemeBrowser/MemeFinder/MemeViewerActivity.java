package josh.land.meemeries.MemeBrowser.MemeFinder;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import josh.land.meemeries.R;

public class MemeViewerActivity extends AppCompatActivity {
    public static String IMGUR_IMAGE = "josh.land.meemeries.MemeViewerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meme_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
