package jiyu.manmankan.data;

import android.util.Log;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by z on 2017/7/28.
 */

public class MBmobQuery <T>{
    private BmobQuery<T> query=new BmobQuery<T>();

    public void selectAll(){
//       query.findObjects(new FindListener<T>() {
//           @Override
//           public void done(List<T> list, BmobException e) {
//               if (e==null){
//                   Log.i("tag",list.get(0).toString());
//               }
//           }
//       });
        
        query.getObject("h5It111U", new QueryListener<T>() {
            @Override
            public void done(T t, BmobException e) {
                Log.i("tag",t.toString());
            }
        });
    }
}
