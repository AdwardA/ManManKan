package jiyu.manmankan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jiyu.manmankan.MainActivity;
import jiyu.manmankan.R;

/**
 * Created by z on 2017/10/7.
 */

public class DownloadFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_download;
    }

    @Override
    protected Unbinder initView(View view) {

        return ButterKnife.bind(this,view);
    }

    @Override
    protected void initData() {
        tabLayout.addTab(tabLayout.newTab().setText("已下载"));
        tabLayout.addTab(tabLayout.newTab().setText("下载中"));
//        tabLayout.setupWithViewPager(viewPager,true);
    }

    @Override
    protected MainActivity.onRefreshListener getOnRefreshListener() {
        return null;
    }
}
