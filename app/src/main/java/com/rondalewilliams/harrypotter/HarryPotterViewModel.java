package com.rondalewilliams.harrypotter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HarryPotterViewModel {

    RecyclerView mRecyclerView;
    List<HarryPotterItem> mItems;
    Context mContext;


    public HarryPotterViewModel(Context mContext) {
        this.mContext = mContext;
        new HarryPotterTask().execute();
    }

    public List<HarryPotterItem> getItems() {
        return this.mItems;
    }

    public void setItems(List<HarryPotterItem> items) {
        this.mItems = items;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public Context getContext() {
        return mContext;
    }

//    public View createView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        View view =  inflater.inflate(R.layout.fragment_harry_potter, container, attachToRoot);
//        mRecyclerView = (RecyclerView)view.findViewById(R.id.harry_potter_recyclerview);
//        setUpAdapter();
//        return view;
//    }

    public void setUpAdapter() {
        mRecyclerView.setAdapter(new HarryPotterAdapter(mItems));
    }

    private class HarryPotterTask extends AsyncTask<Void, Void, List<HarryPotterItem>> {

        @Override
        protected List<HarryPotterItem> doInBackground(Void... params) {

            try {
                return HarryPotterItemsManager.sharedInstance().getHarryPotterItems();

            } catch (JSONException je) {
                je.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<HarryPotterItem> items) {
            super.onPostExecute(items);
            mItems = items;
            setUpAdapter();
        }
    }

    private class HarryPotterViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mAuthorTextView;

        public HarryPotterViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView)itemView.findViewById(R.id.harry_potter_image);
            mTitleTextView = (TextView)itemView.findViewById(R.id.title_text_view);
            mAuthorTextView = (TextView)itemView.findViewById(R.id.author_text_view);
        }

        public void bindHarryPotterItem(HarryPotterItem item) {
            mTitleTextView.setText(item.getTitle());
            mAuthorTextView.setText(item.getAuthor());
            if (item.getImageUrl() == null) {
                Picasso.with(getContext()).load("").into(mImageView);
            }else {
                Picasso.with(getContext()).load(item.getImageUrl()).into(mImageView);
            }
        }
    }

    public class HarryPotterAdapter extends RecyclerView.Adapter<HarryPotterViewHolder> {

        private List<HarryPotterItem> items = new ArrayList<>();

        public HarryPotterAdapter(List<HarryPotterItem> items) {
            this.items = items;
        }

        @Override
        public HarryPotterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.item_list_harry_potter, parent, false);
            return new HarryPotterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HarryPotterViewHolder holder, int position) {
            HarryPotterItem item = items.get(position);
            holder.bindHarryPotterItem(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
