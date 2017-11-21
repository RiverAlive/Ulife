package butao.ulife.com.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/29.
 * 编写人 ：bodong
 * 功能描述 ：1.orders:订单列表
 2.actualPrice：实际支付
 3.phone：手机号
 4.status：状态
 5.statusName：状态名称
 6.sendPrice：配送费
 7.discountNote：优惠介绍
 8.discountPrice：优惠金额
 9.orderTime：订单创建时间
 10.orderWares：商品集合
 11.imgUrl：商品图片
 12.actualPrice：商品价格
 13.wareName：商品名称
 14.wareNum：数量
 15.wareId：商品ID
 */

public class MyOrderModel {
    private List<OrderModel> orders;

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public static class OrderModel {
        private String actualPrice;
        private String phone;
        private String status;
        private String statusName;
        private String sendPrice;
        private String discountNote;
        private String discountPrice;
        private String orderTime;
        private List<OrderWareModel> orderWares;
        private String orderId;

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(String sendPrice) {
            this.sendPrice = sendPrice;
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

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public List<OrderWareModel> getOrderWares() {
            return orderWares;
        }

        public void setOrderWares(List<OrderWareModel> orderWares) {
            this.orderWares = orderWares;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
    public static class OrderWareModel{
       private String imgUrl;
        private String actualPrice;
        private String wareName;
        private String wareNum;
        private String wareId;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
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

        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }
    }
}
