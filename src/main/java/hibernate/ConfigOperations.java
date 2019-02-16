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
        
        public static final String HIBERNATE_CONFIG_FILEPATH = "./src/main/resources/hibernate.cfg.xml"; //@TODO: Change this HIBERNATE_CONFIG_FILEPATH
        
        public String getDatabaseUrl()
        {
                String databaseUrl = null;
                try {
                        //Access Files
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                        // Expression for connection url
                        String xpathURLExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.url']";

                        // Create XPathFactory object
                        XPathFactory xpathFactory = XPathFactory.newInstance();

                        // Create XPath object
                        XPath xpath = xpathFactory.newXPath();

                        // Create XPathExpression object
                        XPathExpression expr = xpath.compile(xpathURLExpression);
                        Node connectionUrl = (Node) xpath.compile(xpathURLExpression).evaluate(doc, XPathConstants.NODE);

                        databaseUrl = connectionUrl.getTextContent();
                } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SAXException e) {
                        e.printStackTrace();
                } catch (XPathExpressionException e) {
                        e.printStackTrace();
                }
                return databaseUrl;
        }

        public String getDatabaseUsername()
        {
                String databaseUsername = null;
                try {
                        //Access Files
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                        // Expression for connection url
                        String xpathExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.username']";

                        // Create XPathFactory object
                        XPathFactory xpathFactory = XPathFactory.newInstance();

                        // Create XPath object
                        XPath xpath = xpathFactory.newXPath();

                        // Create XPathExpression object
                        XPathExpression expr = xpath.compile(xpathExpression);
                        Node connectionUsername = (Node) xpath.compile(xpathExpression).evaluate(doc, XPathConstants.NODE);

                        databaseUsername = connectionUsername.getTextContent();
                } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SAXException e) {
                        e.printStackTrace();
                } catch (XPathExpressionException e) {
                        e.printStackTrace();
                }
                return databaseUsername;
        }

public String getDatabasePassword()
{
        String databasePassword = null;
        try {
                //Access Files
                DocumentBuilderFactory docFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                // Expression for connection url
                String xpathExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.password']";

                // Create XPathFactory object
                XPathFactory xpathFactory = XPathFactory.newInstance();

                // Create XPath object
                XPath xpath = xpathFactory.newXPath();

                // Create XPathExpression object
                XPathExpression expr = xpath.compile(xpathExpression);
                Node connectionPassword = (Node) xpath.compile(xpathExpression).evaluate(doc, XPathConstants.NODE);

                databasePassword = connectionPassword.getTextContent();
        } catch (ParserConfigurationException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (SAXException e) {
                e.printStackTrace();
        } catch (XPathExpressionException e) {
                e.printStackTrace();
        }
        return databasePassword;
}


        public void setConfigURL (String newDatabaseURL) {
                try {
                        //Access Files
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                        // Expression for connection url
                        String xpathURLExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.url']";

                        // Create XPathFactory object
                        XPathFactory xpathFactory = XPathFactory.newInstance();

                        // Create XPath object
                        XPath xpath = xpathFactory.newXPath();

                        // Create XPathExpression object
                        XPathExpression expr = xpath.compile(xpathURLExpression);
                        Node connectionUrl = (Node) xpath.compile(xpathURLExpression).evaluate(doc, XPathConstants.NODE);

                        connectionUrl.setTextContent(newDatabaseURL);

                        // write the content into xml file
                        TransformerFactory transformerFactory = TransformerFactory
                                .newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                        transformer.transform(source, result);

                        System.out.println("Done");

                } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                } catch (TransformerException e) {
                        e.printStackTrace();
                } catch (SAXException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (XPathExpressionException e) {
                        e.printStackTrace();
                }
        }
                public void setConfigUsername (String newDatabaseUsername) {
                        try {
                                //Access Files
                                DocumentBuilderFactory docFactory = DocumentBuilderFactory
                                        .newInstance();
                                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                                Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                                // Expression for connection url
                                String xpathUsernameExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.username']";

                                // Create XPathFactory object
                                XPathFactory xpathFactory = XPathFactory.newInstance();

                                // Create XPath object
                                XPath xpath = xpathFactory.newXPath();

                                // Create XPathExpression object
                                XPathExpression expr = xpath.compile(xpathUsernameExpression);
                                Node connectionUrl = (Node) xpath.compile(xpathUsernameExpression).evaluate(doc, XPathConstants.NODE);

                                connectionUrl.setTextContent(newDatabaseUsername);

                                // write the content into xml file
                                TransformerFactory transformerFactory = TransformerFactory
                                        .newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                DOMSource source = new DOMSource(doc);
                                StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                                transformer.transform(source, result);

                                System.out.println("Done");

                        } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                        } catch (TransformerException e) {
                                e.printStackTrace();
                        } catch (SAXException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } catch (XPathExpressionException e) {
                                e.printStackTrace();
                        }
                }
        public void setConfigPassword (String newDatabasePassword) {
                try {
                        //Access Files
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(HIBERNATE_CONFIG_FILEPATH);

                        // Expression for connection url
                        String xpathUsernameExpression = "/hibernate-configuration/session-factory/property[@name='hibernate.connection.password']";

                        // Create XPathFactory object
                        XPathFactory xpathFactory = XPathFactory.newInstance();

                        // Create XPath object
                        XPath xpath = xpathFactory.newXPath();

                        // Create XPathExpression object
                        XPathExpression expr = xpath.compile(xpathUsernameExpression);
                        Node connectionUrl = (Node) xpath.compile(xpathUsernameExpression).evaluate(doc, XPathConstants.NODE);

                        connectionUrl.setTextContent(newDatabasePassword);

                        // write the content into xml file
                        TransformerFactory transformerFactory = TransformerFactory
                                .newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(HIBERNATE_CONFIG_FILEPATH));
                        transformer.transform(source, result);

                        System.out.println("Done");

                } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                } catch (TransformerException e) {
                        e.printStackTrace();
                } catch (SAXException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (XPathExpressionException e) {
                        e.printStackTrace();
                }
        }
}