package jiyu.manmankan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jiyu.manmankan.ImgActivity;
import jiyu.manmankan.R;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;

/**
 * Created by z on 2017/8/19.
 */

public class RecycleViewAdapterContent extends BaseQuickAdapter<LocalCartoonType,BaseViewHolder> {
    private boolean isShowDownloadMode=false;
    private boolean isSelectAll=false;
    private Map<Integer,LocalCartoonType> downloadContentData=new HashMap<>();
    private List<LocalCartoonType> data;

    public RecycleViewAdapterContent(final Context context, @LayoutRes int layoutResId, @Nullable final List<LocalCartoonType> data, final CartoonType cartoonType) {
        super(layoutResId, data);
        this.data=data;
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
    protected void convert(final BaseViewHolder helper, final LocalCartoonType item) {
        helper.setText(R.id.content_recyclerView_text_num,item.getTitle());
        helper.setVisible(R.id.content_recyclerView_checkbox,isShowDownloadMode);
        CheckBox checkBox= helper.getView(R.id.content_recyclerView_checkbox);
        checkBox.setTag(helper.getAdapterPosition());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    downloadContentData.put(helper.getAdapterPosition(),item);
                }else{
                    downloadContentData.remove(helper.getAdapterPosition());
                }
                if (selectChangeListener!=null){
                    selectChangeListener.selectChange(downloadContentData,downloadContentData.size());
                }
                Log.i("tag", "RecycleViewAdapterConte == onCheckedChanged:==size=="+downloadContentData.size()+"\n "+downloadContentData.values());
            }
        });
        checkBox.setChecked(downloadContentData.get(helper.getAdapterPosition())!=null);

        //控制全选
        if(isSelectAll){
            checkBox.setChecked(true);
            if (selectChangeListener!=null){
                selectChangeListener.selectChange(downloadContentData,downloadContentData.size());
            }
            for (int i = 0; i <data.size() ; i++) {
                downloadContentData.put(i,data.get(i));
            }
        }
    }


    public void showDownloadMode(boolean isShowDownloadMode){
        this.isShowDownloadMode=isShowDownloadMode;
        isSelectAll=false;
        //不显示下载的时候
        if (!isShowDownloadMode){
            downloadContentData.clear();
            Log.i("tag", "RecycleViewAdapterConte == showDownloadMode: ==downloadContentData.size=="+downloadContentData.size());
        }
        this.notifyDataSetChanged();
    }


    public int selectAll(boolean isSelectAll){
        this.isSelectAll=isSelectAll;
        this.notifyDataSetChanged();
        return data.size();
    }


    private ISelectContentChange selectChangeListener;
    public void setSelectChangeListener(ISelectContentChange selectChangeListener) {
        this.selectChangeListener = selectChangeListener;
    }
    public interface ISelectContentChange{
        void selectChange(Map<Integer,LocalCartoonType> data,int selectSize);
    }
}
