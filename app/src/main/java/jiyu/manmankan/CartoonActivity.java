package jiyu.manmankan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiyu.manmankan.adapter.RecycleViewAdapterContent;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.HeroParser;
import jiyu.manmankan.parser.IBaseParser;
import jiyu.manmankan.parser.YaoWangParser;

public class CartoonActivity extends AppCompatActivity {
    List<LocalCartoonType> list;
    @BindView(R.id.cartoon_name)TextView mName;
    @BindView(R.id.content_recyclerView) RecyclerView recyclerView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        ButterKnife.bind(this);
        CartoonType cartoonType= (CartoonType) getIntent().getSerializableExtra("data");
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.show();

        mName.setText(cartoonType.getName());
        switch (cartoonType.getName()){
            case "我的英雄学院":
                new HeroParser().getContent(new HeroParser.onHeroDataCallback() {
                    @Override
                    public void getData( List<LocalCartoonType> data) {
                        list=data;
                        setAdapter();
                    }
                });
                break;
            case "食戟之灵":
                new YaoWangParser().getContentChild(new IBaseParser.onContentCallBack() {
                    @Override
                    public void getContent(List<LocalCartoonType> data) {
                        list=data;
                        setAdapter();
                    }
                });
                break;
            default:
                dialog.dismiss();
                break;
        }

    }
    private void setAdapter(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecycleViewAdapterContent adpter=new RecycleViewAdapterContent(CartoonActivity.this,
                        R.layout.item_recycleview_content,list);
                recyclerView.setAdapter(adpter);
                recyclerView.setLayoutManager(new GridLayoutManager(CartoonActivity.this,5));
                dialog.dismiss();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }


}
