package com.tk.flashcheckbook.ui.slideshow;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tk.flashcheckbook.TransactionEditorActivity;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");



    }

    public LiveData<String> getText() {
        return mText;
    }
}