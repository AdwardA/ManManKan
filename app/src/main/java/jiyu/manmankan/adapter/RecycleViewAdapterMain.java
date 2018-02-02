package jiyu.manmankan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jiyu.manmankan.CartoonActivity;
import jiyu.manmankan.R;
import jiyu.manmankan.entity.CartoonType;



/**
 * Created by z on 2017/8/16.
 */

public class RecycleViewAdapterMain extends BaseQuickAdapter<CartoonType,BaseViewHolder> {
    private Activity activity;
    private List<CartoonType> data;
    @SuppressLint("WrongViewCast")
    public RecycleViewAdapterMain(final Activity activity, @LayoutRes int layoutResId, @Nullable final List<CartoonType> data) {
        super(layoutResId, data);
        this.activity=activity;
        this.data=data;
        this.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            Intent intent=new Intent(activity, CartoonActivity.class);
            assert data != null;
            intent.putExtra("data",data.get(position));
            //设置共享元素
            ImageView img=view.findViewById(R.id.recycle_cartoon_avatar);
            Pair pair1=Pair.create(img,"img");
            ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(activity
                    ,pair1);
            //返回时恢复gif动图
            activity.setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                    super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
                    for (int i = 0; i < sharedElementNames.size(); i++) {
                        if (sharedElementNames.get(i).equals("img")){
                            new android.os.Handler().postDelayed(()->{
                                if (!activity.isDestroyed()){
                                    Glide.with(activity)
                                            .load(data.get(position).getAvatar().getUrl())
                                            .into(img);
                                }
                            },500);
                        }
                    }
                }
            });
            activity.startActivity(intent, activityOptions.toBundle());
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, CartoonType item) {
        helper.setText(R.id.recycle_cartoon_name, item.getName());
        Log.i("tag", "convert: "+item.getAvatar().getUrl());
        Glide
                .with(activity)
                .load(item.getAvatar().getUrl())
//                .apply()
                .into((ImageView) helper.getView(R.id.recycle_cartoon_avatar));
    }


    RequestOptions requestOptions=new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL);
//            .
}
