package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * Created by lingzhen on 15/12/25.
 */
public class ProtocolParserContext extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 解析阶段
     */
    private ProtocolParsePhase phase;

    /**
     * 当前行
     */
    private XSSFRow row;

    /**
     * 解析协议
     */
    private Protocol protocol;

    private String getCellString() {
        return row.getCell(0).getStringCellValue();
    }

    public ProtocolParsePhase getPhase() {
        return phase;
    }

    public void setPhase(ProtocolParsePhase phase) {
        this.phase = phase;
    }

    public XSSFRow getRow() {
        return row;
    }

    public void setRow(XSSFRow row) {
        this.row = row;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
