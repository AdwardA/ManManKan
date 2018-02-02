package jiyu.manmankan;

import android.app.Instrumentation;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiyu.manmankan.adapter.RecycleViewAdapterContent;
import jiyu.manmankan.component.TopActionMode;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.ManManKanParser;
import jiyu.manmankan.service.DownloadService;

public class CartoonActivity extends BaseActivity {
    List<LocalCartoonType> list;
    @BindView(R.id.content_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    private CartoonType cartoonType;
    private RecycleViewAdapterContent adapter;
    private Map<Integer, LocalCartoonType> downloadData;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_cartoon);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        cartoonType = (CartoonType) getIntent().getSerializableExtra("data");
        Glide.with(this).load(cartoonType.getAvatar().getUrl()).into(imageView);

        toolbarLayout.setTitle(cartoonType.getName());
        ManManKanParser.builder()
                .setCartoonType(cartoonType)
                .getContent(data -> {
                    list = data;
                    setAdapter();
                });

        this.setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
                Log.i("tag", "CartoonActivity===onSharedElementsArrived: =====");
                for (int i = 0; i < sharedElementNames.size(); i++) {
                    if (sharedElementNames.get(i).equals("img")) {
                        int finalI = i;
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(() -> {
                                    Glide.with(CartoonActivity.this).load(cartoonType.getAvatar().getUrl()).into(imageView);
                                    Log.i("tag", "CartoonActivity===onSharedElementsArrived: ===img==");
                                });
                            }
                        }, 500);
                    }
                }
            }
        });

    }

    private void setAdapter() {
        runOnUiThread(() -> {
            adapter = new RecycleViewAdapterContent(CartoonActivity.this,
                    R.layout.item_recycleview_content, list, cartoonType);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(CartoonActivity.this));
            //dialog.dismiss();
        });
    }

    @Override
    protected Object getClassName() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.toolbar_download:
                adapter.showDownloadMode(true);
                TopActionMode topActionMode = new TopActionMode(this);
                topActionMode.setiActionMode(new TopActionMode.IActionMode() {
                    @Override
                    public void onActionItemClickedSelectAll(ActionMode mode, MenuItem item) {
                        Log.i("tag", "CartoonActivity == onActionItemClickedSelectAll: ");
                        int i = adapter.selectAll(true);
                        fab.setVisibility(View.VISIBLE);
                        mode.setTitle("已选择+selectSize" + i + "个项目");
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        adapter.showDownloadMode(false);
                        fab.setVisibility(View.GONE);
                        if (downloadData != null) {
                            downloadData.clear();
                        }
                    }
                });
                final ActionMode actionMode = startSupportActionMode(topActionMode);
                adapter.setSelectChangeListener(new RecycleViewAdapterContent.ISelectContentChange() {
                    @Override
                    public void selectChange(Map<Integer, LocalCartoonType> data, int selectSize) {

                        downloadData = data;
                        if (selectSize >= 1) {
                            fab.setVisibility(View.VISIBLE);
                            assert actionMode != null;
                            actionMode.setTitle("已选择" + selectSize + "个项目");
                        } else {
                            assert actionMode != null;
                            actionMode.setTitle("选择项目");
                            fab.setVisibility(View.GONE);
                        }
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Object[] oo = downloadData.values().toArray();
        LocalCartoonType[] dataCollection = new LocalCartoonType[downloadData.size()];
        for (int i = 0; i < dataCollection.length; i++) {
            dataCollection[i] = (LocalCartoonType) oo[i];
        }
        String[] titles = new String[dataCollection.length];
        String[] contentUrls = new String[dataCollection.length];
        for (int i = 0; i < dataCollection.length; i++) {
            titles[i] = dataCollection[i].getTitle();
            contentUrls[i] = dataCollection[i].getAddrss();
        }
        Intent intent = new Intent(CartoonActivity.this, DownloadService.class);
        intent.putExtra("title", titles);
        intent.putExtra("url", contentUrls);
        intent.putExtra("CartoonType", cartoonType);
        startService(intent);

        //模拟返回按键，但是不能在ui线程调用
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
