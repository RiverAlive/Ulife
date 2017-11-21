package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareDetailModel {
    private WareDetail purchaseInfo;
    private String error;

    public WareDetail getPurchaseInfo() {
        return purchaseInfo;
    }

    public void setPurchaseInfo(WareDetail purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class WareDetail{
        private String id;
        private String name;
        private String remark;
        private String price;
        private String wareNum;
        private String likes;
        private String cid;
        private String createTime;
        private String imgPath;
        private String wareId;
        private String userId;
        private String work;
        private String userPath;
        private String uid;
        private String sortFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getWareNum() {
            return wareNum;
        }

        public void setWareNum(String wareNum) {
            this.wareNum = wareNum;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getUserPath() {
            return userPath;
        }

        public void setUserPath(String userPath) {
            this.userPath = userPath;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSortFlag() {
            return sortFlag;
        }

        public void setSortFlag(String sortFlag) {
            this.sortFlag = sortFlag;
        }
    }

}
