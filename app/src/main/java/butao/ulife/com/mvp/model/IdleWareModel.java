package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/12.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IdleWareModel {
    private List<IdleWare> wares;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public List<IdleWare> getWares() {
        return wares;
    }

    public void setWares(List<IdleWare> wares) {
        this.wares = wares;
    }

    public class IdleWare{
        private String    uid;
        private String           id;
        private String    phone;
        private String    title;
        private String path;
        private String likeCheck;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getLikeCheck() {
            return likeCheck;
        }

        public void setLikeCheck(String likeCheck) {
            this.likeCheck = likeCheck;
        }
    }

}
