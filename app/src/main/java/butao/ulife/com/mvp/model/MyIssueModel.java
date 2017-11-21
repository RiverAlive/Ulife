package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/22.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyIssueModel {
    public List<MyIssue> myWares;
    private String error;

    public List<MyIssue> getMyWares() {
        return myWares;
    }

    public void setMyWares(List<MyIssue> myWares) {
        this.myWares = myWares;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class MyIssue{
       private String uid;
        private String  id	;
        private String  phone;
        private String  title;
        private String address;
        private String  zipcode;
private String path;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
