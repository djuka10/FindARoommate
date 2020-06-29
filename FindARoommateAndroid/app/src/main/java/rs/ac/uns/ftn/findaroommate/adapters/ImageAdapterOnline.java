package rs.ac.uns.ftn.findaroommate.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;


public class ImageAdapterOnline extends PagerAdapter {
    Context context;
    List<ResourceRegistry> images;
    LayoutInflater layoutInflater;

    ProgressDialog loadingBar;

    public static String IMAGE_URL = "http://HOST/server/user/profile/";

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

        loadingBar = new ProgressDialog(context);

        loadingBar.setTitle("Loading profile into");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();


        //TODO ukoliko ne postoje slike treba dismissovati ovo

        Glide.with(context)
                .load(IMAGE_URL.replace("HOST", context.getString(R.string.host)) + fileName)
                .listener(new RequestListener<Drawable>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                  loadingBar.dismiss();
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                  loadingBar.dismiss();
                                  return false;
                              }
                          }
                )
                .into(imageView);

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
