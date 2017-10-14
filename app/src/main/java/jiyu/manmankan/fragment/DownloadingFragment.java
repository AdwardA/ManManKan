package jiyu.manmankan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jiyu.manmankan.R;
import jiyu.manmankan.component.StorageProgressBar;

/**
 * Created by z on 2017/10/10.
 */

public class DownloadingFragment extends Fragment {

    @BindView(R.id.download_recycle_content)
    RecyclerView downloadRecycleContent;
    @BindView(R.id.download_recycle_detail)
    RecyclerView downloadRecycleDetail;
    @BindView(R.id.no_data_text)
    TextView noDataText;
    Unbinder unbinder;
    @BindView(R.id.storageProgressBar)
    StorageProgressBar storageProgressBar;
    @BindView(R.id.storage_text)
    TextView storageText;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_downloaded, container, false);
        unbinder = ButterKnife.bind(this, view);
        //隐藏无用的布局
        downloadRecycleContent.setVisibility(View.GONE);
        storageProgressBar.setVisibility(View.GONE);
        storageText.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
