package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/21.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MessgaeModel {
    private List<MessageItem> list;
    private String error;

    public List<MessageItem> getList() {
        return list;
    }

    public void setList(List<MessageItem> list) {
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class MessageItem{

        private String id;
        private String   remark;
        private String userId;
        private String   createTime;
        private String  wareId;
        private SocialLogin  socialLogin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }

        public SocialLogin getSocialLogin() {
            return socialLogin;
        }

        public void setSocialLogin(SocialLogin socialLogin) {
            this.socialLogin = socialLogin;
        }

        public class SocialLogin{
            private String id;
            private String location;
            private String uid;
            private String  accessToken;
            private String name;
            private String  school;
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
}
