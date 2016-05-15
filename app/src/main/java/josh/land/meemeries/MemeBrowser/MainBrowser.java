package josh.land.meemeries.MemeBrowser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import josh.land.meemeries.R;
import josh.land.meemeries.MemeBrowser.models.Meme;
import josh.land.meemeries.MemeBrowser.models.MemeUser;

public class MainBrowser extends AppCompatActivity {

    Firebase firebaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate the Firebase connection and objects
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseRoot = new Firebase("https://crackling-inferno-3202.firebaseIO.com/memes/");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMeme();
                    Snackbar.make(view, "Meme Uploaded", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    private void addMeme() {
        Meme newMeme = new Meme();
        newMeme.setTitle("My Fav. List");
        newMeme.setPostedBy(new MemeUser("joshjosherson"));
        newMeme.setImageUrl("http://google.com/");

        if (newMeme.getPostedBy() != null && newMeme.getPostedBy().getName() != null &&
                !newMeme.getPostedBy().getName().isEmpty()) {
            // Set Value will overwrite whatever exists on this node.
//            firebaseRoot.child()
//            .setValue(newMeme);
            firebaseRoot.push().setValue(newMeme);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
