package jiyu.manmankan.entity;

import java.io.Serializable;

import jiyu.manmankan.utils.FileUtils;

/**
 * Created by z on 2017/8/17.
 */

public class LocalCartoonType implements Serializable {
    private String title;
    private String addrss;
    private String name;
    private boolean isDownloaded;
    private String localPath= FileUtils.PATH_DOWNLOAD+"/"+name+"/"+title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public String getLocalPath() {
        return localPath;
    }
}
