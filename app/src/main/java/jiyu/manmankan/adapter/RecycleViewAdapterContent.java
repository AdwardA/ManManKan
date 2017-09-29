package jiyu.manmankan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jiyu.manmankan.ImgActivity;
import jiyu.manmankan.R;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;

/**
 * Created by z on 2017/8/19.
 */

public class RecycleViewAdapterContent extends BaseQuickAdapter<LocalCartoonType,BaseViewHolder> {

    public RecycleViewAdapterContent(final Context context, @LayoutRes int layoutResId, @Nullable final List<LocalCartoonType> data, final CartoonType cartoonType) {
        super(layoutResId, data);
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(context, ImgActivity.class);
                assert data != null;
                intent.putExtra("data",data.get(position));
                intent.putExtra("cartoonType",cartoonType);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalCartoonType item) {
        helper.setText(R.id.content_recyclerView_text_num,item.getTitle());

    }
}
