package butao.ulife.com.mvp.model;

/**
 * 创建时间 ：2017/7/5.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class LoginModel {
    private String  isFirst;
    private SocialModel   socialLogin;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    public SocialModel getSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(SocialModel socialLogin) {
        this.socialLogin = socialLogin;
    }

    public class SocialModel{
        private String id;
        private String location;
        private String uid;
        private String accessToken;
        private String name;
        private String school;
        private String imgPath;
        private String phone;
        private String wxcode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWxcode() {
            return wxcode;
        }

        public void setWxcode(String wxcode) {
            this.wxcode = wxcode;
        }
    }

}
