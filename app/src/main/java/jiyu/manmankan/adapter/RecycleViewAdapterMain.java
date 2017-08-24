package jiyu.manmankan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
    private Context context;
    private List<CartoonType> data;
    public RecycleViewAdapterMain(final Context context, @LayoutRes int layoutResId, @Nullable final List<CartoonType> data) {
        super(layoutResId, data);
        this.context=context;
        this.data=data;
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(context, CartoonActivity.class);
                assert data != null;
                intent.putExtra("data",data.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, CartoonType item) {
        helper.setText(R.id.recycle_cartoon_name, item.getName());
        Log.i("tag", "convert: "+item.getAvatar().getUrl());
        Glide
                .with(context)
                .asGif()
                .load(item.getAvatar().getUrl())
                .into((ImageView) helper.getView(R.id.recycle_cartoon_avatar));
    }



}
