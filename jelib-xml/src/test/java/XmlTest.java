package com.test.xml;

import org.junit.Test;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;

import com.je.core.JeLib;
import com.je.xml.reader.JeXmlReader;
import com.je.xml.writer.JeXmlWriter;

public class XmlTest {
	public static final class Data {
		public String a, b, c, d;

		public Data() {
		}

		@Override
		public String toString() {
			return String.format("Data{a:%s,b:%s,c:%s,d:%s}", a, b, c, d);
		}
	}

	@Test
	public void testReader() {
		InputStream xmlStream = getClass().getResourceAsStream("/data.xml");
		JeXmlReader<Data> reader = new JeXmlReader<>();
		Data d = reader.read(xmlStream, Data.class);
		JeLib.console().log(d);
	}

	@Test
	public void testWriter() {
		Data dat = new Data();
		dat.a = "a value";
		dat.b = "b value";
		dat.c = "c c c c";
		dat.d = "d d d d";

		File file = new File("data.xml");
		try(OutputStream outStream = new FileOutputStream(file)) {
			new JeXmlWriter<Data>().write(outStream, dat);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}
