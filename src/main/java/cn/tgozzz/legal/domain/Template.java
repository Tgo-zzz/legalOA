package cn.tgozzz.legal.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

/**
 * 模板文件
 */
@Document(collection = "templates")
@Data
public class Template {

    @Id
    private String tid;
    private String name = "example";
    private String type = "docx";
    private String uri = "http://";
    private String owner = "system";
    private String modifier = "";
    private String group = "";
    private String info = "";
    private long createTime = new Date().getTime();
    private long updateTime = new Date().getTime();
    private String updateInfo = "";
    private int star = 0;
    private int apply = 0;
    private ArrayList<String> baseT = new ArrayList<>();

    /**
     * 添加修改备注时自动修改最近更新时间
     */
    public void setUpdateInfo(String info) {
        this.updateInfo = info;
        this.updateTime = new Date().getTime();
    }

    /**
     * 收藏标记+1
     */
    public void addStar() {
        this.star++;
    }

    /**
     * 使用标记+1
     */
    public void addApply() {
        this.apply++;
    }

    /**
     * 收藏标记-1
     */
    public void reduceStar() {
        this.star = Math.max(this.star - 1, 0);
    }

    /**
     * 通过覆盖进行更新
     */
    public void updateByCover(String oldTid, User user) {
        this.uri = "http://legal.tgozzz.cn/office/files/__ffff_127.0.0.1/" + this.tid;
        this.baseT.add(0, oldTid);
        this.modifier = user.getName();
        this.setUpdateInfo("用户" + user.getName() + "进行了覆盖更新");
    }

    /**
     * 通过新增版本进行更新
     */
    public void updateByExtend(String oldTid, User user, Template template) {
        this.uri = "http://legal.tgozzz.cn/office/files/__ffff_127.0.0.1/" + this.tid;
        this.baseT.clear();
        this.baseT.add(oldTid);
        this.owner = user.getName();
        this.modifier = "";
        this.createTime = new Date().getTime();
        this.apply = 0;
        this.star = 0;
        this.setUpdateInfo("用户" + user.getName() + "基于文件 " + template.getName() + " 创建此版本");
    }

    /**
     * 推测更新模式
     */
    public String updateMode() {
        return this.modifier.equals("") ? "extend" : "cover";
    }
}
