package jiyu.manmankan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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

    Fragment[] fragments=new Fragment[]{new DownloadedFragment(),new DownloadingFragment()};
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

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
        //会清除tab
        tabLayout.setupWithViewPager(viewPager,true);
        tabLayout.getTabAt(0).setText("已下载");
        tabLayout.getTabAt(1).setText("下载中");
    }

    @Override
    protected MainActivity.onRefreshListener getOnRefreshListener() {
        return null;
    }
}
