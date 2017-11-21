package butao.ulife.com.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/7/23.
 * 编写人 ：bodong
 * 功能描述 ：5.status:状态1：正常营业，2：未审核，3：审核拒绝，4：暂停营业,	5:休息
 6.statusName：状态名称
 */
public class ShopListModel {
 private List<StoreModel> storeList;
 private String error;


 public List<StoreModel> getStoreList() {
  return storeList;
 }

 public void setStoreList(List<StoreModel> storeList) {
  this.storeList = storeList;
 }

 public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class StoreModel {

     private String imgUrl;
     private String storeName;
     private String storeId;
     private String status;
     private String statusName;
     public String getImgUrl() {
      return imgUrl;
     }

     public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
     }

     public String getStoreName() {
      return storeName;
     }

     public void setStoreName(String storeName) {
      this.storeName = storeName;
     }

     public String getStoreId() {
      return storeId;
     }

     public void setStoreId(String storeId) {
      this.storeId = storeId;
     }

     public String getStatus() {
      return status;
     }

     public void setStatus(String status) {
      this.status = status;
     }

     public String getStatusName() {
      return statusName;
     }

     public void setStatusName(String statusName) {
      this.statusName = statusName;
     }
    }
}
