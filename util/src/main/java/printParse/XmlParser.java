package printParse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.qzdatasoft.comm.framework.listener.QzListenerBean;

/**
 * 说明：解析XML文件
 * @author 陈文
 * 时间: 2010-05-06
 */

public class XmlParser {
	private Log logger = LogFactory.getLog(this.getClass().getName());
	private Document doc = null;
	
	/**
	 * 构造函数
	 * @param xmlPath: xml路径
	 */
	public XmlParser( String xmlPath ) {
	    try {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true); 
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse( this.getClass().getResourceAsStream(xmlPath) );		    
	    } catch (ParserConfigurationException e) 
	    {
		logger.error( "ParserConfigurationException:" + e);
	    } catch (SAXException e)
	    {
		logger.error("SAXException:" + e);
	    } catch (IOException e) {
		logger.error("IOException:" + e);
	    }
	}
	
	/**
	 * 根据节点名,属性名和属性值找到其节点
	 * @param tagName
	 * @param value
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public Element getElementByAttribute(String tagName,String value,String attribute) {
	    NodeList nodeList = doc.getElementsByTagName(tagName);
	    Node node = null;
	    Element element = null;
	    for(int i=0;i<nodeList.getLength();i++) {
		node = nodeList.item(i);
		element = (Element) node;
		if(value.equals(element.getAttribute(attribute))) {
		    break;
		}
	    }
	    return element;
	}
	
	/**
	 * 获取监听bean列表
	 * @param element
	 * @return
	 */
	//public List getQzListenrBeanList(Element element) {
	/*
	    List list = new ArrayList();
	    Node sunNode = null;
		NodeList sunNodeList = element.getElementsByTagName("qz_listener_class");
		for(int i=0;i<sunNodeList.getLength();i++) {
		    QzListenerBean qzListenerBean = new QzListenerBean();
		    sunNode = sunNodeList.item(i);
		    element = (Element) sunNode;
		    qzListenerBean.setName(element.getAttribute("name"));
		    qzListenerBean.setClassFullName(element.getAttribute("class"));
		    qzListenerBean.setOnerrorroolback(element.getAttribute("onerrorroolback"));
		    qzListenerBean.setDepends(element.getAttribute("depends"));
		    list.add(qzListenerBean);
		}
	    return list;
	*/
	//}
	
	public String getAnalyzer() {	
	    String analysis = "";
	    
	    try {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr  = xpath.compile("//analysis/classname");
		analysis = (String)expr.evaluate(doc, XPathConstants.STRING);
	    } 
	    catch (XPathExpressionException e) {
		logger.error("XPathExpressionException:" + e);
		return "";
	    }
	    return analysis;
	}
	
	public static void main(String[] args) {/*
	    XmlParser xmlParser = new XmlParser("/qzlistener.xml");
	    System.out.println("-------------------" + xmlParser.getQzListenrBeanList(xmlParser.getElementByAttribute("qz_listener", "001", "code")));
	*/}
}
