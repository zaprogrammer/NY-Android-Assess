package com.elshaabany.nyTimes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.elshaabany.nyTimes.R;
import com.elshaabany.nyTimes.models.Result;
import com.elshaabany.nyTimes.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.article_date)
    TextView mArticleDate;
    @BindView(R.id.article_title)
    TextView mArticleTitle;
    @BindView(R.id.article_description)
    TextView mArticleDescription;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.notMVP)
    TextView mNotMVP;

    private Result articleDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        articleDetails = getIntent().getExtras().getParcelable(Constants.EVENT_EXTRA);
        setViewData(articleDetails);
    }

    private void setViewData(Result articleDetails) {
        Picasso.with(this).load(articleDetails.media().get(0).mediaMetadata().get(2).url())
                .error(R.drawable.placeholder_nomoon)
                .placeholder(R.drawable.placeholder_nomoon)
                .into(mImage);

        mArticleDate.setText(articleDetails.publishedDate());
        mArticleTitle.setText(articleDetails.title());
        mArticleDescription.setText(articleDetails.jsonMemberAbstract());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_share:
                shareTheDetail(articleDetails);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareTheDetail(Result articleDetails) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, articleDetails.jsonMemberAbstract());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

}
