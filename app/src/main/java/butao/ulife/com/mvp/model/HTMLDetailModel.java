package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLDetailModel {
    private HTMLDetail wareInfo;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HTMLDetail getWareInfo() {
        return wareInfo;
    }

    public void setWareInfo(HTMLDetail wareInfo) {
        this.wareInfo = wareInfo;
    }

    public class HTMLDetail {
        private String id;
        private String title;
        private String         price;
        private String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}
