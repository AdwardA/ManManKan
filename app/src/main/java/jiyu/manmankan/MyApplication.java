package jiyu.manmankan;

import java.io.IOException;

import jiyu.manmankan.utils.FileUtils;

/**
 * Created by ti on 2017/9/26.
 */

public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            FileUtils.setFoldersInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
