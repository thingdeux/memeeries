package josh.land.meemeries.MemeBrowser.MemeFinder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import josh.land.meemeries.MemeBrowser.API.Appery;
import josh.land.meemeries.MemeBrowser.API.BackendLess;
import josh.land.meemeries.MemeBrowser.API.FireBaseAPI;
import josh.land.meemeries.MemeBrowser.API.models.Meme;
import josh.land.meemeries.MemeBrowser.MemeBrowser.dialogs.UsernameEntryDialog;
import josh.land.meemeries.MemeBrowser.MemeFinder.models.ImgurGallery;
import josh.land.meemeries.MemeBrowser.MemeFinder.singletons.ImgurDataManager;
import josh.land.meemeries.MemeBrowser.Utils.SharedPrefManager;
import josh.land.meemeries.R;

/*
    Very Simply Activity, view the meme image expanded and add to server.
 */

public class MemeViewerActivity extends AppCompatActivity {
    public static String IMGUR_IMAGE = "josh.land.meemeries.MemeViewerActivity";
    private SharedPrefManager.ApiType currentlySelectedAPI;
    private ImgurGallery selectedImage;
    private Button sendToServerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentlySelectedAPI = SharedPrefManager.getApiType(this);
        setContentView(R.layout.activity_meme_viewer);
        ImageView imageView = (ImageView) findViewById(R.id.imgur_image);
        TextView textView = (TextView) findViewById(R.id.imgur_title);
        Button cancelButton = (Button) findViewById(R.id.cancel_action);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        sendToServerButton = (Button) findViewById(R.id.send_to_server);

        int imagePosition = getIntent().getIntExtra(IMGUR_IMAGE, -1);
        if (imagePosition >= 0) {
            this.selectedImage = ImgurDataManager.getInstance().getImgurImages().get(imagePosition);
            if (this.selectedImage != null) {
                Picasso.with(this)
                        .load(this.selectedImage.getLink())
                        .noFade()
                        .noPlaceholder()
                        .into(imageView);

                if (this.selectedImage.getTitle() != null && textView != null) {
                    textView.setText(this.selectedImage.getTitle());
                }
                bindSendToServer(sendToServerButton);
            } else {
                // Would normally display some error here, but quick and dirty demo - toast and dismiss
                Toast.makeText(MemeViewerActivity.this, "Error Opening Image", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            if (cancelButton != null) { cancelButton.setVisibility(View.INVISIBLE); }
            sendToServerButton.setVisibility(View.INVISIBLE);

            if (ImgurDataManager.getInstance().getSelectedImage() != null) {
                // View X's profile
                // TODO : If Time - allow view profile
                Picasso.with(this)
                        .load(ImgurDataManager.getInstance().getSelectedImage().getLink())
                        .noFade()
                        .noPlaceholder()
                        .into(imageView);

                if (ImgurDataManager.getInstance().getSelectedImage().getTitle() != null && textView != null) {
                    textView.setText(ImgurDataManager.getInstance().getSelectedImage().getTitle());
                }

            } else {
                Toast.makeText(MemeViewerActivity.this, "Error Opening Image", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    public static Intent NewApiMemeView(ImgurGallery gallery, Context context) {
        ImgurDataManager.getInstance().setSelectedImage(gallery);
        Intent intent = new Intent(context, MemeViewerActivity.class);
        intent.putExtra(MemeViewerActivity.IMGUR_IMAGE, -1);
        return intent;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ImgurDataManager.getInstance().setSelectedImage(null);
        ImgurDataManager.getInstance().setCurrentlySelectedImage(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImgurDataManager.getInstance().setSelectedImage(null);
        ImgurDataManager.getInstance().setCurrentlySelectedImage(null);
    }

    private void bindSendToServer(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
                Toast.makeText(MemeViewerActivity.this, "Sent to Server", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void sendToServer() {
        if (SharedPrefManager.getUsername(this) != null && !SharedPrefManager.getUsername(this).isEmpty()) {
            Meme newMeme = new Meme();
            newMeme.setTitle(this.selectedImage.getTitle());
            newMeme.setImageUrl(this.selectedImage.getLink());
            newMeme.setPostDate(System.currentTimeMillis());
            newMeme.setPostedBy(SharedPrefManager.getUsername(this));

            switch (this.currentlySelectedAPI) {
                case ApperyIO:
                    Appery.createMeme(newMeme, null);
                    break;
                case Backendless:
                    BackendLess.createMeme(newMeme);
                default:
                    FireBaseAPI.getInstance().addMeme(newMeme);
                    break;
            }
        } else {
            Toast.makeText(MemeViewerActivity.this, "Username Missing or Empty", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
