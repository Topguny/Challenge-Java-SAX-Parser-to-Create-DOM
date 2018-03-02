/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.File;
import javafx.scene.control.TreeItem;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author lukas
 */
public class Parser extends DefaultHandler {
    static TreeItem<String> Root = new TreeItem();
    
    static TreeItem<String> parser(File XMLFile) throws Exception  {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(XMLFile, new Parser());           
            TreeItem<String> item = Root.getChildren().get(0);
            Root.getChildren().clear();
            return item;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
   }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        TreeItem item = new TreeItem<String>(qName);
        Root.getChildren().add(item);
        Root = item;
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
        Root = Root.getParent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String data = String.valueOf(ch, start, length).trim();
        if (!data.isEmpty()) {
            Root.getChildren().add(new TreeItem<String>(data));
        }
    }
}
