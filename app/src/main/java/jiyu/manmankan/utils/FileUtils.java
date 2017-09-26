package jiyu.manmankan.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ti on 2017/9/26.
 */

public class FileUtils {
    public static String  MAIN_NAME="manmankan";
    public static String  PATH_CACHE=MAIN_NAME+"/cache";
    public static String  PATH_CACHE_HEADER=PATH_CACHE+"/header";
    public static String  PATH_CACHE_CAETOONTYE=PATH_CACHE+"/cartoonType";

    public static void creatDirectityInSdcardJiyu(String pathName){
        File file=new File("/mnt/sdcard/jiyu/"+pathName);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static boolean setFoldersInit ()throws IOException{
        boolean b=false;
        //创建。nomedia文件；
        File nomedia=new File("/mnt/sdcard/jiyu/"+MAIN_NAME+"/.nomedia");
        if (!nomedia.exists()){
           b=nomedia.createNewFile();
        }
        //创建缓存目录
        FileUtils.creatDirectityInSdcardJiyu(PATH_CACHE);
        //--创建header文件夹；
        FileUtils.creatDirectityInSdcardJiyu(PATH_CACHE_HEADER);
        //--创建cartoonType文件夹，保存漫画预览图文件；
        FileUtils.creatDirectityInSdcardJiyu(PATH_CACHE_CAETOONTYE);
        return b;
    }

}
