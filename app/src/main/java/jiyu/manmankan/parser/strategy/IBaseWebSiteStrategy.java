package jiyu.manmankan.parser.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by ti on 2017/9/29.
 */

public interface IBaseWebSiteStrategy {
    Elements getContentTitles(Document doc);
    String getContentTitleAddress(String serverAddress,Element title);
    String setNextPageStrategy(String urlContent, int i);
    String findImgAddress(Document doc);
}
