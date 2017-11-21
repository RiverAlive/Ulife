package butao.ulife.com.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/7/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CitiFXModel {

        /**
         * list : [["美元","100","675.3500","669.9300","678.2600","676.7000","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT5SAUEkVAABoD0Nr4dU827.jpg"],["日元","100","5.9879","5.7985","6.0359","6.0119","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT56AcVRiAAAeeyauzgc934.jpg"],["欧元","100","772.8700","748.4200","779.0700","775.9700","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT6WAJ4ofAAAh5QsBo_Y751.jpg"],["英镑","100","883.1300","855.2000","890.2300","886.6800","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT62AUurcAAA7hG9i38E747.jpg"],["澳大利亚元","100","527.4000","510.7200","531.6400","529.5200","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT7iAI3tjAABQSgm0AKA449.jpg"],["加拿大元","100","532.8900","516.0400","537.1700","535.0300","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT8GAHWGUAAAiDZV6zto888.jpg"],["瑞士法郎","100","699.3100","677.1900","704.9300","702.1200","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnUH6AYUoEAAAWGDIAJno510.jpg"],["港币","100","86.5300","85.9200","86.8700","86.7000","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnT8uAL0OyAAAkHmLB7qE089.jpg"],["新西兰元","100","495.0500","479.4000","499.0300","497.0400","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnUHKAIFrJAAAwuzIYpEA355.jpg"],["新加坡元","100","492.0000","476.4400","495.9600","493.9800","http://139.129.41.199:8088/group1/M00/00/08/i4Epx1jnUAiAGYYUAAAgHj2-akk176.jpg"]]
         * update : 2017-07-17 09:43:18
         */

        private CurrencyBean currency;

    private String error;
        public CurrencyBean getCurrency() {
            return currency;
        }

        public void setCurrency(CurrencyBean currency) {
            this.currency = currency;
        }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class CurrencyBean {
            private String update;
            private List<List<String>> list;

            public String getUpdate() {
                return update;
            }

            public void setUpdate(String update) {
                this.update = update;
            }

            public List<List<String>> getList() {
                return list;
            }

            public void setList(List<List<String>> list) {
                this.list = list;
            }
        }

}
