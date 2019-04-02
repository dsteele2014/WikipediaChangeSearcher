package edu.bsu.cs222;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class ParseXMLFromFileTest {

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

    private void testParseXmlDocumentFromFile()
    {}
}
