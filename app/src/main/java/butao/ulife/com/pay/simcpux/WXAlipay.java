package butao.ulife.com.pay.simcpux;


import com.google.gson.annotations.SerializedName;

/**
 * Created by bodong on 2016/5/9.
 */

public class WXAlipay {


    private WXPay payInfo;

private String error;

    public WXPay getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(WXPay payInfo) {
        this.payInfo = payInfo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public  class WXPay{
        private String appid;
        private String  noncestr;
        @SerializedName("package")
        private String packageValue;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String retmsg;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getRetmsg() {
            return retmsg;
        }

        public void setRetmsg(String retmsg) {
            this.retmsg = retmsg;
        }
    }
}
