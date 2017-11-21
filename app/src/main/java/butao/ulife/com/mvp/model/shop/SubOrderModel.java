package butao.ulife.com.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：1.actualPrice:实际支付
 2.totalPriceInit：原价
 3.address：配送地址
 4.userPhone:用户手机号，预留号码
 5.discountNote：优惠描述
 6.discountPrice：优惠金额
 7.note：用户备注
 8.rate：汇率
 9.RMBPrice：人民币支付金额
 10.storeId：门店ID
 11.userId：用户ID
 12.orderWareForms:商品集合
 13.wareId：商品ID
 14.warePrice：原价
 15.actualPrice：实际价格（无优惠的话 和原价相等）
 16.wareName：商品名称
 17.wareNum：数量

 */

public class SubOrderModel {
    private String actualPrice;
    private String totalPriceInit;
    private String address;
    private String discountNote;
    private String discountPrice;
    private String note;
    private String rate;
    private String RMBPrice;
    private String storeId;
    private String userId;
    private String userPhone;
    private String  packPrice;
    private String   sendNote;
    private String zipcode;
    private List<OrderWare> orderWareForms;

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getTotalPriceInit() {
        return totalPriceInit;
    }

    public void setTotalPriceInit(String totalPriceInit) {
        this.totalPriceInit = totalPriceInit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscountNote() {
        return discountNote;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRMBPrice() {
        return RMBPrice;
    }

    public void setRMBPrice(String RMBPrice) {
        this.RMBPrice = RMBPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<OrderWare> getOrderWareForms() {
        return orderWareForms;
    }

    public void setOrderWareForms(List<OrderWare> orderWareForms) {
        this.orderWareForms = orderWareForms;
    }

    public String getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(String packPrice) {
        this.packPrice = packPrice;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }

    public static class OrderWare{
        private String wareId;
        private String warePrice;
        private String actualPrice;
        private String wareName;
        private String wareNum;
        private String ImgUrl;
        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }

        public String getWarePrice() {
            return warePrice;
        }

        public void setWarePrice(String warePrice) {
            this.warePrice = warePrice;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getWareName() {
            return wareName;
        }

        public void setWareName(String wareName) {
            this.wareName = wareName;
        }

        public String getWareNum() {
            return wareNum;
        }

        public void setWareNum(String wareNum) {
            this.wareNum = wareNum;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String imgUrl) {
            ImgUrl = imgUrl;
        }
    }
}
