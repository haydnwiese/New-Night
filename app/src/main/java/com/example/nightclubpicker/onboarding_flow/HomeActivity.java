package com.example.nightclubpicker.onboarding_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.background)
    ImageView backgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        loadBackgroundImage();
    }

    private void loadBackgroundImage() {
        Picasso.get()
                .load(R.drawable.home_background)
                .fit()
                .centerCrop()
                .into(backgroundView);
    }

    @OnClick(R.id.startButton)
    public void navigateToFilters(View view) {
        Intent intent = new Intent(this, FilterSelectionActivity.class);
        startActivity(intent);
    }
}
