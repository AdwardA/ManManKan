package jiyu.manmankan.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by ti on 2017/9/26.
 */

public class FileUtils {
    public static String  MAIN_NAME="manmankan";
    public static String  PATH_DOWNLOAD=MAIN_NAME+"/ic_download";
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
        //创建漫画下载目录
        creatDirectityInSdcardJiyu(PATH_DOWNLOAD);
        return b;
    }

    /**
     * 获取手机内部空间大小
     * @return
     */
    public static String getTotalInternalStorgeSize(){
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSizeLong();
        long totalBlocks = mStatFs.getBlockCountLong();
        //保留一位小数
        DecimalFormat df = new DecimalFormat("######0.0");
        double size=(totalBlocks * blockSize)/(1024*1024*1024d);
        return df.format(size)+"G";
    }
    public static long getTotalInternalStorgeSizeLong(){
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSizeLong();
        long totalBlocks = mStatFs.getBlockCountLong();
        return totalBlocks * blockSize;
    }
    /**
     * 获取手机内部可用空间大小
     * @return
     */
    public static String getAvailableInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSizeLong();
        long availableBlocks = mStatFs.getAvailableBlocksLong();
        //保留一位小数
        DecimalFormat df = new DecimalFormat("######0.0");
        double size=(availableBlocks * blockSize)/(1024*1024*1024d);
        if (size<1){
            size=(availableBlocks * blockSize)/(1024*1024d);
            return df.format(size)+"M";
        }
        return df.format(size)+"G";
    }
    public static long getAvailableInternalStorgeSizeLong() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSizeLong();
        long availableBlocks = mStatFs.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

}
