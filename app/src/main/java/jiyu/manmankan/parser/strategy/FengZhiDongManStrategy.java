package jiyu.manmankan.parser.strategy;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by ti on 2017/9/29.
 */

public class FengZhiDongManStrategy implements IBaseWebSiteStrategy {
    @Override
    public Elements getContentTitles(Document doc) {
        Element content=doc.select("div.pure-g").last();
        return content.select("li a");
    }

    @Override
    public String getContentTitleAddress(String serverAddress,Element title) {
//        Log.i("tag", "getContentTitleAddress: ====="+title.toString());
        String t=title.attr("title");
        String address=serverAddress+title.attr("href");
        return t+"-"+address;
    }

    @Override
    public String setNextPageStrategy(String urlContent,int i) {
        return urlContent + "index_" + i + ".html";
    }


    @Override
    public String findImgAddress(Document doc) {
        String content= String.valueOf(doc.select("script[type*=text/javascript]").last());
        String[] contents = content.split("var");
        String address = "";
        for (String s : contents) {
            if (s.contains("mhurl = \"")) {
                String[] ss = s.split("\"");
                address ="http://p1.xiaoshidi.net/"+ ss[1];
                break;
            }
        }
        return address;
    }
}
