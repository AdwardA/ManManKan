package jiyu.manmankan.data;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

/**
 * Created by z on 2017/12/1.
 */

@Database(name = DBManManKan.NAME,version = DBManManKan.VERSION)
public class DBManManKan {
   public static final String NAME="ManManKan";
   public static final int VERSION=2;

}
