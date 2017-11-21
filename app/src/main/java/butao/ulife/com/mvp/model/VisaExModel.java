package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/18.
 * 编写人 ：bodong
 * 功能描述 ：签证升学，全国快递
 */
public class VisaExModel {
    private List<VisaEx> wares;
    private String error;

    public List<VisaEx> getWares() {
        return wares;
    }

    public void setWares(List<VisaEx> wares) {
        this.wares = wares;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class VisaEx{
        private String id;
        private String phone;
        private String title;
        private String  address;
        private String zipcode;
        private String path;
        private String miaoshu;

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

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
        }
    }
}
