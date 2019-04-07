package hibernate;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

public class ConfigOperations {
        
        private static final String HIBERNATE_CONFIG_FILEPATH = "./hibernate.cfg.xml";

        private Document accessConfigFile() {
                //Access Files
                DocumentBuilderFactory docFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder docBuilder = null;
                try {
                        docBuilder = docFactory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                }

                Document doc = null;
                try {
                        assert docBuilder != null;
                        doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);
                } catch (IOException | SAXException e) {
                        e.printStackTrace();
                }

                return doc;
        }

        private Node getNodeByXpathExpression(Document doc, String xpathExpression) {
                // Create XPathFactory object
                XPathFactory xpathFactory = XPathFactory.newInstance();

                // Create XPath object
                XPath xpath = xpathFactory.newXPath();

                // Create XPathExpression object
                Node node = null;

                try {
                        node = (Node) xpath.compile(xpathExpression).evaluate(doc, XPathConstants.NODE);
                } catch (XPathExpressionException e) {
                        e.printStackTrace();
                }

                return node;
        }

        public String getDatabaseUrl()
        {
                //Get document file
                Document doc = accessConfigFile();

                // Expression for connection url
                String xpathURLExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.url']";

                // Create XPathExpression object
                Node connectionUrl = getNodeByXpathExpression(doc, xpathURLExpression);

                //Return content of URL xpath
                return connectionUrl.getTextContent();
        }

        public String getDatabaseUsername()
        {
                //Get document file
                Document doc = accessConfigFile();

                // Expression for connection url
                String xpathUsernameExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.username']";

                // Create XPathExpression object
                Node connectionUsername = getNodeByXpathExpression(doc, xpathUsernameExpression);

                //Return content of username xpath
                return connectionUsername.getTextContent();
        }

        public String getDatabasePassword()
        {
                //Get document file
                Document doc = accessConfigFile();

                // Expression for connection url
                String xpathPasswordExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.password']";

                // Create XPathExpression object
                Node connectionPassword = getNodeByXpathExpression(doc, xpathPasswordExpression);

                //Return content of password xpath
                return connectionPassword.getTextContent();
        }

        public void setConfigURL (String newDatabaseURL) {
                try {
                        //Get document file
                        Document doc = accessConfigFile();

                        // Expression for connection url
                        String xpathURLExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.url']";

                        // Create XPathExpression object
                        Node connectionUrl = getNodeByXpathExpression(doc, xpathURLExpression);

                        connectionUrl.setTextContent(newDatabaseURL);

                        // write the content into xml file
                        TransformerFactory transformerFactory = TransformerFactory
                                .newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                        transformer.transform(source, result);

                        System.out.println("Done");
                } catch (TransformerException e) {
                        e.printStackTrace();
                }
        }

                public void setConfigUsername (String newDatabaseUsername) {
                        try {
                                //Get document file
                                Document doc = accessConfigFile();

                                // Expression for connection url
                                String xpathUsernameExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.username']";

                                // Create XPathExpression object
                                Node connectionUsername = getNodeByXpathExpression(doc, xpathUsernameExpression);

                                connectionUsername.setTextContent(newDatabaseUsername);

                                //Write the content into xml file
                                TransformerFactory transformerFactory = TransformerFactory
                                        .newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                DOMSource source = new DOMSource(doc);
                                StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                                transformer.transform(source, result);

                                System.out.println("Done");
                        } catch (TransformerException e) {
                                e.printStackTrace();
                        }
                }

        public void setConfigPassword (String newDatabasePassword) {
                try {
                        //Get document file
                        Document doc = accessConfigFile();

                        // Expression for connection url
                        String xpathPasswordExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.password']";

                        // Create XPathExpression object
                        Node connectionPassword = getNodeByXpathExpression(doc, xpathPasswordExpression);

                        connectionPassword.setTextContent(newDatabasePassword);

                        //Write the content into xml file
                        TransformerFactory transformerFactory = TransformerFactory
                                .newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                        transformer.transform(source, result);

                        System.out.println("Done");
                } catch (TransformerException e) {
                        e.printStackTrace();
                }
        }
}