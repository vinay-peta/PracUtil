package com.java.utils;

import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathHandler {

	private Document document;
	
	public XPathHandler(Document document){
		this.document=document;
	}
	
	/**
	 * Get text from document whose XPATH is xPath
	 * @param xPath
	 * @return String
	 * @throws XPathExpressionException
	 */
	public String getValue(String xPath) throws XPathExpressionException{
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		return (String) expr.evaluate(document, XPathConstants.STRING);
	}
	
	/**
	 * If the xPath has multiple nodes separate it using separator
	 * @param xPath
	 * @param separator
	 * @return String
	 * @throws XPathExpressionException
	 */
	public String getValues(String xPath,String separator) throws XPathExpressionException{
		StringBuilder str = new StringBuilder();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		NodeList list = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		for(int count=0;count<list.getLength();count++){
			str.append(separator);
			str.append(list.item(count).getTextContent());
		}
		if(separator.equals("||"))
			separator="\\|{2}";
		return str.toString().replaceFirst(separator,"");
	}

	/**
	 * If the xPath has multiple nodes give it as a list
	 * @param xPath
	 * @param separator
	 * @return String
	 * @throws XPathExpressionException
	 */
	public List<String> getValuesAsList(String xPath) throws XPathExpressionException{
		List<String> lList = new LinkedList<String>();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		NodeList list = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		for(int count=0;count<list.getLength();count++){
			lList.add(list.item(count).getTextContent());
		}
		return lList;
	}
	
	/**
	 * If the xPath has multiple nodes give the count
	 * @param xPath
	 * @param separator
	 * @return String
	 * @throws XPathExpressionException
	 */
	public String getCount(String xPath) throws XPathExpressionException{
		StringBuilder str = new StringBuilder();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		NodeList list = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		return str.append(list.getLength()).toString();
	}
	
	/**
	 * Get Node if the document matches xPath
	 * @param xPath
	 * @return
	 * @throws XPathExpressionException
	 * @throws TransformerException
	 */
	public String getNodeAsString(String xPath) throws XPathExpressionException, TransformerException{
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		Node node = (Node)expr.evaluate(document, XPathConstants.NODE);
		return DocumentHandler.nodeToString(node);
	}
	
	public String getNodesAsString(String xPath,String tagName) throws XPathExpressionException, TransformerException{
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		NodeList nodeList = (NodeList)expr.evaluate(document, XPathConstants.NODESET);
		StringBuilder str = new StringBuilder("<"+tagName+">");
		for(int item=0;item<nodeList.getLength();item++){
			Node node = nodeList.item(item);
			str.append(DocumentHandler.nodeToString(node));
		}
		str.append("</"+tagName+">");
		return str.toString();
	}
}
