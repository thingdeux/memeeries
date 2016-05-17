package josh.land.meemeries.MemeBrowser.MemeFinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs.UsernameEntryDialog;
import josh.land.meemeries.R;

public class MemeFinderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meme_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meme_finder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_set_username) {
            this.showEnterNameDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEnterNameDialog() {
        // Prompt for username if one hasn't been set.
        UsernameEntryDialog dialog = new UsernameEntryDialog();
        dialog.show(getSupportFragmentManager(), "Enter Username Fragment");
    }
}
