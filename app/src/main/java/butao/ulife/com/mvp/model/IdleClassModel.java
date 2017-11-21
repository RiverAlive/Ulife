package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/12.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class IdleClassModel {
    private List<IdleClass> categorys;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<IdleClass> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<IdleClass> categorys) {
        this.categorys = categorys;
    }

    public static class IdleClass{
        private String  level;
        private String  remark;
        private String  name;
        private String  cid;
        private String   mainImg;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }
    }

}
