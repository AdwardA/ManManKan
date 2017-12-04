package jiyu.manmankan.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.entity.LocalCartoonType;
import jiyu.manmankan.parser.strategy.ParserStrategyManager;

/**
 * Created by ti on 2017/9/29.
 */

public  class ManManKanParser{
    private  CartoonType cartoonType;
    private BaseParser baseParser=new BaseParser();
    private static ManManKanParser manManKanParser=new ManManKanParser();
    private ParserStrategyManager strategyManager=new ParserStrategyManager();

    public static ManManKanParser builder(){
        return manManKanParser;
    }
    public BaseParser setCartoonType(CartoonType cartoonType) {
        this.cartoonType = cartoonType;
        //根据不同的网站来源使用不同的策略
        strategyManager.setStrategy(cartoonType.getOrigin());
        return this.baseParser;
    }


    public class BaseParser  extends IBaseParser{
        List<LocalCartoonType> contentData;

        public void getContent(onContentCallBack callBack) {
            super.getContent(cartoonType,callBack);
        }
        public void getImgAddress(String urlContent,onAddressCallBack callBack){
            super.getImgAddress(urlContent,callBack);
        }


        @Override//获取漫画章节的标题标签
        Elements getContentTitles(Document doc) {
            return strategyManager.getContentTitles(doc);
        }

        @Override//获取漫画标题和地址，中间用“-”分分隔
        String getContentTitleAddress(Element title) {
            return strategyManager.getContentTitleAddress(title);
        }

        @Override//获取图片的地址
        String findImgAddress(Document doc) {
            return strategyManager.findImgAddress(doc);
        }
    }

}
