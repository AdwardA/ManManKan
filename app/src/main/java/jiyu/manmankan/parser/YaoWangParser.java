package jiyu.manmankan.parser;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static cn.bmob.v3.BmobConstants.TAG;

/**
 * Created by ti on 2017/9/27.
 */

public class YaoWangParser extends IBaseParser {


    @Override
    public void getContentChild(onContentCallBack contentCallBack) {
        super.getContent("http://manhua.fzdm.com/058/",contentCallBack);
    }

    @Override
    Elements getContentTitles(Document doc) {
        Element content=doc.select("div.pure-g").last();
        Elements contents=content.select("li");
        Log.i("tag", "getContentTitles: \n"+contents.toString());
        return contents;
    }

    @Override
    String getContentTitleAddress(int i) {
        return null;
    }
}
