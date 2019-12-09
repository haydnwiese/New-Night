package com.example.nightclubpicker.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.StringRes;

import com.example.nightclubpicker.MainActivity;
import com.example.nightclubpicker.R;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setIcon(R.color.transparent);

        if (!(this instanceof MainActivity)) {
            setAdditionalActionBarProperties();
        }
    }

    protected void setAdditionalActionBarProperties() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        onNavigateUp();
        super.onBackPressed();
    }

    @Override
    public void setTitle(@StringRes int title) {
        getActionBar().setTitle(title);
    }

    public void setTitle(String title) {
        getActionBar().setTitle(title);
    }
}
