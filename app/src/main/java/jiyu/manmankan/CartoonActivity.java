package jiyu.manmankan;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jiyu.actionMode.TopActionMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiyu.manmankan.adapter.RecycleViewAdapterContent;
import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.IBaseParser;
import jiyu.manmankan.parser.ManManKanParser;
import jiyu.manmankan.service.DownloadService;

public class CartoonActivity extends AppCompatActivity {
    List<LocalCartoonType> list;
    @BindView(R.id.cartoon_name)
    TextView mName;
    @BindView(R.id.content_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ProgressDialog dialog;
    private CartoonType cartoonType;
    private RecycleViewAdapterContent adapter;
    private Map<Integer, LocalCartoonType> downloadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        ButterKnife.bind(this);
        cartoonType = (CartoonType) getIntent().getSerializableExtra("data");
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.show();

        mName.setText(cartoonType.getName());

        ManManKanParser.builder()
                .setCartoonType(cartoonType)
                .getContent(new IBaseParser.onContentCallBack() {
                    @Override
                    public void getContent(List<LocalCartoonType> data) {
                        list = data;
                        setAdapter();
                    }
                });
    }

    private void setAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new RecycleViewAdapterContent(CartoonActivity.this,
                        R.layout.item_recycleview_content, list, cartoonType);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(CartoonActivity.this));
                dialog.dismiss();
            }
        });
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
                        int i=adapter.selectAll(true);
                        fab.setVisibility(View.VISIBLE);
                        mode.setTitle("已选择+selectSize"+i+"个项目");

                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        adapter.showDownloadMode(false);
                        fab.setVisibility(View.GONE);
                        if (downloadData!=null){
                            downloadData.clear();
                        }
                    }
                });
                final ActionMode actionMode=startSupportActionMode(topActionMode);
                adapter.setSelectChangeListener(new RecycleViewAdapterContent.ISelectContentChange() {
                    @Override
                    public void selectChange(Map<Integer, LocalCartoonType> data, int selectSize) {

                        downloadData = data;
                        if (selectSize >= 1) {
                            fab.setVisibility(View.VISIBLE);
                            assert actionMode != null;
                            actionMode.setTitle("已选择"+selectSize+"个项目");
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

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Object[] oo=  downloadData.values().toArray();
        LocalCartoonType[] dataCollection=new LocalCartoonType[downloadData.size()];
        for (int i = 0; i <dataCollection.length; i++) {
            dataCollection[i]= (LocalCartoonType) oo[i];
        }
        String[] titles=new String[dataCollection.length];
        String[] contentUrls=new String[dataCollection.length];
        for (int i = 0; i <dataCollection.length ; i++) {
            titles[i]=dataCollection[i].getTitle();
            contentUrls[i]=dataCollection[i].getAddrss();
        }
        Intent intent=new Intent(CartoonActivity.this, DownloadService.class);
        intent.putExtra("title",titles);
        intent.putExtra("url",contentUrls);
        intent.putExtra("CartoonType",cartoonType);
        startService(intent);

        //模拟返回按键，但是不能在ui线程调用
        new Thread () {
            public void run () {
                try {
                    Instrumentation inst= new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
