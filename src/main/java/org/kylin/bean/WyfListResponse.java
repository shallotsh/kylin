package org.kylin.bean;

import java.util.List;

/**
 * @author huangyawu
 * @date 2017/6/25 下午3:24.
 */
public class WyfListResponse {

    private ListData data;

    public WyfListResponse(Integer total, List<?> items) {
        this.data = new ListData(total, items);
    }

    public ListData getData() {
        return data;
    }

    public void setData(ListData data) {
        this.data = data;
    }

    private class ListData{
        Integer total;
        List<?> items;

        public ListData(Integer total, List<?> items) {
            this.total = total;
            this.items = items;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }
}
