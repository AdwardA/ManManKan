package jiyu.manmankan.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.raizlabs.android.dbflow.annotation.NotNull;

/**
 * Created by ti on 17-7-20.
 */

public class ToastUtils {

    public static void shortToast( Context context, String text){
        if (context!=null) Toast.makeText(context,text, Toast.LENGTH_SHORT).show();

    }

    public static void longToast(Context context, String text){
        if (context!=null) Toast.makeText(context,text, Toast.LENGTH_LONG).show();
    }
}
