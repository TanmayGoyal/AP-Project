import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class GetKPublications extends DefaultHandler{
	boolean bAuthor = false;
	boolean bIgnore = false;

	HashMap<String,Integer> map = new HashMap<String,Integer>();
	int k;
	ArrayList<String> partAuthor;
	int count = 0;

	public GetKPublications(HashMap<String,Integer> map, int k) {
		this.map = map;
		this.k = k;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			bAuthor = true;
			partAuthor = new ArrayList<String>();
		}
		else if (qName.equalsIgnoreCase("www")) {
			bIgnore = true;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		if (bAuthor) {
			partAuthor.add(new String(ch, start, length));
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("author") && bIgnore == false) {
			StringBuilder listString = new StringBuilder();

			for (String s : partAuthor)
     			listString.append(s);
			
			bAuthor = false;

			// System.out.println("hi : " + listString.toString());
			map.put(listString.toString(), map.get(listString.toString()) + 1);
		}
		else if (qName.equalsIgnoreCase("www")) {
			bIgnore = false;
		}
	}

	public String[][] getAuthors (int _k) {
		for (String s : map.keySet()) {
			if (map.get(s) > _k) {
				count++;
			}
		}
		String[][] arr = new String[count][2];
		int i = 0;

		for (String s : map.keySet()) {
			if (map.get(s) > _k) {
				arr[i][0] = s;
				arr[i][1] = Integer.toString(map.get(s));
				// System.out.println(s + "  " + map.get(s));
				i++;	
			}
		}

		return arr;
	}

	public int getCount() {
		return count;
	}
}