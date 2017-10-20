package jiyu.manmankan.service;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jiyu.manmankan.utils.FileUtils;
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
    private String[] imgUrls;
    private Context context;
    private RetrofitResponseBody retrofitResponseBody;
    public DownloadThread(String title, String[] imgUrls){
        Log.i("tag", "DownloadThread == DownloadThread: ==downloadPath=="+title);
        FileUtils.creatDirectityInSdcardJiyu(title);
        savePath=title;
        this.imgUrls=imgUrls;
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
            final int finalI = i;
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        writeToDisk(response, finalI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void writeToDisk(Response<ResponseBody> response,int i) throws IOException {
        InputStream is=response.body().byteStream();
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
    }

}
