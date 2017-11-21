package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareBaseModel {
    private List<WareBase> wareList;
    private String error;

    public List<WareBase> getWareList() {
        return wareList;
    }

    public void setWareList(List<WareBase> wareList) {
        this.wareList = wareList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class WareBase{
    private String id;
    private String price;
    private String remark;
    private String name;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}


}
