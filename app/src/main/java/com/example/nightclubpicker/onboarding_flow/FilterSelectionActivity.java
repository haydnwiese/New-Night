package com.example.nightclubpicker.onboarding_flow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nightclubpicker.R;
import com.example.nightclubpicker.common.BaseActivity;
import com.example.nightclubpicker.common.ResourceSingleton;
import com.example.nightclubpicker.common.list_items.SubHeaderListItem;
import com.example.nightclubpicker.common.views.SubHeaderListItemWrapperView;
import com.example.nightclubpicker.nearby_places.NearbyPlacesActivity;
import com.example.nightclubpicker.onboarding_flow.models.DressCode;
import com.example.nightclubpicker.onboarding_flow.models.MusicGenre;
import com.example.nightclubpicker.onboarding_flow.models.PlaceType;
import com.example.nightclubpicker.onboarding_flow.models.VenueSize;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterSelectionActivity extends BaseActivity {

    @BindView(R.id.placeTypeHeader)
    SubHeaderListItemWrapperView placeTypeHeaderView;
    @BindView(R.id.placeTypeFilterGroup)
    RadioGroup placeTypeRadioGroup;
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
    @BindView(R.id.seeResultsButton)
    ImageView seeResultsImageButton;

    private static ColorStateList colourStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_checked}, //disabled
                    new int[]{android.R.attr.state_checked} //enabled
            },
            new int[] {
                    ResourceSingleton.getInstance().getColor(R.color.darker_grey), //disabled
                    ResourceSingleton.getInstance().getColor(R.color.light_purple) //enabled
            }
    );
    private static final String DRESS_CODE_GROUP = "dressCodeGroup";
    private static final String MUSIC_GENRE_GROUP = "musicGenreGroup";
    private static final String PLACE_TYPE_GROUP = "placeTypeGroup";
    private static final String VENUE_SIZE_GROUP = "venueSizeGroup";

    private int radius;
    private DressCode dressCode;
    private MusicGenre musicGenre;
    private PlaceType placeType;
    private VenueSize venueSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_selection);
        setTitle(R.string.select_filters);
        ButterKnife.bind(this);

        radius = distanceSlider.getProgress();
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
                radius = seekBar.getProgress();
            }
        });

        generateSectionHeaders();
        createButtonGroups();
    }

    private void createButtonGroups() {
        List<String> groupOptions = PlaceType.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, placeTypeRadioGroup, PLACE_TYPE_GROUP);
        groupOptions = MusicGenre.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, musicTypeRadioGroup, MUSIC_GENRE_GROUP);
        groupOptions = VenueSize.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, venueSizeRadioGroup, VENUE_SIZE_GROUP);
        groupOptions = DressCode.getAllDisplayStrings();
        createIndividualRadioGroup(groupOptions, dressCodeRadioGroup, DRESS_CODE_GROUP);
    }

    private void createIndividualRadioGroup(List<String> options, RadioGroup radioGroup, String groupId) {
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            radioButton.setButtonTintList(colourStateList);
            radioButton.setPadding(50, 40, 0, 40);
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(getCheckedChangeListener(groupId));
        radioGroup.check(0);
    }

    private RadioGroup.OnCheckedChangeListener getCheckedChangeListener(String groupId) {
        switch(groupId) {
            case PLACE_TYPE_GROUP:
                return ((radioGroup, i) -> placeType = PlaceType.values()[i]);
            case MUSIC_GENRE_GROUP:
                return ((radioGroup, i) -> musicGenre = MusicGenre.values()[i]);
            case VENUE_SIZE_GROUP:
                return ((radioGroup, i) -> venueSize = VenueSize.values()[i]);
            case DRESS_CODE_GROUP:
                return ((radioGroup, i) -> dressCode = DressCode.values()[i]);
            default:
                return null;
        }
    }

    private void generateSectionHeaders() {
        placeTypeHeaderView.setItems(new SubHeaderListItem(getString(R.string.place_type)));
        musicTypeHeaderView.setItems(new SubHeaderListItem(getString(R.string.music_type)));
        distanceHeaderView.setItems(new SubHeaderListItem(getString(R.string.distance)));
        venueSizeHeaderView.setItems(new SubHeaderListItem(getString(R.string.venue_size)));
        dressCodeHeaderView.setItems(new SubHeaderListItem(getString(R.string.dress_code)));
    }

    @OnClick(R.id.seeResultsButton)
    public void navigateToResults(View view) {
        Intent intent = new Intent(this, NearbyPlacesActivity.class);
        intent.putExtras(NearbyPlacesActivity.getNavBundle(radius, dressCode, musicGenre, placeType, venueSize));
        startActivity(intent);
    }
}
