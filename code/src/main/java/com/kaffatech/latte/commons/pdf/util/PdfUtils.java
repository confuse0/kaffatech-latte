package com.kaffatech.latte.commons.pdf.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.io.util.FileUtils;

import java.io.*;
import java.util.Map;

/**
 * @author zhen.ling on 16/12/7.
 */
public class PdfUtils {

    /**
     * 创建PDF文档
     *
     * @param templatePath
     * @param data
     * @param filePath
     */
    public static void createPdf(String templatePath, Map<String, String> data, String filePath) {
        try {
            createPdf(new PdfReader(templatePath), data, filePath);
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 创建PDF文档
     *
     * @param templateInputStream
     * @param data
     * @param filePath
     */
    public static void createPdf(InputStream templateInputStream, Map<String, String> data, String filePath) {
        try {
            createPdf(new PdfReader(templateInputStream), data, filePath);
        } catch (Exception e) {
            throw new IoRuntimeException(e);
        }
    }

    private static void createPdf(PdfReader templateReader, Map<String, String> data, String filePath) throws DocumentException, IOException {
        File file = new File(FileUtils.getFolder(filePath));
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }

        // 读取模版
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(templateReader, bos);
        try {
            AcroFields fields = ps.getAcroFields();
            for (String key : data.keySet()) {
                String value = data.get(key);
                fields.setField(key, value);
            }
            ps.setFormFlattening(true);
        } finally {
            ps.close();
        }

        // 写文件
        FileOutputStream fos = new FileOutputStream(filePath);
        try {
            fos.write(bos.toByteArray());
        } finally {
            fos.close();
        }
    }

}
