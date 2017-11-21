package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/14.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareInfoModel {
    private WareInfo wareInfo;
    private String error;

    public WareInfo getWareInfo() {
        return wareInfo;
    }

    public void setWareInfo(WareInfo wareInfo) {
        this.wareInfo = wareInfo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class WareInfo{
        private String   uid;
        private String           imgs;
        private String    phone;
        private String    remark;
        private String           goodCheck;
        private String    cid;
        private String    id;
        private String    likeCheck;
        private String    title;
        private List<path> paths;
        private String     price;
        private String    path;
        private String    wareNum;
        private String   zipcode;
        private String   socnum;
        private String   wxcode;
        private String   address;
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getGoodCheck() {
            return goodCheck;
        }

        public void setGoodCheck(String goodCheck) {
            this.goodCheck = goodCheck;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLikeCheck() {
            return likeCheck;
        }

        public void setLikeCheck(String likeCheck) {
            this.likeCheck = likeCheck;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<WareInfo.path> getPaths() {
            return paths;
        }

        public void setPaths(List<WareInfo.path> paths) {
            this.paths = paths;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getWareNum() {
            return wareNum;
        }

        public void setWareNum(String wareNum) {
            this.wareNum = wareNum;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getSocnum() {
            return socnum;
        }

        public void setSocnum(String socnum) {
            this.socnum = socnum;
        }

        public String getWxcode() {
            return wxcode;
        }

        public void setWxcode(String wxcode) {
            this.wxcode = wxcode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public  class path{
            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
