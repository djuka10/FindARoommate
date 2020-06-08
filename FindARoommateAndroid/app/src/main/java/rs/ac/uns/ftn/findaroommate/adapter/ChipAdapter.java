package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserReviewDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.ReviewDto;
import rs.ac.uns.ftn.findaroommate.model.AdItem;

public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.ViewHolder>{

    private List<AdItem> mItems;
    private List<AdItem> selectedIAdtems;
    private List<Integer> selectedAdItemsId;


    public ChipAdapter(List<AdItem> items, List<AdItem> selectedIAdtems, List<Integer> selectedAdItemsId) {
        this.mItems = items;
        this.selectedIAdtems = selectedIAdtems;
        this.selectedAdItemsId = selectedAdItemsId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.amenity_chip_item_recl, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AdItem item = mItems.get(i);

        Chip chip = viewHolder.chip;
        chip.setTag(item);
        chip.setText(item.getName());

        if(shouldBeSelected(chip)){
            chip.setChecked(true);
        }

        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Chip c = (Chip) buttonView;
                if(isChecked){
                    selectedIAdtems.add((AdItem) c.getTag());
                    selectedAdItemsId.add(((AdItem) c.getTag()).getEntityId());
                } else {
                    AdItem adItem = (AdItem)c.getTag();
                    selectedIAdtems.remove(adItem);
                    selectedAdItemsId.remove(Integer.valueOf(adItem.getEntityId()));
                }
            }
        });


        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean t = true;
                Chip c = (Chip)v;
                c.setChecked(false);
                AdItem adItem = (AdItem)c.getTag();
                selectedIAdtems.remove(adItem);
                selectedAdItemsId.remove(Integer.valueOf(adItem.getEntityId()));
            }
        });
    }

    private boolean shouldBeSelected(Chip chip){
        AdItem item = (AdItem)chip.getTag();

        for(AdItem i : selectedIAdtems){
            if(i == item){
                return true;
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final Chip chip;

        ViewHolder(View v) {
            super(v);
            chip = (Chip)v.findViewById(R.id.chip);
        }
    }

}
