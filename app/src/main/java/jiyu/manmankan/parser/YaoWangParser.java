package jiyu.manmankan.parser;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Map;


/**
 * Created by ti on 2017/9/27.
 */

public class YaoWangParser extends IBaseParser {
    String url="http://manhua.fzdm.com/058/";

    @Override
    public void getContentChild(onContentCallBack contentCallBack) {
        super.getContent(url,contentCallBack);
    }

    @Override
    Elements getContentTitles(Document doc) {
        Element content=doc.select("div.pure-g").last();
        return content.select("li a");
    }

    @Override
    String getContentTitleAddress(Element title) {
        Map<String,String> map=super.getNormalSolveTitleAndAddress(title);
        String t=map.get("title");
        t=t.substring(t.indexOf("灵")+1);
        String address=map.get("address");
        return t+"-"+address;
    }

    @Override
    public void getImgAddressChild(String urlContent, onAddressCallBack callBack) {
        super.getImgAddress(urlContent,callBack);
    }

    @Override
    String findImgAddress(Document doc) {
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
