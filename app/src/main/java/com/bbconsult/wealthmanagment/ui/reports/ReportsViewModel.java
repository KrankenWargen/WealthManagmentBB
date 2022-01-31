package com.bbconsult.wealthmanagment.ui.reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bbconsult.wealthmanagment.common.WebViewReader;
import com.bbconsult.wealthmanagment.domain.models.PackageDirectory;

import java.util.ArrayList;

public class ReportsViewModel extends ViewModel {

    ArrayList<PackageDirectory> packageDirectories;
    private final MutableLiveData<String> mText;

    public ReportsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");

        packageDirectories = WebViewReader.getInstance().GetPackages();

    }

    public LiveData<String> getText() {
        return mText;
    }
}