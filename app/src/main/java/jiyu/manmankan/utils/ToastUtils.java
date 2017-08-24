package jiyu.manmankan.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ti on 17-7-20.
 */

public class ToastUtils {

    public static void shortToast(Context context, String text){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
    }
}
