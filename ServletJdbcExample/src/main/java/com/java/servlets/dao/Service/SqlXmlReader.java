package com.java.servlets.dao.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by proton2 on 11.12.2016.
 */
public class SqlXmlReader {

    public SqlXmlReader() {
    }

    public static String getQuerryStr(String className, String querryName) {
        String fileName = "sql.xml";
        ClassLoader classLoader = SqlXmlReader.class.getClassLoader();
        File fXmlFile = new File(classLoader.getResource(fileName).getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase("querry")) {
            return null;
        }
        NodeList nList = doc.getElementsByTagName("class");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if (className.equalsIgnoreCase(eElement.getAttribute("name"))) {
                    NodeList sqlList = eElement.getElementsByTagName("SQL");
                    for (int i = 0; i < sqlList.getLength(); i++) {
                        Node sqlNode = sqlList.item(i);
                        if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element sqlElement = (Element) sqlNode;
                            if (querryName.equalsIgnoreCase(sqlElement.getAttribute("name"))) {
                                return sqlElement.getFirstChild().getNextSibling().getNodeValue();
                            }
                        }
                    }
                }//eElement.getElementsByTagName("getall").item(0).getTextContent());
            }
        }
        return null;
    }
}
