package jiyu.manmankan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jiyu.manmankan.R;
import jiyu.manmankan.component.StorageProgressBar;
import jiyu.manmankan.utils.FileUtils;

/**
 * Created by z on 2017/10/10.
 */

public class DownloadedFragment extends Fragment {

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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("tag", "DownloadedFragment == onStart: ");
    }

    @Override
    public void onResume() {
        Log.i("tag", "DownloadedFragment == onResume: ");
        super.onResume();
        long total=FileUtils.getTotalInternalStorgeSizeLong();
        long available=FileUtils.getAvailableInternalStorgeSizeLong();
        storageText.setText("主储存："+FileUtils.getTotalInternalStorgeSize()+" /可用："+FileUtils.getAvailableInternalStorgeSize());
        storageProgressBar.setProgressBarScale( (total-available)/ (double)total);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}