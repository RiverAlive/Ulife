package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/22.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class GoodLikeModel {
    private List<GoodLike> list;
    private String error;

    public List<GoodLike> getList() {
        return list;
    }

    public void setList(List<GoodLike> list) {
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class GoodLike{
        private String id;
        private String wareId;
        private String cid;
        private String userId;
        private String createTime;
        private String  remark;
        private String  path;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
