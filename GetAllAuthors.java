import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class GetAllAuthors extends DefaultHandler {
	boolean bAuthor = false;
	// boolean bTitle = false;
	int count = 0;

	HashMap<String,Integer> map = new HashMap<String,Integer>();
	ArrayList<String> partAuthor;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			// System.out.println(count++);
			bAuthor = true;
			partAuthor = new ArrayList<String>();
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		if (bAuthor) {
			partAuthor.add(new String(ch, start, length));
		}
	}


	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			StringBuilder listString = new StringBuilder();

			for (String s : partAuthor)
     			listString.append(s);
			
			bAuthor = false;

			map.put(listString.toString(),0);
		}
	}

	public HashMap<String,Integer> getHashMap() {
		return map;
	}
}