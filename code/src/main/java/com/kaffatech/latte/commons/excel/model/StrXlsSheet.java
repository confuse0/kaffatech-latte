package com.kaffatech.latte.commons.excel.model;

import com.kaffatech.latte.commons.bean.model.BaseBean;

import java.util.List;

/**
 * @author lingzhen on 16/9/10.
 */
public class StrXlsSheet extends BaseBean {

    private static final long serialVersionUID = 1L;

    private List<List<String>> recordList;

    public List<List<String>> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<List<String>> recordList) {
        this.recordList = recordList;
    }
}
