package org.atmosphere.ext;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.atmosphere.ext.xmlReader.Group;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class xmlReader {

	public xmlReader(String path) throws SAXException{	
		try {
			URL url = new URL(path);
		
			XMLReader myReader = XMLReaderFactory.createXMLReader();
			myHandler handler = null;
			myReader.setContentHandler(handler);
			myReader.parse(new InputSource(url.openStream()));
			
			ArrayList<Group> list = handler.getList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class myHandler implements ContentHandler  {
		
		ArrayList<Group> list = new ArrayList<Group>();
		Group groupObj = null;
		String tmpValue;

		
		public myHandler(){
			
		}
		
		public ArrayList<Group> getList(){
			return list;
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			
			
			if (qName.equalsIgnoreCase("group")) {
	           
	            int id = attributes.getIndex(qName);
	            groupObj = new Group(id);
	   
	        } else if (qName.equalsIgnoreCase("GroupName")) {
	        	 System.out.println("GroupName");
	        } else if (qName.equalsIgnoreCase("startPos")) {
	        	 System.out.println("StartPos");
	        	 //double StartPos = attributes.get
	        }
			
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			
			 if (qName.equals("book")) {				 
				 list.add(groupObj);
			 }
				 
	         if (qName.equalsIgnoreCase("groupName")) {	 
	             groupObj.setGroupName(tmpValue);
	         }
	         
	         if (qName.equalsIgnoreCase("startPos")) {	 
	             groupObj.setGroupName(tmpValue);
	         }

		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
		
			tmpValue = new String(ch, start, length);

		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void skippedEntity(String name) throws SAXException {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}
	
	public class Group {
		private int ID;
		private String GroupName;
		private double StartPos;
		
		public Group(int ID){
			this.ID = ID;
		}

		public int getID() {
			return ID;
		}

		public String getGroupName() {
			return GroupName;
		}

		public void setGroupName(String groupName) {
			GroupName = groupName;
		}

		public double getStartPos() {
			return StartPos;
		}

		public void setStartPos(double startPos) {
			StartPos = startPos;
		}
		
		
		
	}
}


