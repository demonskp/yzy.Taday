package yzy.today.Dao;

import android.media.Image;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yzy on 2016/9/17.
 */
@Entity
public class Event {
    @Id(autoincrement = false)
    private long ids;
    @Property(nameInDb = "ID")
    private long id;
    @Property(nameInDb = "DAY")
    private String day;
    @Property(nameInDb = "DES")
    private String des;  // 内容
    @Property(nameInDb = "LUNAR")
    private String lunar;   // 农历日期
    @Property(nameInDb = "MONTH")
    private String month;
    @Property(nameInDb = "PIC")
    private String pic;  // 图片
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "YEAR")
    private String year;
    @Generated(hash = 1005632839)
    public Event(long ids, long id, String day, String des, String lunar,
            String month, String pic, String title, String year) {
        this.ids = ids;
        this.id = id;
        this.day = day;
        this.des = des;
        this.lunar = lunar;
        this.month = month;
        this.pic = pic;
        this.title = title;
        this.year = year;
    }
    @Generated(hash = 344677835)
    public Event() {
    }
    public long getIds() {
        return this.ids;
    }
    public void setIds(long ids) {
        this.ids = ids;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDay() {
        return this.day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getDes() {
        return this.des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public String getLunar() {
        return this.lunar;
    }
    public void setLunar(String lunar) {
        this.lunar = lunar;
    }
    public String getMonth() {
        return this.month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ids=" + ids +
                ", id=" + id +
                ", day='" + day + '\'' +
                ", des='" + des + '\'' +
                ", lunar='" + lunar + '\'' +
                ", month='" + month + '\'' +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
