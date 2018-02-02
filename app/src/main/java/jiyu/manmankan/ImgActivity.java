package jiyu.manmankan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiyu.manmankan.adapter.PageAdapterImg;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.ManManKanParser;
import jiyu.manmankan.utils.FileUtils;

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


        if (localCartoonType.isDownloaded()){
            File file=new File(FileUtils.PATH_DOWNLOAD+"/"+localCartoonType.getName()+"/"+localCartoonType.getTitle());
            Log.i("tag", "onCreate:===path=="+file.getPath());
            String[] address=new String[file.list().length];
            for (int i = 0; i <address.length ; i++) {
                address[i]= FileUtils.PATH_DOWNLOAD+"/"
                        +localCartoonType.getName()+"/"
                        +localCartoonType.getTitle()+"/"
                        +i+".jpg";
            }
            PageAdapterImg adapter=new PageAdapterImg(address,ImgActivity.this);
            imgViewPage.setAdapter(adapter);
        }else {
            ManManKanParser.builder()
                    .setCartoonType(cartoonType)
                    .getImgAddress(contentAddress, imgAddress -> runOnUiThread(() -> {
                        PageAdapterImg adapter=new PageAdapterImg(imgAddress,ImgActivity.this);
                        imgViewPage.setAdapter(adapter);
                    }));
        }

    }

}
