package com.example.nightclubpicker.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.StringRes;

import com.example.nightclubpicker.MainActivity;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActionBar().setDisplayUseLogoEnabled(false);

        if (!(this instanceof MainActivity)) {
            setAdditionalActionBarProperties();
        }
    }

    protected void setAdditionalActionBarProperties() {
        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActionBar().setDisplayShowHomeEnabled(true);
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
        this.getActionBar().setTitle(title);
    }
}
