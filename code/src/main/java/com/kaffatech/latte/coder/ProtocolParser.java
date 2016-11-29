package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.base.math.NumberUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingzhen on 15/12/25.
 */
public class ProtocolParser {

    public static void parse(ProtocolParserContext context) {
        Protocol protocol = context.getProtocol();
        String value = getCellString(context);

        if (ProtocolParsePhase.NAME.equals(context.getPhase())) {
            // 接口名
            protocol.setName(StringUtils.substring(value, 1, value.length() - 1));

            // 设置下一个阶段为接口介绍
            context.setPhase(ProtocolParsePhase.DESP);

            return;
        }

        if (ProtocolParsePhase.DESP.equals(context.getPhase())) {
            // 接口介绍
            protocol.setDescription(value);

            // 设置下一个阶段为接口介绍
            context.setPhase(ProtocolParsePhase.METHOD);

            return;
        }

        if (ProtocolParsePhase.METHOD.equals(context.getPhase())) {
            // 接口方法
            protocol.setMethod(value);

            // 设置下一个阶段为接口方法
            context.setPhase(ProtocolParsePhase.FIELD);

            return;
        }

        if (ProtocolParsePhase.REQ_FIELD_TITLE.equals(context.getPhase())) {
            ProtocolClass clazz = new ProtocolClass();
            String clazzName = context.getProtocol().getMethod()+"ReqDto";
            clazzName = clazzName.substring(0, 1).toUpperCase() + clazzName.substring(1);
            clazz.setName(clazzName);
            clazz.setPkg(context.getProtocol().getPkg());
            protocol.setRequest(clazz);

            // 设置下一个阶段为请求类型
            context.setPhase(ProtocolParsePhase.REQ_FIELD);

            return;
        }

        if (ProtocolParsePhase.REQ_FIELD.equals(context.getPhase())) {
            addField(context, protocol.getRequest());

            return;
        }

        if (ProtocolParsePhase.REQ_SUBFIELD_TITLE.equals(context.getPhase())) {
            ProtocolClass clazz = protocol.getRequest();
            if (CollectionUtils.isEmpty(clazz.getSubClassList())) {
                clazz.setSubClassList(new ArrayList<ProtocolClass>());
            }
            ProtocolClass subClass = new ProtocolClass();
            subClass.setName(value.split("_")[1]);
            clazz.getSubClassList().add(subClass);

            // 设置下一个阶段为请求类型
            context.setPhase(ProtocolParsePhase.REQ_SUBFIELD);

            return;
        }

        if (ProtocolParsePhase.REQ_SUBFIELD.equals(context.getPhase())) {
            ProtocolClass clazz = protocol.getRequest();
            List<ProtocolClass> clazzList = clazz.getSubClassList();
            addField(context, clazzList.get(clazzList.size() - 1));

            return;
        }


        if (ProtocolParsePhase.RES_FIELD_TITLE.equals(context.getPhase())) {
            ProtocolClass clazz = new ProtocolClass();
            String clazzName = context.getProtocol().getMethod()+"ResDto";
            clazzName = clazzName.substring(0, 1).toUpperCase() + clazzName.substring(1);
            clazz.setName(clazzName);
            clazz.setPkg(context.getProtocol().getPkg());
            protocol.setResponse(clazz);

            // 设置下一个阶段为请求类型
            context.setPhase(ProtocolParsePhase.RES_FIELD);

            return;
        }

        if (ProtocolParsePhase.RES_FIELD.equals(context.getPhase())) {
            addField(context, protocol.getResponse());

            return;
        }

        if (ProtocolParsePhase.RES_SUBFIELD_TITLE.equals(context.getPhase())) {
            ProtocolClass clazz = protocol.getResponse();
            if (CollectionUtils.isEmpty(clazz.getSubClassList())) {
                clazz.setSubClassList(new ArrayList<ProtocolClass>());
            }
            ProtocolClass subClass = new ProtocolClass();
            subClass.setName(value.split("_")[1]);
            clazz.getSubClassList().add(subClass);

            // 设置下一个阶段为请求类型
            context.setPhase(ProtocolParsePhase.RES_SUBFIELD);

            return;
        }

        if (ProtocolParsePhase.RES_SUBFIELD.equals(context.getPhase())) {
            ProtocolClass clazz = protocol.getResponse();
            List<ProtocolClass> clazzList = clazz.getSubClassList();
            addField(context, clazzList.get(clazzList.size() - 1));

            return;
        }
    }

    private static void addField(ProtocolParserContext context, ProtocolClass clazz) {
        ProtocolField filed = getField(context);

        if (StringUtils.startsWith(filed.getType(), "List")) {
            // 如果是List类型的话
            clazz.addImport("java.util.List");
        }

        if (StringUtils.startsWith(filed.getType(), "Set")) {
            // 如果是Set类型的话
            clazz.addImport("java.util.Set");
        }

        if (StringUtils.startsWith(filed.getType(), "Map")) {
            // 如果是Map类型的话
            clazz.addImport("java.util.Map");
        }

        clazz.addField(filed);
    }

    private static ProtocolField getField(ProtocolParserContext context) {
        XSSFRow row = context.getRow();
        ProtocolField filed = null;
        if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
            filed = new ProtocolField();
            filed.setName(row.getCell(0).getStringCellValue());
            filed.setType(row.getCell(1).getStringCellValue());
            filed.setMust(row.getCell(2).getStringCellValue());


            XSSFCell sizeCell = row.getCell(3);
            switch (sizeCell.getCellType()) {
                case XSSFCell.CELL_TYPE_NUMERIC:
                    filed.setSize(String.valueOf(NumberUtils.down(row.getCell(3).getNumericCellValue())));
                    break;
                case XSSFCell.CELL_TYPE_STRING:
                    filed.setSize(row.getCell(3).getStringCellValue());
                    break;
                default:
                    break;
            }
            filed.setFormat(row.getCell(4).getStringCellValue());
            filed.setComment(row.getCell(5).getStringCellValue());
        }

        return filed;
    }

    private static String getCellString(ProtocolParserContext context) {
        XSSFRow row = context.getRow();
        if (row != null && row.getCell(0) != null) {
            return row.getCell(0).getStringCellValue();
        }
        return "";
    }
}
