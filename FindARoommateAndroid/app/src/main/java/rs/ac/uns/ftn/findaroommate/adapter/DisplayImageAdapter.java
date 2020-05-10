package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFinalFragment;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;


public class DisplayImageAdapter extends PagerAdapter {
    Context context;
    public List<ResourceRegistry> images;
    LayoutInflater layoutInflater;
    Fragment fragment;

    public DisplayImageAdapter(Context context, Fragment fragment,List<ResourceRegistry> images) {
        this.context = context;
        this.fragment = fragment;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.image_item, container,false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_item_view);
        final ResourceRegistry image = images.get(position);
        imageView.setImageURI(Uri.parse(image.getUri()));

        Button button = (Button) itemView.findViewById(R.id.image_item_delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment instanceof NewAdFinalFragment){
                    int posAfterDelete = -1;
                    if (position == 0){
                        posAfterDelete = 0;
                    } else if (position == getCount() - 1) {
                        posAfterDelete = position - 1;
                    } else {
                        posAfterDelete = position + 1;
                    }
                    ((NewAdFinalFragment)fragment).removeImage(image, posAfterDelete);
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}

