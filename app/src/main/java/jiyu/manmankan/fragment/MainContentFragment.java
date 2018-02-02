package jiyu.manmankan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import jiyu.manmankan.MainActivity;
import jiyu.manmankan.R;
import jiyu.manmankan.adapter.RecycleViewAdapterMain;
import jiyu.manmankan.entity.CartoonType;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainContentFragment extends BaseFragment {
    @BindView(R.id.main_recycleView)
    RecyclerView recyclerView;

    public MainContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected int getResLayout() {
        return R.layout.content_main;
    }

    @Override
    protected Unbinder initView(View view) {
        return ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected MainActivity.onRefreshListener getOnRefreshListener() {
        return swipeRefreshLayout -> new Thread(() -> {
            BmobQuery<CartoonType> query = new BmobQuery<CartoonType>();
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
            query.findObjects(new FindListener<CartoonType>() {
                @Override
                public void done(List<CartoonType> list, BmobException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (e == null) {
                        Log.i("tag", "done: " + list.size() + "ge");
                        RecycleViewAdapterMain adapter = new RecycleViewAdapterMain(
                                getActivity(), R.layout.item_recycleview_main_cartoon, list);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    } else {
                        Log.i("tag", "done: fail:" + e.getMessage());
                    }
                }
            });
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
