package butao.ulife.com.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopAddressModel {
   private List<AddressModel> list;

    public List<AddressModel> getList() {
        return list;
    }

    public void setList(List<AddressModel> list) {
        this.list = list;
    }

    public static class AddressModel{

           private String id;
            private String sex;
            private String phone;
            private String address;
            private String name;
            private String userId;
private String zipcode;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }
}
