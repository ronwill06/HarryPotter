package com.rondalewilliams.harrypotter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HarryPotterFragment extends Fragment {
    private  static final String TAG  = "HarryPotterFragment";

    private RecyclerView mRecyclerView;
    private HarryPotterViewModel mViewModel;

     public static HarryPotterFragment newInstance() {
        Bundle args = new Bundle();
        HarryPotterFragment fragment = new HarryPotterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new HarryPotterViewModel(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_harry_potter, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.harry_potter_recyclerview);
        mViewModel.setRecyclerView(mRecyclerView);
        mViewModel.setUpAdapter();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
