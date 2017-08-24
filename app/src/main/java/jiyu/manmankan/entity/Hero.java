package jiyu.manmankan.entity;

import java.io.Serializable;

/**
 * Created by z on 2017/8/17.
 */

public class Hero implements Serializable {
    private String title;
    private String addrss;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddrss() {
        return addrss;
    }

    public void setAddrss(String addrss) {
        this.addrss = addrss;
    }
}
