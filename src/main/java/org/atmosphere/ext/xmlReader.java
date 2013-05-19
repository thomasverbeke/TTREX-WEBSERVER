package org.atmosphere.ext;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class xmlReader {

	public xmlReader(String path) throws SAXException{	
		try {
			URL url = new URL(path);
		
			XMLReader myReader = XMLReaderFactory.createXMLReader();
			ContentHandler handler = null;
			myReader.setContentHandler(handler);
			myReader.parse(new InputSource(url.openStream()));
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
