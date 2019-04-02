package edu.bsu.cs222;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ReadXMLFromFileTest {
    @Test
    public void testReadXmlDocumentFromFile() throws ParserConfigurationException, SAXException, IOException {
        Document document = readSampleFileAsXmlDocument();
        assertNotNull(document.getDocumentElement());
    }

    private Document readSampleFileAsXmlDocument() throws SAXException, IOException, ParserConfigurationException {
        InputStream sampleFileInputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("sample.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(sampleFileInputStream);
    }
    private static final String PATH_TO_REVISIONS_ELEMENT = "api/query/pages/page/revisions";

    @Test
    public void testCountRevisionChildrenWithXpath()
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        Document document = readSampleFileAsXmlDocument();
        XPathExpression xpathExpression = createExpressionToCountChildrenOfRevisionElement();
        Node result = (Node) xpathExpression.evaluate(document, XPathConstants.NODE);
        assertEquals(30, result.getChildNodes().getLength());
    }

    private XPathExpression createExpressionToCountChildrenOfRevisionElement()
            throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = PATH_TO_REVISIONS_ELEMENT;
        return xpath.compile(expression);
    }
}
