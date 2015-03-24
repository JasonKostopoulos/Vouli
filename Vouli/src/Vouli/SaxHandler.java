/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vouli;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Iasonas
 */
public class SaxHandler extends DefaultHandler {

    public void startDocument() throws SAXException {
        System.out.println("start document   : ");
    }

    public void endDocument() throws SAXException {
        System.out.println("end document     : ");
    }

    public void startElement(String uri, String localName,  String qName, Attributes attributes)throws SAXException {

        System.out.println("start element    : " + qName);
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
        System.out.println("end element      : " + qName);
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        System.out.println("start characters : " +
            new String(ch, start, length));
    }

}