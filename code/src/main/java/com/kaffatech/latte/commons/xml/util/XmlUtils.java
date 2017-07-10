package com.kaffatech.latte.commons.xml.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA
 * User: Alan
 * Date: 17/7/5
 */
public class XmlUtils {
    private static String VERSION = "1.0";

    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj 任意对象
     * @return 字符串
     */

    public static String convertToXml(Object obj, String encoding) {
        // 创建输出流
        StringWriter sw = new StringWriter();

        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

            Marshaller marshaller = context.createMarshaller();
            //关闭默认xml头信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            // 重写xml头信息
            // 如果不重写xml头信息，可使用内置转换方法，并且可以格式化输出效果，重写后则不行。
            byteArrayOutputStream = new ByteArrayOutputStream();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(byteArrayOutputStream, encoding);
            xmlStreamWriter.writeStartDocument(encoding, VERSION);
            marshaller.marshal(obj, xmlStreamWriter);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            return new String(byteArrayOutputStream.toByteArray());
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj  待转对象
     * @param path 存储地址
     */
    public static void convertToXml(Object obj, String path, String encoding) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
}
