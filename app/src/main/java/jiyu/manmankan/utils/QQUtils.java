package jiyu.manmankan.utils;

import android.app.Activity;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by z on 2017/8/23.
 */

public class QQUtils {
    public static Tencent tencent;
    private final Activity activity;
    private String mApp_id="101425044";

    public static Tencent getTencent() {
        return tencent;
    }
    public  QQUtils(Activity activity){
        this.activity=activity;
        tencent = Tencent.createInstance(mApp_id,activity.getApplicationContext());
    }

    public void login(){
        tencent.login(activity, "all", new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i("tag", "onComplete: ====\n"+o.toString());
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

}
