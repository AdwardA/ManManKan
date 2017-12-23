package jiyu.manmankan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;

import java.util.Date;

import jiyu.manmankan.data.DBTDownload;
import jiyu.manmankan.data.DBTDownload_Table;
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
        final CartoonType cartoonType= (CartoonType) intent.getSerializableExtra("CartoonType");
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
                            //把下载任务写入数据库
                            DBTDownload dbtDownload= new DBTDownload(cartoonType.getName(),titles[finalI],address.length,address);
                            DBTDownload dbtDownload1= SQLite.select()
                                    .from(DBTDownload.class)
                                    .where(DBTDownload_Table.contentName.eq(titles[finalI]))
                                    .querySingle();
                            if (dbtDownload1==null){
                                dbtDownload.insert();
                            }else {
                                dbtDownload.update();
                            }
                            //开始下载
                            new DownloadThread(getApplicationContext(),pathCartoon +"/",titles[finalI],address).start();
                        }
                    });
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
