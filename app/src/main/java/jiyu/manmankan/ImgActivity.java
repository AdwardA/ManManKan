package jiyu.manmankan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiyu.manmankan.adapter.PageAdapterImg;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.IBaseParser;
import jiyu.manmankan.parser.ManManKanParser;

public class ImgActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_view_page)
    ViewPager imgViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_img);
        ButterKnife.bind(this);
        toolbar.setVisibility(View.GONE);
        LocalCartoonType localCartoonType = (LocalCartoonType) getIntent().getSerializableExtra("data");
        String contentAddress = localCartoonType.getAddrss();
        CartoonType cartoonType= (CartoonType) getIntent().getSerializableExtra("cartoonType");
        Log.i("tag", "onCreate: ===="+contentAddress);

        ManManKanParser.builder()
                .setCartoonType(cartoonType)
                .getImgAddress(contentAddress, new IBaseParser.onAddressCallBack() {
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
    }

}
