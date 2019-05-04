package com.elshaabany.nyTimes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elshaabany.nyTimes.R;
import com.elshaabany.nyTimes.listener.OnHolderClickListener;
import com.elshaabany.nyTimes.listener.OnRecyclerViewClickListener;
import com.elshaabany.nyTimes.models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> implements OnHolderClickListener {
    private List<Result> results;
    private Context mContext;
    private OnRecyclerViewClickListener clickListener;

    public ArticleAdapter(Context context, List<Result> results, OnRecyclerViewClickListener clickListener) {
        this.mContext = context;
        this.results = results;
        this.clickListener = clickListener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {
        holder.currentResult = results.get(position);
        holder.holderPosition = position;
        holder.mTitle.setText(holder.currentResult.title());
        holder.mByLine.setText(holder.currentResult.byline());
        holder.mArticleDate.setText(holder.currentResult.publishedDate());
        Picasso.with(mContext).load(holder.currentResult.media().get(0).mediaMetadata().get(0).url())
                .error(R.drawable.placeholder_nomoon)
                .placeholder(R.drawable.placeholder_nomoon)
                .into(holder.mImage);
    }

    @Override
    public void onHolderClicked(int holderPosition, View clickedImage) {
        clickListener.onRecyclerViewClick(results.get(holderPosition), clickedImage);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setPosts(List<Result> posts) {
        this.results = posts;
    }

    public void clearData() {
        results.clear();
    }

    public void addAllData(List<Result> posts) {
        this.results.addAll(posts);
    }

    public void updateList(List<Result> newList) {
        results = new ArrayList<>();
        results.addAll(newList);
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        CircleImageView mImage;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.byLine)
        TextView mByLine;
        @BindView(R.id.article_date)
        TextView mArticleDate;
        //public View view;
        private Result currentResult;
        private int holderPosition;
        private OnHolderClickListener clickListener;

        public ArticleViewHolder(View view, OnHolderClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.article_card_view)
        public void onClick() {
            if (clickListener != null) {
                clickListener.onHolderClicked(holderPosition, mImage);
            }
        }
    }
}
