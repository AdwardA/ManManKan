package jiyu.manmankan.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by z on 2017/12/3.
 */

public class DBBaseDate extends BaseModel {
    @Column
    Date createDate;

    @Column
    Date updateDate;

    public DBBaseDate() {
    }

    @Override
    public long insert() {
        this.createDate=new Date();
        this.updateDate=new Date();
        return super.insert();
    }

    @Override
    public boolean update() {
        this.updateDate=new Date();
        return super.update();
    }
    

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
