package com.elshaabany.nyTimes.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.elshaabany.nyTimes.MyApplication;
import com.elshaabany.nyTimes.R;
import com.elshaabany.nyTimes.adapters.ArticleAdapter;
import com.elshaabany.nyTimes.listener.OnRecyclerViewClickListener;
import com.elshaabany.nyTimes.models.Result;
import com.elshaabany.nyTimes.presenters.ArticlePresenter;
import com.elshaabany.nyTimes.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */

public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener, SearchView.OnQueryTextListener {

    @BindView(R.id.content_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.content_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    ArticleAdapter mArticleAdapter;
    ArticlePresenter mArticlePresenter;
    boolean isLoading = true;
    @Inject
    Retrofit retrofit;
    private List<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getNetComponent().inject(this);

        toolbar.setTitle(getString(R.string.app_name));
        //Add Support for Menu Icon
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        mRecyclerView.setHasFixedSize(true);
        showProgress();
        initializeAdapter();

        mArticlePresenter = new ArticlePresenter(this, retrofit);
        mArticlePresenter.loadPosts(false);
        initializeRefreshLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem actionSearch = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) actionSearch.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_last1:
                loadArticlesByDaysFilter(1);
                return true;
            case R.id.action_last7:
                loadArticlesByDaysFilter(7);
                return true;
            case R.id.action_last30:
                loadArticlesByDaysFilter(30);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeAdapter() {
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Result>(), this);
        mRecyclerView.setAdapter(mArticleAdapter);
    }

    public void initializeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mArticlePresenter.loadPosts(true);
            }
        });
    }

    /**
     * Show the progress loader to indicate data fetching from server
     */
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Hides the progress loader and showing the SwipeToRefresh control
     */
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Displays ErrorSnackbar at the bottom of the screen to indicate errors
     */
    public void displayErrorSnackbar() {
        hideProgress();
        final View.OnClickListener clickListener = new View.OnClickListener() {
            public void onClick(View v) {
                showProgress();
                mArticlePresenter.loadPosts(false);
            }
        };
        final View coordinatorLayoutView = findViewById(R.id.snackbar_position);
        Snackbar
                .make(coordinatorLayoutView, R.string.error_load_post_text, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_action_retry, clickListener)
                .show();
    }

    /**
     * @param articles
     */
    public void addPosts(List<Result> articles) {
        this.results = articles;
        mArticleAdapter.addAllData(articles);
        mArticleAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    /**
     * Refresh the articles by clearing current results using SwipeToRefresh
     *
     * @param articles Array of Results returned
     */
    public void refreshPosts(List<Result> articles) {
        mArticleAdapter.clearData();
        mArticleAdapter.addAllData(articles);
        mArticleAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override
    public void onRecyclerViewClick(Object clickedObject, View clickedImageView) {
        Result resultDetails = (Result) clickedObject;
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.EVENT_EXTRA, resultDetails);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, clickedImageView, getString(R.string.sharedElement));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Result> filteredResults = new ArrayList<>();

        for (Result article : this.results) {
            if (article.title().toLowerCase().contains(userInput) ||
                    article.jsonMemberAbstract().toLowerCase().contains(userInput))
                filteredResults.add(article);
        }

        mArticleAdapter.updateList(filteredResults);
        return true;
    }

    /**
     * Load articles API using days parameter
     *
     * @param days Days of articles: 1, 7, or 30
     */
    private void loadArticlesByDaysFilter(int days) {
        Constants.DEFAULT_LAST_DAYS_FILTER = days;
        showProgress();
        mArticlePresenter.loadPosts(true);
    }
}
