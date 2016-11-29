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

	///This is an overridden method from DefaultHandler which reads the starting tag of an element in the XML file.
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			// System.out.println(count++);
			bAuthor = true;
			partAuthor = new ArrayList<String>();
		}
	}
	/**< It only reads the tags of the elements that have 
	* been explicity added by programmer to check from the XML file.*/

	///This is an overridden method from DefaultHandler which reads the content between the opening and closing tags of an XML file.
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (bAuthor) {
			partAuthor.add(new String(ch, start, length));
		}
	}

	///This is an overridden method from DefaultHandler which reads the closing tag of an element in the XML file.
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			StringBuilder listString = new StringBuilder();

			for (String s : partAuthor)
     			listString.append(s);
			
			bAuthor = false;

			map.put(listString.toString(),0);
		}
	}

	///This method returns a HashMap<String, Integer>.
	public HashMap<String,Integer> getHashMap() {
		return map;
	}
}