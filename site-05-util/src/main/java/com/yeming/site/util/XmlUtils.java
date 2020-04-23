package com.yeming.site.util;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yeming.gao
 * @Description: xml工具类
 * @date 2019/6/27 19:42
 */
public class XmlUtils {
    private XmlUtils() {
    }

    /**
     * xml转换为map
     *
     * @param xml 需要转换得xml
     * @return Map<String               ,               String> 将输入流转换程Map对象
     * @throws DocumentException xml转换异常
     */
    public static Map<String, String> xmlToMap(String xml) throws DocumentException {
        Map<String, String> map = new HashMap<>(16);
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        //遍历节点
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param t 消息对象
     * @return xml
     */
    public static <T> String objectToXml(T t, String rootElement) {
        XStream xstream = new XStream();
        xstream.alias(rootElement, t.getClass());
        return xstream.toXML(t);
    }

    /**
     * 将xml转换成首字母大写
     *
     * @param xmlStr 需要转换的xml
     * @param breakElements 不需要转换的节点
     * @return String
     */
    public static String xmlTagCapitalize(String xmlStr,String ... breakElements) {
        String regex = "<(/*[A-Za-z]+)>";
        Matcher matcher = Pattern.compile(regex).matcher(xmlStr);
        StringBuffer sb = new StringBuffer();
        outbroke:
        while (matcher.find()) {
            String str = matcher.group(1);
            for(String breakElement : breakElements){
                if(str.contains(breakElement)){
                    continue outbroke;
                }
            }
            char[] chars = str.toCharArray();
            if (!str.startsWith("/")) {
                if (chars[0] >= 'a' && chars[0] <= 'z') {
                    chars[0] = (char) (chars[0] - 32);
                }
            } else {
                if (chars[1] >= 'a' && chars[1] <= 'z') {
                    chars[1] = (char) (chars[1] - 32);
                }
            }
            matcher.appendReplacement(sb, "<" + new String(chars) + ">");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void main(String[] args) {
        String xml = "<xml><name>gym</name><age>18</age></xml>";
        xmlTagCapitalize(xml,"xml","name");
    }


}
