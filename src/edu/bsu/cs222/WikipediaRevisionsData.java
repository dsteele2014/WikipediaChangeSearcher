package edu.bsu.cs222;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.print.Doc;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class WikipediaRevisionsData {

    private HashMap<String, Date> userData = new HashMap<>();
    private String urlString = "";
    private String redirect = "";

    WikipediaRevisionsData(String urlString) {
        this.urlString = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=xml&rvprop=timestamp%7Cuser&rvlimit=30&titles=" + urlString + "&redirects=";
    }

    HashMap<String, Date> wikipediaData(){
        try {
            searchWikipedia();
        } catch (UnknownHostException e){
            redirect = "Cannot connect to the Wikipedia page. Please check your internet connection and try again.";
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return userData;
    }

    private void searchWikipedia() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        URLConnection connection = connectToWikipedia();
        Document document = readXmlDocumentFrom(connection);
        parseXML(document);
    }

    private URLConnection connectToWikipedia() throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent",
                "CS222FirstProject/0.1 (dllargent@bsu.edu)");
        connection.connect();
        return connection;
    }

    private static Document readXmlDocumentFrom(URLConnection connection) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(connection.getInputStream());
    }

    private static void printDocument(Document doc) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
    }

    private void parseXML(Document document) throws ParserConfigurationException, IOException, SAXException {
        String user;
        String tempTime;
        Date parsedDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        redirects(document);
        NodeList nodes = document.getElementsByTagName("rev");
        for(int i = 0; i<nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                tempTime = element.getAttribute("timestamp");
                try{
                    parsedDate = sdf.parse(tempTime);
                }
                catch (ParseException parseException){
                    parseException.printStackTrace();
                }
                user = element.getAttribute("user");
                userData.put(user, parsedDate);
            }
        }
    }

    private void redirects(Document document){
        try {
            NodeList redirectNode = document.getElementsByTagName("r");
            Element redirectElement = (Element) redirectNode.item(0);
            redirect = "Redirected from " + redirectElement.getAttribute("to");
        } catch(Exception e){
            redirect = "No redirect";
        }
    }

    String getRedirect(){
        return redirect;
    }
}
