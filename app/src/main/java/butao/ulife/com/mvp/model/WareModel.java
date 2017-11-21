package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/12.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareModel {
    private List<Ware> wares;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public List<Ware> getWares() {
        return wares;
    }

    public void setWares(List<Ware> wares) {
        this.wares = wares;
    }

    public class Ware{
        private String    uid;
        private String           id;
        private String    phone;
        private String    title;
        private String path;
        private String likeCheck;
        private String miaoshu;
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

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
        }
    }

}
