package com.kaffatech.latte.commons.excel.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/9/10.
 */
public class TitledStrXlsSheet extends BaseBean {

    private static final long serialVersionUID = 1L;

    private List<Map<String, String>> recordList;

    public List<Map<String, String>> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Map<String, String>> recordList) {
        this.recordList = recordList;
    }
}
