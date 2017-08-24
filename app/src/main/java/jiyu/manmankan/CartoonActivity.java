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
import jiyu.manmankan.entity.Hero;
import jiyu.manmankan.parser.HeroParser;

public class CartoonActivity extends AppCompatActivity {
    List<Hero> list;
    @BindView(R.id.cartoon_name)TextView mName;
    @BindView(R.id.content_recyclerView) RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        ButterKnife.bind(this);
        CartoonType cartoonType= (CartoonType) getIntent().getSerializableExtra("data");
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.show();

        mName.setText(cartoonType.getName());
        new HeroParser().getContent(new HeroParser.onHeroDataCallback() {
            @Override
            public void getData( List<Hero> data) {
                list=data;
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
