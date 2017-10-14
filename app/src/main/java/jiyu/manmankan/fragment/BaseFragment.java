package jiyu.manmankan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Unbinder;
import jiyu.manmankan.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    private View view;
    private Unbinder unbinder;
    private Context context;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getResLayout(),null);
        unbinder = initView(view);
        setOnRefresh(getOnRefreshListener());
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getResLayout();

    protected abstract Unbinder initView(View view);

    protected abstract void initData();
    protected abstract MainActivity.onRefreshListener getOnRefreshListener();

    void setOnRefresh(MainActivity.onRefreshListener listener){
        MainActivity activity= (MainActivity) getActivity();
        activity.swipeRefreshLayout.setRefreshing(true);
        if (listener==null){
            activity.swipeRefreshLayout.setEnabled(false);
            return;
        }
        activity.swipeRefreshLayout.setEnabled(true);
        activity.setOnRefreshListener(listener);
        activity.onRefresh();
    }
}
