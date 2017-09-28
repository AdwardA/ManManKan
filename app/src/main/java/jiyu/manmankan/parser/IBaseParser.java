package jiyu.manmankan.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jiyu.manmankan.entity.LocalCartoonType;

/**
 * Created by ti on 2017/9/27.
 */

public abstract class IBaseParser {
    private String[] imgUrls = new String[100];
    List<LocalCartoonType> data=new ArrayList<LocalCartoonType>();

    public abstract void getContentChild(onContentCallBack contentCallback);
    //爬取漫画章节
    public void getContent(final String uri, final onContentCallBack contentCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    //联网获取对应的网址的源码
                    doc = Jsoup.connect(uri).timeout(10000).get();
                    assert doc != null;
                    //提取漫画章节标题
                    Elements titles = getContentTitles(doc);
                    Log.i("tag", "run: =============titles:\n" + titles.size() + "===" + titles);
                    //------对漫画标题进行处理和赋值
                    for (int i = 0; i < titles.size(); i++) {
                        LocalCartoonType cartoonType = new LocalCartoonType();
                        String s=getContentTitleAddress(titles.get(i));
                        String[] ss=s.split("-");
                        cartoonType.setTitle(ss[0]);
                        cartoonType.setAddrss(uri + ss[1]);
                        data.add(cartoonType);
                    }
                    contentCallBack.getContent(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    abstract Elements getContentTitles(Document doc);

    abstract String getContentTitleAddress(Element title);

    /**
     * 用于处理标题和地址的属性为title和href的元素
     * @param title
     * @return
     */
    protected Map<String,String> getNormalSolveTitleAndAddress(Element title){
        Map<String,String> map=new HashMap<>();
        String t=title.attr("title");
        String address=title.attr("href");
        map.put("title",t);
        map.put("address",address);
        return map;
    }

    /**
     * ===================================================================================================================================================
     * 在网页代码中寻找正确的图片地址
     * @param urlContent
     * @param callBack
     */
    abstract void getImgAddressChild(String urlContent,onAddressCallBack callBack);

    public void getImgAddress(final String urlContent, final onAddressCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                boolean isContinue=true;
                Document doc= null;
                while (isContinue){
                    try {
                        doc = Jsoup.connect(urlContent + "index_" + i + ".html").timeout(20*1000).get();
                        assert doc!=null;
                        imgUrls[i] = findImgAddress(doc);
                        Log.i("tag", "getImgAdress======"+i+"====" + imgUrls[i]);
                        i=i+1;
                    } catch (IOException e) {
                        isContinue=false;
                        e.printStackTrace();
                    }
                }
                //根据现有的图片数，对图片地址重新赋值
                i=i-1;
                String[] urls=new String[i];
                System.arraycopy(imgUrls, 0, urls, 0, i);
                callBack.getAddress(urls);
            }
        }).start();
    }

    abstract String findImgAddress(Document doc);


    public interface onContentCallBack {
        void getContent(List<LocalCartoonType> data);
    }

    public interface onAddressCallBack {
        void getAddress(String[] address);
    }


}

