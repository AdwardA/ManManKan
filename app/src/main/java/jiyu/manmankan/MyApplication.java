package jiyu.manmankan;

import android.os.Environment;
import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.tencent.bugly.Bugly;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jiyu.manmankan.data.DBManManKan;
import jiyu.manmankan.data.DBTDownload;
import jiyu.manmankan.utils.FileUtils;

/**
 * Created by ti on 2017/9/26.
 */

public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(this,"5d403b0c62",true);
        FlowManager.init(this);
        DatabaseDefinition db=FlowManager.getDatabase(DBManManKan.class);
        Log.i("tag", "onCreate: "+ db.getDatabaseFileName());
        List<DBTDownload> list= SQLite.select()
                .from(DBTDownload.class)
                .queryList();
        Log.i("tag", "onCreate:====个数="+list.size());
        if (list.size()>0){
            Log.i("tag", "onCreate:===mingzi=="+list.get(0).getCartoonName());
            Log.i("tag", "onCreate: =====数据库路径=="+ this.getDatabasePath("ManManKan.db"));
        }

        try {
            FileUtils.setFoldersInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
