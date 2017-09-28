package jiyu.manmankan.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jiyu.manmankan.entity.LocalCartoonType;


/**
 * Created by z on 2017/8/17.
 */

public class HeroParser {
    private String uri="http://manhua.fzdm.com/131/";
    private String urlContent;
    private String[] urls;
    private String[] imgUrls=new String[30];
    private List<LocalCartoonType> data=new ArrayList<LocalCartoonType>();

    //爬取漫画章节
    public void getContent(final onHeroDataCallback callback)  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc= null;
                try {
                    doc = Jsoup.connect(uri).timeout(10000).get();
                    assert doc!=null;
                    Element content=doc.select("div.pure-g").last();
//                    Log.i("tag", "run: ================="+content.html());
                    Elements titles=content.select("li a");
                    Log.i("tag", "run: =============titles:"+titles.size()+"==="+titles);
                    for (int i = 0; i <titles.size() ; i++) {
                        LocalCartoonType localCartoonType =new LocalCartoonType();
                        String t=titles.get(i).attr("title");
                        String address=titles.get(i).attr("href");
                        //分割字符
                        t=t.substring(t.indexOf("院")+1);
                        if (t.contains("第")){
                            t=t.substring(t.indexOf("第")+1,t.length());
                        }
//                        if (t.contains("话")){
//                            t=t.substring(0,t.indexOf("话"));
//                        }
                        Log.i("tag", "run: ==========title:"+t);

                        localCartoonType.setTitle(t);
                        localCartoonType.setAddrss(uri+address);
                        data.add(localCartoonType);
                    }
                    callback.getData(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getImgAddress( String url, final onHeroAddressCallback callback) {
        urlContent=url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                boolean isContinue=true;
                Document doc= null;
                while (isContinue){
                    try {
                        doc = Jsoup.connect(urlContent + "index_" + i + ".html").timeout(30*1000).get();
                        String content = doc.select("script[type*=text/javascript]").last().toString();
//                    Log.i("tag", "getImgAdress: ========"+content);
                        String[] contents = content.split("var");
                        String address = "";
                        for (String s : contents) {
                            if (s.contains("mhurl = \"")) {
                                String[] ss = s.split("\"");
                                address = ss[1];
                                break;
                            }
                        }
                        imgUrls[i] = "http://p1.xiaoshidi.net/" + address;
                        Log.i("tag", "getImgAdress======"+i+"====" + imgUrls[i]);
                        i=i+1;
                    } catch (IOException e) {
                        isContinue=false;
                        e.printStackTrace();
                    }
                }
                urls=new String[i];
                for (int j = 0; j < i; j++) {
                    urls[j]=imgUrls[j];
                }
                callback.getAddress(urls);
            }
        }).start();


    }
    public interface onHeroAddressCallback{
        void getAddress(String[] address);
    }
    public interface onHeroDataCallback{
        void getData(List<LocalCartoonType> data);
    }
}
