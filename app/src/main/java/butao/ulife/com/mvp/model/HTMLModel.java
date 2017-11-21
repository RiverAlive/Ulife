package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HTMLModel {
    private List<HTML> wares;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<HTML> getWares() {
        return wares;
    }

    public void setWares(List<HTML> wares) {
        this.wares = wares;
    }

    public class HTML {
        private String id;
        private String likeCheck;
        private String title;
        private String path;
        private String  miaoshu;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
        }
    }
}
