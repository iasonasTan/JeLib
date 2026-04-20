package com.je.xml.reader;

import com.je.core.JeLib;
import com.je.xml.IterableNodeList;
import com.je.xml.JeXmlConstants;

import java.io.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.util.*;

public class JeXmlReader<T> implements JeXmlConstants {
	public T read(InputStream input, Class<T> clazz) {
		return read(input, clazz, FORMAT_NODE_VALUE);
	}

	public T read(InputStream input, Class<T> clazz, String formatType) {
		T t;
		
		try {
			Constructor<T> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			t = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | 
				InvocationTargetException | NoSuchMethodException e) {

			throw new RuntimeException(e);
		}
		assert t != null;

		Document document;
		try {
			DocumentBuilder builder = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder();
			document = builder.parse(input);
		} catch(ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
		assert document != null;

		try {
			JeLib.console().log("Storing to class "+clazz.getName());
		    for(Field field: clazz.getFields()){
		    	field.setAccessible(true);
		        readValueInNode(document, t, field);
		    }
        	} catch (Exception e) {
        		throw new RuntimeException(e);
        	}
		return t;
	}

	private void readValueInNode(Document document, T t, Field field) throws Exception {
	    JeLib.console().log("Searching for tags named '"+field.getName()+"'");
		NodeList nodes = document.getElementsByTagName(field.getName());
		for(Node node: new IterableNodeList(nodes)) {
		    if(node instanceof Element element) {
		        String content = element.getTextContent();
				JeLib.console().log("Found value '"+content+"'");
                field.set(t, content);
		    }
		}
	}
}
