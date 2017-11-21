package butao.ulife.com.mvp.model;

/**
 * 创建时间 ：2017/8/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WelcomeModel {
    private String code;

    private String expires;

    private WelomeData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public WelomeData getData() {
        return data;
    }

    public void setData(WelomeData data) {
        this.data = data;
    }

    public class WelomeData{
        public Welcome info;
        public Welcome getInfo() {
            return info;
        }

        public void setInfo(Welcome info) {
            this.info = info;
        }
    }


    public static class Welcome{
        private String id;
        private String name;
        private String path;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
