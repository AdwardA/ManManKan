package jiyu.manmankan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import jiyu.manmankan.entity.CartoonType;
import jiyu.manmankan.parser.IBaseParser;
import jiyu.manmankan.parser.ManManKanParser;
import jiyu.manmankan.utils.FileUtils;
import jiyu.manmankan.utils.ToastUtils;

public class DownloadService extends Service {
    private boolean isStart=false;
    private boolean isPause=false;
    private boolean isDelect=false;
    private String pathCartoon;

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String[] titles=intent.getStringArrayExtra("title");
        String[] contentUrls=intent.getStringArrayExtra("url");
        CartoonType cartoonType= (CartoonType) intent.getSerializableExtra("CartoonType");
        ToastUtils.shortToast(this,"成功添加"+titles.length+"个下载任务");
        pathCartoon = FileUtils.PATH_DOWNLOAD+"/"+cartoonType.getName();
        FileUtils.creatDirectityInSdcardJiyu(pathCartoon);
        for (int i = 0; i <titles.length ; i++) {
            //开始解析地址
            final int finalI = i;
            ManManKanParser.builder()
                    .setCartoonType(cartoonType)
                    .getImgAddress(contentUrls[i], new IBaseParser.onAddressCallBack() {
                        @Override
                        public void getAddress(String[] address) {
                            //开始下载
                            DownloadThread downloadThread=new DownloadThread(pathCartoon +"/"+titles[finalI],address);
                            downloadThread.start();
                        }
                    });
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
