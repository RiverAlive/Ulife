package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：签证升学，全国快递
 */
public class VisaExDetailModel {
    private VisaExDetail wareInfo;
    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public VisaExDetail getWareInfo() {
        return wareInfo;
    }

    public void setWareInfo(VisaExDetail wareInfo) {
        this.wareInfo = wareInfo;
    }

    public class VisaExDetail{
        private String  uid;
        private String id;
        private String imgs;
        private String phone;
        private String title;
        private List<VisaImg> paths;
        private String  price;
        private String wxcode;
        private String remark;
        private String address;
        private String zipcode;
        private String  path;
        private String  cid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<VisaImg> getPaths() {
            return paths;
        }

        public void setPaths(List<VisaImg> paths) {
            this.paths = paths;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getWxcode() {
            return wxcode;
        }

        public void setWxcode(String wxcode) {
            this.wxcode = wxcode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public class VisaImg{
            private String  path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
