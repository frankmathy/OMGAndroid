package example.com.omgandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.squareup.picasso.Picasso;

/**
 * Created by frank on 06.07.14.
 */
public class DetailActivity extends Activity {

    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    String mImageURL;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.img_cover);
        String coverID = this.getIntent().getExtras().getString("coverID");
        if(coverID.length() > 0) {
            mImageURL = IMAGE_URL_BASE + coverID + "-L.jpg";
            Picasso.with(this).load(mImageURL).placeholder(R.drawable.img_books_loading).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        }
        setShareIntent();
        return true;
    }


    private void setShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageURL);
        mShareActionProvider.setShareIntent(shareIntent);
    }
}
