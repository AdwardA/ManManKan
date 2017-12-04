package jiyu.manmankan.service;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jiyu.manmankan.data.DBTDownload;
import jiyu.manmankan.data.DBTDownload_Table;
import jiyu.manmankan.utils.FileUtils;
import jiyu.manmankan.utils.ToastUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by z on 2017/10/16.
 * O(∩_∩)~
 */

public class DownloadThread extends Thread {
    private String savePath= "";
    private String title;
    private String[] imgUrls;
    private Context context;
    private Map<Integer,String> map=new HashMap<>();
    private RetrofitResponseBody retrofitResponseBody;


    public DownloadThread(String path,String title, String[] imgUrls){
        savePath=path+title;
        FileUtils.creatDirectityInSdcardJiyu(savePath);
        Log.i("tag", "DownloadThread == DownloadThread: ==downloadPath=="+savePath);
        this.title=title;
        this.imgUrls=imgUrls;
        for (int i = 0; i <imgUrls.length ; i++) {
            map.put(i,imgUrls[i]);
        }
    }

    interface RetrofitResponseBody{
        @GET
        Call<ResponseBody> downloadImg(@Url String url);
    }


    @Override
    public void run() {
        super.run();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://timgsa.baidu.com/")
                .build();
        retrofitResponseBody=retrofit.create(RetrofitResponseBody.class);
        for (int i = 0; i <imgUrls.length ; i++) {
            Call<ResponseBody> call=retrofitResponseBody.downloadImg(imgUrls[i]);
            try {
               Response response= call.execute();
                if (response.isSuccessful()){
                    writeToDisk((ResponseBody) response.body(), i);
                }else {
                    ToastUtils.shortToast(context,"下载失败："+response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeToDisk(ResponseBody response, int i) throws IOException {
        InputStream is=response.byteStream();
        File file=new File(savePath,i+".jpg");
        FileOutputStream fos=new FileOutputStream(file);
        BufferedInputStream bis=new BufferedInputStream(is);
        byte[] buffer=new byte[1024];
        int len;
        while ((len=(bis.read(buffer)))!=-1){
            fos.write(buffer,0,len);
        }
        fos.flush();
        fos.close();
        bis.close();
        is.close();
        Log.i("tag", "DownloadThread == writeToDisk: ==i=="+i);


        if(map.containsKey(i)){
            map.remove(i);
        }
        if (map.isEmpty()){
            SQLite.update(DBTDownload.class)
                    .set(DBTDownload_Table.isDone.eq(true))
                    .where(DBTDownload_Table.contentName.eq(title))
                    .execute();
            Log.i("tag", "writeToDisk:===下载完成=="+title);
        }
    }

}
