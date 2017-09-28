package jiyu.manmankan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiyu.manmankan.adapter.PageAdapterImg;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.HeroParser;

public class ImgActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_view_page)
    ViewPager imgViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ButterKnife.bind(this);
        toolbar.setVisibility(View.GONE);
        LocalCartoonType localCartoonType = (LocalCartoonType) getIntent().getSerializableExtra("data");
        String contentAddress = localCartoonType.getAddrss();
        String name=getIntent().getStringExtra("name");
        switch (name){
            case "我的英雄学院":
                new HeroParser().getImgAddress(contentAddress, new HeroParser.onHeroAddressCallback() {
                    @Override
                    public void getAddress(final String[] address) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PageAdapterImg adapter=new PageAdapterImg(address,ImgActivity.this);
                                imgViewPage.setAdapter(adapter);
                            }
                        });
                    }
                });
                break;
            case "食戟之灵":
                break;
        }


    }

}
