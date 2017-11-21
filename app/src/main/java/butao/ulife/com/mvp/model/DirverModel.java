package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/21.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class DirverModel {
    private List<Dirver> drivers;
    private String error;

    public List<Dirver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Dirver> drivers) {
        this.drivers = drivers;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class Dirver {
        private String uid;
        private String  id;
        private String name	;
        private String nationality;
        private String nameCode	;
        private String age;
        private String miaoshu;
        private String sex;
        private String phone;
        private String passportNo;
        private String drivingNo;
        private String path;
        private String userId;
        private String sortFlag ;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getNameCode() {
            return nameCode;
        }

        public void setNameCode(String nameCode) {
            this.nameCode = nameCode;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
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

        public String getPassportNo() {
            return passportNo;
        }

        public void setPassportNo(String passportNo) {
            this.passportNo = passportNo;
        }

        public String getDrivingNo() {
            return drivingNo;
        }

        public void setDrivingNo(String drivingNo) {
            this.drivingNo = drivingNo;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSortFlag() {
            return sortFlag;
        }

        public void setSortFlag(String sortFlag) {
            this.sortFlag = sortFlag;
        }
    }
}
