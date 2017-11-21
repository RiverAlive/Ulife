package butao.ulife.com.mvp.model;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeImgModel {
    private HomeImg info;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HomeImg getInfo() {
        return info;
    }

    public void setInfo(HomeImg info) {
        this.info = info;
    }

    public class HomeImg {
       private String  id;
        private String type;
        private String  content	;
        private String mainImg	;
        private String tempId;
        private String createTime;
        private String title;
        private String sortFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public String getTempId() {
            return tempId;
        }

        public void setTempId(String tempId) {
            this.tempId = tempId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSortFlag() {
            return sortFlag;
        }

        public void setSortFlag(String sortFlag) {
            this.sortFlag = sortFlag;
        }
    }
}
