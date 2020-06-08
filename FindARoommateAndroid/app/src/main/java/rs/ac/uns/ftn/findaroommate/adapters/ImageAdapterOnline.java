package rs.ac.uns.ftn.findaroommate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;


public class ImageAdapterOnline extends PagerAdapter {
    Context context;
    List<ResourceRegistry> images;
    LayoutInflater layoutInflater;

    public ImageAdapterOnline(Context context, List<ResourceRegistry> images) {
        this.context = context;
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
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.activity_item, container,false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        String fileName = images.get(position).getUri();

        String IMAGE_URL = "http://192.168.1.2:8089/server/user/profile/";
        Glide.with(context)
                .load(IMAGE_URL + fileName).into(imageView);

        container.addView(itemView);



        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
