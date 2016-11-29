package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.io.util.FileUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by lingzhen on 15/12/24.
 */
public class DtoGenerater {

    private static final boolean WRITE_FILE = true;

    private static final String SAMPLE_TITLE = "示例";

    private static Template template;

    static {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.ENCODING_DEFAULT, "UTF-8");
        ve.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
        ve.setProperty(VelocityEngine.OUTPUT_ENCODING, "UTF-8");
        ve.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        ve.init();

        template = (Template) ve.getTemplate("/com/uuban/toffee/coder/dtoclass.vm");
    }


    public static void generate() {
        try {
            String folderPath = getDocFolder();

            Collection<File> fileList = FileUtils.listFiles(new File(folderPath), new String[]{"xlsx"}, false);

            for (File file : fileList) {
                System.out.println("解析文件:" + file.getName());
                parse(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parse(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        try {
            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);

                if (!StringUtils.startsWith(sheet.getSheetName(), SAMPLE_TITLE)) {
                    parseSheet(file, sheet);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static void parseSheet(File file, XSSFSheet sheet) throws IOException {
        List<Protocol> protocolList = parseToProtocolList(file, sheet);

        for (Protocol each : protocolList) {
            write(each.getRequest());
            write(each.getResponse());
        }
    }


    private static void write(ProtocolClass clazz) throws IOException {
        // 构造模版数据上下文
        VelocityContext context = new VelocityContext();
        context.put("o", clazz);

        // 生成绑定数据后结果

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        String classStr = writer.toString();

        System.out.println("生成类:" + clazz.getName());
        System.out.println(classStr);

        if (WRITE_FILE) {
            File file = new File(getCodeFolder() + "/" + clazz.getPkg().replace(".", "/") + "/" + clazz.getName() + ".java");
            FileUtils.write(file, classStr, "UTF-8");
        }
    }


    private static List<Protocol> parseToProtocolList(File file, XSSFSheet sheet) {
        String pkg = getPackage(file);

        List<Protocol> protocolList = new ArrayList<Protocol>();
        Protocol protocol = null;
        ProtocolParserContext ctx = new ProtocolParserContext();

        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);

            if (row != null && row.getCell(0) != null) {
                String value = row.getCell(0).getStringCellValue();
                if (!StringUtils.isEmpty(value)) {
                    if (StringUtils.startsWith(value, "#") && StringUtils.endsWith(value, "#")) {
                        // 找到一个接口
                        if (protocol != null) {
                            protocolList.add(protocol);
                        }
                        protocol = new Protocol();
                        protocol.setPkg(pkg);
                        ctx.setPhase(ProtocolParsePhase.NAME);
                        ctx.setProtocol(protocol);
                    }

                    if (StringUtils.equals(value, "接口方法")) {
                        // 设置下一个阶段为接口方法
                        ctx.setPhase(ProtocolParsePhase.METHOD);
                        continue;
                    }

                    if (StringUtils.equals(value, "请求字段")) {
                        ctx.setPhase(ProtocolParsePhase.REQ_FIELD_TITLE);
                    }

                    if (StringUtils.equals(value, "响应字段")) {
                        ctx.setPhase(ProtocolParsePhase.RES_FIELD_TITLE);
                    }

                    if (StringUtils.startsWith(value, "类型_")) {
                        if (ProtocolParsePhase.REQ_FIELD_TITLE.equals(ctx.getPhase()) || ProtocolParsePhase.REQ_FIELD.equals(ctx.getPhase())) {
                            ctx.setPhase(ProtocolParsePhase.REQ_SUBFIELD_TITLE);
                        }
                        if (ProtocolParsePhase.RES_FIELD_TITLE.equals(ctx.getPhase()) || ProtocolParsePhase.RES_FIELD.equals(ctx.getPhase())) {
                            ctx.setPhase(ProtocolParsePhase.RES_SUBFIELD_TITLE);
                        }
                    }

                    if (StringUtils.equals(value, "名称")) {
                        // 类型说明栏不做任何解析
                        continue;
                    }

                    ctx.setRow(row);
                    ProtocolParser.parse(ctx);
                }

                continue;
            }
        }
        if (protocol != null) {
            protocolList.add(protocol);
        }

        return protocolList;
    }


    private static String getPackage(File file) {
        String p = "com.uuban.sweet." + file.getName().split("_")[0] + ".dto";
        return p;
    }

    private static String getDocFolder() {
        String docPath = DtoGenerater.class.getResource("/").getPath();

        String[] array = docPath.split("/");
        array = Arrays.copyOf(array, array.length - 3);

        StringBuilder sb = new StringBuilder();
        for (String each : array) {
            if (!StringUtils.isEmpty(each)) {
                sb.append("/");
                sb.append(each);
            }

        }
        sb.append("/doc/interface");

        return sb.toString();
    }

    private static String getCodeFolder() {
        String docPath = DtoGenerater.class.getResource("/").getPath();

        String[] array = docPath.split("/");
        array = Arrays.copyOf(array, array.length - 3);

        StringBuilder sb = new StringBuilder();
        for (String each : array) {
            if (!StringUtils.isEmpty(each)) {
                sb.append("/");
                sb.append(each);
            }

        }
        sb.append("/code/src/main/java");

        return sb.toString();
    }

    public static void main(String[] args) {
        generate();
    }

}