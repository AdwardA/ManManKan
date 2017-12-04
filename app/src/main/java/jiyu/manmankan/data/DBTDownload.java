package jiyu.manmankan.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.UUID;

/**
 * Created by z on 2017/11/28.
 */

@Table(database = DBManManKan.class)
public class DBTDownload extends DBBaseDate{
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String cartoonName;
    @Unique
    @Column
    String contentName;
    @Column
    int cartoonNum;
    @Column
    String imgAdress;
    @Column(defaultValue = "false")
    boolean isDone;


    public DBTDownload() {
    }

    public DBTDownload( String cartoonName, String contentName, int cartoonNum,String[] imgAdress) {
        this.cartoonName = cartoonName;
        this.contentName = contentName;
        this.cartoonNum = cartoonNum;
        for (int i = 0; i <imgAdress.length ; i++) {
            if (i==0){
                this.imgAdress=imgAdress[0];
            }else {
                this.imgAdress+=","+imgAdress[i];
            }
        }
    }

    public static DBTDownload getByContentName(String contentName){
        return SQLite.select().from(DBTDownload.class)
                .where(DBTDownload_Table.contentName.eq(contentName))
                .querySingle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getCartoonNum() {
        return cartoonNum;
    }

    public void setCartoonNum(int cartoonNum) {
        this.cartoonNum = cartoonNum;
    }

    public String getImgAdress() {
        return imgAdress;
    }

    public void setImgAdress(String imgAdress) {
        this.imgAdress = imgAdress;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
