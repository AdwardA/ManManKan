package jiyu.manmankan;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by ti on 2018/1/31.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract protected Object getClassName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.i("tag", "life==="+getClassName().toString()+"===onCreate: =====");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tag", "life==="+getClassName().toString()+"===onResume: =====");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag", "life==="+getClassName().toString()+"===onDestroy: =====");
    }
}
