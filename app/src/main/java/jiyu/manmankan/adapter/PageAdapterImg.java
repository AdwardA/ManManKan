package jiyu.manmankan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jiyu.manmankan.R;

/**
 * Created by z on 2017/8/22.
 */

public class PageAdapterImg extends PagerAdapter {
    private String[] urls;
    private ImageView[] imageViews;
    private Context context;
    private View[] views;
    private ImageView imageView;

    public PageAdapterImg(String[] urls, Context context) {
        this.urls=urls;
        this.context=context;
        imageViews=new ImageView[urls.length];
        views=new View[urls.length];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_view_pager_img,null);
        views[position]=view;
        TextView textView=view.findViewById(R.id.text);
        imageView = view.findViewById(R.id.img_view_page);
        textView.setText(urls[position]);

        Glide
                .with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(urls[position])
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i("tag", "destroyItem: ===destroyItem==="+position);
        container.removeView(views[position]);

    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    RequestOptions requestOptions=new RequestOptions()
            .skipMemoryCache(true)
            .fitCenter();
}
