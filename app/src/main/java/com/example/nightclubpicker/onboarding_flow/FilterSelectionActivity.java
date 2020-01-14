package com.example.nightclubpicker.onboarding_flow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.views.SubHeaderListItemWrapperView;
import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;
import com.example.nightclubpicker.places.NearbyPlacesListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterSelectionActivity extends BaseActivity {

    @BindView(R.id.placeTypeHeader)
    SubHeaderListItemWrapperView placeTypeHeaderView;
    @BindView(R.id.musicTypeHeader)
    SubHeaderListItemWrapperView musicTypeHeaderView;
    @BindView(R.id.musicTypeFilterGroup)
    RadioGroup musicTypeRadioGroup;
    @BindView(R.id.distanceHeader)
    SubHeaderListItemWrapperView distanceHeaderView;
    @BindView(R.id.distanceValue)
    TextView distanceValueView;
    @BindView(R.id.distanceSlider)
    SeekBar distanceSlider;
    @BindView(R.id.venueSizeHeader)
    SubHeaderListItemWrapperView venueSizeHeaderView;
    @BindView(R.id.venueSizeFilterGroup)
    RadioGroup venueSizeRadioGroup;
    @BindView(R.id.dressCodeHeader)
    SubHeaderListItemWrapperView dressCodeHeaderView;
    @BindView(R.id.dressCodeFilterGroup)
    RadioGroup dressCodeRadioGroup;

    public static final String BUNDLE_LAT = "bundleLatitude";
    public static final String BUNDLE_LNG = "bundleLongitude";

    private Location location;
    private LocationManager locationManager;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_selection);
        setTitle(R.string.select_filters);
        ButterKnife.bind(this);

        distanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distanceValueView.setText(getString(R.string.distance_from_user, String.valueOf(i)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateSectionHeaders();
        createButtonGroups();
    }

    private void createButtonGroups() {
        List<String> groupOptions = MusicGenre.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, musicTypeRadioGroup);
        groupOptions = VenueSize.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, venueSizeRadioGroup);
        groupOptions = DressCode.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, dressCodeRadioGroup);
    }

    private void createIndividualRadioGroup(List<String> options, RadioGroup radioGroup) {
        ColorStateList colourStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, //disabled
                        new int[]{android.R.attr.state_checked} //enabled
                },
                new int[] {
                        getColor(R.color.darker_grey), //disabled
                        getColor(R.color.light_purple) //enabled
                }
        );

        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            radioButton.setButtonTintList(colourStateList);
            radioButton.setPadding(50, 40, 0, 40);
            radioGroup.addView(radioButton);
        }
        radioGroup.check(0);
    }

    private void generateSectionHeaders() {
        placeTypeHeaderView.setItems(new SubHeaderListItem(getString(R.string.place_type)));
        musicTypeHeaderView.setItems(new SubHeaderListItem(getString(R.string.music_type)));
        distanceHeaderView.setItems(new SubHeaderListItem(getString(R.string.distance)));
        venueSizeHeaderView.setItems(new SubHeaderListItem(getString(R.string.venue_size)));
        dressCodeHeaderView.setItems(new SubHeaderListItem(getString(R.string.dress_code)));
    }

    public void navigateToResults(View view) {
        Intent intent = new Intent(this, NearbyPlacesListActivity.class);
        startActivity(intent);
    }
}
