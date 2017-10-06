package jiyu.manmankan.utils;

import android.content.Context;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by z on 2017/10/3.
 */

public class BmobUpdateUtils {
    public void update(Context context){
//        注：
//        1、initAppVersion方法适合开发者调试自动更新功能时使用，一旦AppVersion表在后台创建成功，建议屏蔽或删除此方法，否则会生成多行记录。
//        2、如果调用了此方法后，在管理后台没有看见AppVersion表生成，建议到手机的应用管理界面清除该应用的数据，并再次调用该方法，也可到LogCat中查看与bmob相关错误日志。
//        3、如果2方法尝试多次之后仍然无效，请手动创建AppVersion表，表的各个字段名称请查看下表。
//        BmobUpdateAgent.initAppVersion();

        BmobUpdateAgent.update(context);
        //考虑到用户流量的限制，目前我们默认在WiFi接入情况下才进行自动提醒。如需要在任意网络环境下都进行更新自动提醒，则请在update调用之前添加以下代码：
        BmobUpdateAgent.setUpdateOnlyWifi(false);

    }
}
