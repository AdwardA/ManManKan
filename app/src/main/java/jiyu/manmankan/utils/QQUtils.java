package jiyu.manmankan.utils;

import android.app.Activity;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by z on 2017/8/23.
 */

public class QQUtils {
    private static Tencent tencent;
    private final Activity activity;

    public static Tencent getTencent() {
        return tencent;
    }
    public  QQUtils(Activity activity){
        this.activity=activity;
        tencent = Tencent.createInstance("jiyu",activity.getApplicationContext());
        tencent.setOpenId("1106294777");
    }

    public void login(){
        tencent.login(activity, "all", new IUiListener() {
            @Override
            public void onComplete(Object o) {

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
