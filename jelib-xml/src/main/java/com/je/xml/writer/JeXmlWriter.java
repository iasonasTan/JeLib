package com.je.xml.writer;

import com.je.core.JeLib;
import com.je.xml.JeXmlConstants;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

public class JeXmlWriter<T> implements JeXmlConstants {
    public void write(OutputStream outputStream, T t) {
        try {
            write(outputStream, t, FORMAT_NODE_VALUE);
        } catch(Exception e) {
            JeLib.console().exception(e);
        }
    }
    
    public void write(OutputStream outputStream, T t, String format) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();

        Document document = builder.newDocument();

        try {
            storeObjectValuesInNodes(t, document);
        } catch (Exception e) {
            JeLib.console().exception(e);
        }

        TransformerFactory transformerFactory=TransformerFactory.newInstance();
        Transformer transformer=transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source=new DOMSource(document);
        StreamResult result=new StreamResult(outputStream);
        transformer.transform(source, result);
    }
    
    private void storeObjectValuesInNodes(T t, Document document) throws Exception {
        Element root = document.createElement(t.getClass().getSimpleName());
        for(Field field: t.getClass().getFields()) {
            Element element1 = document.createElement(field.getName());
            element1.setTextContent(String.valueOf(field.get(t)));
            root.appendChild(element1);
        }
        document.appendChild(root);
    }
}
