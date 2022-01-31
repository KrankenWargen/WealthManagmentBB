package com.bbconsult.wealthmanagment.ui.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbconsult.wealthmanagment.databinding.FragmentReportsBinding;
import com.bbconsult.wealthmanagment.ui.reports.adapters.ReportsAdapter;

public class ReportsFragment extends Fragment {

    private ReportsViewModel reportsViewModel;
    private FragmentReportsBinding binding;
    private ReportsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportsViewModel =
                new ViewModelProvider(this).get(ReportsViewModel.class);

        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView reportsRecyclerView = binding.reportsRecyclerView;

        adapter = new ReportsAdapter(getContext(), reportsViewModel.packageDirectories, getChildFragmentManager(), "parent");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        reportsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        reportsRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}