package jiyu.manmankan.parser.strategy;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by ti on 2017/9/29.
 */

public class ParserStrategyManager implements IBaseWebSiteStrategy {
    private IBaseWebSiteStrategy strategy;
    private FengZhiDongManStrategy fengZhiDongManStrategy=new FengZhiDongManStrategy();
    private KuKuDMStrategy kuKuDMStrategy=new KuKuDMStrategy();
    public void setStrategy(String origin) {
        switch (origin){
            case "风之动漫":
                strategy=fengZhiDongManStrategy;
                break;
            case "kuku动漫":
                strategy=kuKuDMStrategy;
                break;
        }

    }

    @Override
    public Elements getContentTitles(Document doc) {
        return strategy.getContentTitles(doc);
    }

    @Override
    public String getContentTitleAddress(String serverAddress,Element title) {
        return strategy.getContentTitleAddress(serverAddress, title);
    }

    @Override
    public String setNextPageStrategy(String urlContent, int i) {
        return strategy.setNextPageStrategy(urlContent,i);
    }

    @Override
    public String findImgAddress(Document doc) {
        return strategy.findImgAddress(doc);
    }
}
