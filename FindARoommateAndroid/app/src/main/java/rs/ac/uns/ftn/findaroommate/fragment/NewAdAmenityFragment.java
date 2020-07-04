package rs.ac.uns.ftn.findaroommate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.adapter.ChipAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.UserReviewRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class NewAdAmenityFragment extends NewAdFragmentAbstact {

    ChipGroup chipGroup;
    List<AdItem> selectedAdItems;
    RecyclerView amenityChipView;

    public NewAdAmenityFragment(AdDto ad) {
        super(ad);
    }

    public static NewAdAmenityFragment newInstance(AdDto ad) {
        NewAdAmenityFragment mpf = new NewAdAmenityFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_add_amenity, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + dialogNamePatern.replace("NAME", getString(R.string.ad_form_amenity)));

        if(savedInstanceState != null){

        }

        selectedAdItems = ad.getAdItems();

        amenityChipView = (RecyclerView) view.findViewById(R.id.amenity_chip_view);
        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        amenityChipView.setLayoutManager(recyclerViewLayoutManager);
        amenityChipView.setAdapter(new ChipAdapter(Mockup.getInstance().getAdItems(), selectedAdItems, ad.getAdItemsIds()));

        //chipGroup = (ChipGroup) view.findViewById(R.id.chips);
        //createAdItemChips();

        return view;
    }

    private void createAdItemChips(){
        for (AdItem adItem : Mockup.getInstance().getAdItems()){
            Chip c1 = (Chip) this.getLayoutInflater().inflate(R.layout.amenity_chip_item, null, false);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            double ratio = ((float) (width))/300.0;
            int height = (int)(ratio*50);
            c1.setWidth(width);

            c1.setText(adItem.getName());
            c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Chip c = (Chip) buttonView;
                    if(isChecked){
                        selectedAdItems.add((AdItem) c.getTag());
                        ad.getAdItemsIds().add(((AdItem) c.getTag()).getEntityId());
                    } else {
                        selectedAdItems.remove((AdItem)c.getTag());
                        ad.getAdItemsIds().remove(new Integer(((AdItem) c.getTag()).getEntityId()));
                    }
                }
            });
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            c1.setPadding(paddingDp, 0, paddingDp, 0);
            c1.setTag(adItem);

            c1.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean t = true;
                    Chip c = (Chip)v;
                    c.setChecked(false);
                    selectedAdItems.remove((AdItem)c.getTag());
                    ad.getAdItemsIds().remove(new Integer(((AdItem) c.getTag()).getEntityId()));

                }
            });

            chipGroup.addView(c1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        NewAdActivity act = (NewAdActivity) getActivity();
        act.getToolbar().setTitle("New ad " + dialogNum + " of 6");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Gladiator");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
