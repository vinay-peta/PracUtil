package com.java.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentHandler {

	/**
	 * Convert Node to String
	 * 
	 * @param node
	 * @return
	 * @throws TransformerException
	 */
	public static String nodeToString(Node node) throws TransformerException {
		StringWriter sw = new StringWriter();
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(node), new StreamResult(sw));

		return sw.toString();
	}
	
	/**
	 * get Document from String
	 * @param caseDocument
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document stringToDocument(String caseDocument) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new InputSource(new StringReader(caseDocument)));
	}
	
	/**
	 * Convert Document to String
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String documentToString(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}
}
