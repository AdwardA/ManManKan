package jiyu.manmankan.parser.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by z on 2018/1/21.
 */

public class KuKuDMStrategy implements IBaseWebSiteStrategy {
    @Override
    public Elements getContentTitles(Document doc) {
        Element content=doc.getElementById("comiclistn");
        return content.select("dd").select("a").prev().prev().prev();
    }

    @Override
    public String getContentTitleAddress(String serverAddress,Element title) {
        String t=title.text();
        String address="http://comic.kukudm.com"+title.attr("href");
        return t+"-"+address;
    }

    @Override
    public String setNextPageStrategy(String urlContent, int i) {
        return urlContent.replace("1.htm",i+1+".htm");
    }

    @Override
    public String findImgAddress(Document doc) {
        String s=doc.body().select("script").before("<img src").toString();
        //String[] ss=s.split("<img src='\"+m201304d+\"");
        s=s.substring(s.indexOf("newkuku"),s.indexOf("jpg")+3);
        return "http://n.1whour.com/"+s;
    }
}
