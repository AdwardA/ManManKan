package jiyu.manmankan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import jiyu.manmankan.MainActivity;

/**
 * Created by z on 2017/8/23.
 */

public class QQUtils implements IUiListener {
    public static Tencent tencent;
    private final MainActivity activity;
    private String mApp_id = "1106296341";
    private final SharedPreferences sp;
    private static final String AVA40="figureurl_qq_1";
    private static final String AVA100="figureurl_qq_2";
    private static final String NICKNAME="nickname";
    public static Tencent getTencent() {
        return tencent;
    }

    public QQUtils(MainActivity activity) {
        this.activity = activity;
        sp = activity.getSharedPreferences(activity.getPackageName()+"_preferences", Context.MODE_PRIVATE);
        tencent = Tencent.createInstance(mApp_id, activity.getApplicationContext());
        tencent.setOpenId(sp.getString(Constants.PARAM_OPEN_ID,""));
        tencent.setAccessToken(sp.getString(Constants.PARAM_ACCESS_TOKEN,"")
                ,sp.getString(Constants.PARAM_EXPIRES_IN,"0"));
    }

    public void login() {
        tencent.login(activity, "all", this);
    }

    public void logout() {
        tencent.logout(activity);
    }

    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11101) {
            if (resultCode == Activity.RESULT_OK) {
                Tencent.handleResultData(data,this);
            }
        }
    }

    public void getUserInfo() {
        UserInfo userInfo=new UserInfo(activity,tencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i("tag", "onComplete:==userInfo==="+o.toString());
                JSONObject oo= (JSONObject) o;
                try {
                    if (oo.getString("ret").equals("0")){
                        activity.setUserInfo(oo.getString(AVA100),oo.getString(NICKNAME));
                    }else {
                        login();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
    @Override
    public void onComplete(Object o) {
        Log.i("tag", "onComplete:=====\n" + o.toString());
        JSONObject oo= (JSONObject) o;
        if (o.toString().contains("\"ret\":0")){
            try {
                String openId=oo.getString(Constants.PARAM_OPEN_ID);
                String token=oo.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires=oo.getString(Constants.PARAM_EXPIRES_IN);
                Log.i("tag", "getUserInfo:===openID=="+openId+"==expires_in="+expires);
                tencent.setOpenId(openId);
                tencent.setAccessToken(token,expires);
                //保存openID等信息到sp
                SharedPreferences.Editor editor=sp.edit();
                editor.putString(Constants.PARAM_OPEN_ID,openId);
                editor.putString(Constants.PARAM_ACCESS_TOKEN,token);
                editor.putString(Constants.PARAM_EXPIRES_IN,expires);
                editor.apply();
                //获取用户名字和头像
                getUserInfo();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        Log.i("tag", "onError() returned: " + uiError.toString());
    }

    @Override
    public void onCancel() {
        Log.i("tag", "onCancel:=====");
    }
}
