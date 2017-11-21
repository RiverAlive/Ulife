package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/23.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DinerColesModel {
 private List<DinerColes> wares;
 private String error;

 public List<DinerColes> getWares() {
  return wares;
 }

 public void setWares(List<DinerColes> wares) {
  this.wares = wares;
 }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class DinerColes {
     private String uid;
     private String id;
     private String likeCheck;
     private String phone;
     private String title;
     private String address;
     private String zipcode;
     private String path;
     private String miaoshu;
     private String socnum;

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

     public String getLikeCheck() {
      return likeCheck;
     }

     public void setLikeCheck(String likeCheck) {
      this.likeCheck = likeCheck;
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

     public String getSocnum() {
      return socnum;
     }

     public void setSocnum(String socnum) {
      this.socnum = socnum;
     }
    }
}
