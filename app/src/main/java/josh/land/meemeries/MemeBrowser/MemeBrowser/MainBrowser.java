package josh.land.meemeries.MemeBrowser.MemeBrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs.ApiSelectionDialog;
import josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs.UsernameEntryDialog;
import josh.land.meemeries.MemeBrowser.MemeBrowser.events.ApiChoiceModified;
import josh.land.meemeries.MemeBrowser.MemeFinder.MemeFinderActivity;

import josh.land.meemeries.MemeBrowser.API.FireBaseAPI;
import josh.land.meemeries.MemeBrowser.Utils.SharedPrefManager;
import josh.land.meemeries.R;


public class MainBrowser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate the Firebase connection and objects
        // Will do this irrespective of whether or not the user has firebase selected just for ease.
        Firebase.setAndroidContext(this);
        FireBaseAPI.getInstance().initFireBaseConnection(this);

        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.browser_fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MemeFinderActivity.class);
                    startActivity(intent);
                }
            });
        }

        this.checkUsernameExists();
    }

    private void checkUsernameExists() {
        if (SharedPrefManager.getUsername(this) == null) {
            // Prompt for username if one hasn't been set.
            UsernameEntryDialog dialog = new UsernameEntryDialog();
            dialog.show(getSupportFragmentManager(), "Enter Username Fragment");
        }
    }

    private void showEnterNameDialog() {
        // Prompt for username if one hasn't been set.
        UsernameEntryDialog dialog = new UsernameEntryDialog();
        dialog.show(getSupportFragmentManager(), "Enter Username Fragment");
    }

    private void showChangeApiDialog() {
        // Allow the user to change the backend API
        ApiSelectionDialog dialog = new ApiSelectionDialog();
        dialog.show(getSupportFragmentManager(), "Change API Fragment");
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
        if (id == R.id.action_set_username) {
            this.showEnterNameDialog();
            // TODO : Handle username already exists error. Status code 400 for appery
            // Actually - no time ... don't deal with real user relationships... can't easily
            // Embed them in the main response with a 1-to-1
            return true;
        } else if (id == R.id.action_change_api) {
            this.showChangeApiDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(ApiChoiceModified event) {
        this.recreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
