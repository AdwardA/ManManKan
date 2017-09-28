package jiyu.manmankan.component;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiyu.manmankan.R;

/**
 * Created by z on 2017/8/26.
 */

public class IndicatorBar extends LinearLayout {
    @BindView(R.id.chapter)
    TextView chapter;
    @BindView(R.id.page_now)
    TextView pageNow;
    @BindView(R.id.page_total)
    TextView pageTotal;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.electric)
    TextView electric;

    public IndicatorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_indicator_bar, null);
        ButterKnife.bind((Activity) context);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        TimeElectricReceiver receiver=new TimeElectricReceiver();
        context.registerReceiver(receiver,intentFilter);
    }
    public void setData(String chapter,String pageNow,String pageTotal){
        this.chapter.setText(chapter);
        this.pageNow.setText(pageNow);
        this.pageTotal.setText(pageTotal);
    }

    class  TimeElectricReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK)){
                time.setText("text");
            }
        }
    }
}
