package butao.ulife.com.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/10/16.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ShopRemarkModel {
    private List<String > baseNotes;
    private List<String > historyNotes;

    public List<String> getBaseNotes() {
        return baseNotes;
    }

    public void setBaseNotes(List<String> baseNotes) {
        this.baseNotes = baseNotes;
    }

    public List<String> getHistoryNotes() {
        return historyNotes;
    }

    public void setHistoryNotes(List<String> historyNotes) {
        this.historyNotes = historyNotes;
    }
}
