
package com.kaffatech.latte.search.seo.sitemap;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author zhen.ling
 *
 */
public class SiteMapGenerator {

    /**
     * @param args
     * @throws DocumentException
     * @throws
     */
    public static void main(String[] args) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File(
                "/alexhome/dev/pj/sweet/doc/sitemap.xml"));
        Element root = doc.getRootElement();

        Document newDoc = DocumentHelper.createDocument();
        Element newRoot = DocumentHelper.createElement("urlset");
        newDoc.setRootElement(newRoot);

        List<Element> elements = root.elements();
        for (Element each : elements) {
            // 获取url
            Element loc = each.element("loc");
            String url = loc.getText();
            if (!url.endsWith("?") && !url.contains("glb.clouddn.com")) {
                Element newUrl = DocumentHelper.createElement("url");
                newUrl.addElement("loc");
                newUrl.element("loc").setText(url);

                newRoot.add(newUrl);
            }
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(
                "/alexhome/dev/pj/sweet/doc/sitemap_n.xml"), format);
        writer.write(newDoc);
        writer.close();
    }
}
