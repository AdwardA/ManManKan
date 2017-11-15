package jiyu.manmankan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import jiyu.manmankan.fragment.DownloadFragment;
import jiyu.manmankan.fragment.MainContentFragment;
import jiyu.manmankan.utils.BmobUpdateUtils;
import jiyu.manmankan.utils.QQUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.swipe_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ImageView imageView;
    private QQUtils qqUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_main);
        //第一：默认初始化
        qqUtils = new QQUtils(this);
        Bmob.initialize(this, "5af3d42c59b69857fd2e57c7bf5e7491");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        imageView=view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqUtils.login();
            }
        });
        setFragment(0);
//        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        new BmobUpdateUtils().update(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_first) {

        } else if (id == R.id.nav_collect) {

        } else if (id == R.id.nav_download) {//下载管理

        } else if (id == R.id.nav_cache) {


        }
        setFragment(id);
        navigationView.setCheckedItem(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //设置fragment
    MainContentFragment mainContentFragment=new MainContentFragment();
    DownloadFragment downloadFragment=new DownloadFragment();
    private void setFragment(int selectId){
        Fragment fragment=null;
        switch (selectId){
            case R.id.nav_first:
            default:
                navigationView.setCheckedItem(R.id.nav_first);
                fragment=mainContentFragment;
                break;
            case R.id.nav_download:
                fragment=downloadFragment;
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content,fragment)
                .commit();
    }


    @Override
    public void onRefresh() {
        Log.i("tag", "onRefresh: ...");
        refreshListener.refresh(swipeRefreshLayout);
    }
    //回调来自子fragment设置的refresh方法
    onRefreshListener refreshListener;
    public void setOnRefreshListener(onRefreshListener listener){
        this.refreshListener=listener;
    }
    public interface onRefreshListener{
        void refresh(SwipeRefreshLayout swipeRefreshLayout);
    }
}
