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

	ArrayList<String> authorAlias = new ArrayList<String>();

	public GetKPublications(HashMap<String,Integer> map, int k) {//, ArrayList<AuthorNames> authorEntities) {
		this.map = map;
		this.k = k;

		// searchForEntities(authorEntities);
	}

	// public void searchForEntities(ArrayList<AuthorNames> authorEntities, String recAuthor) {
	// 	// this.authorEntities = authorEntities;

	// 	for (AuthorNames a : authorEntities) {
	// 		if (search(a.getAlias(), recAuthor)) {
	// 			authorAlias = a.getAlias();
	// 		}
	// 	}
	// }

	// public boolean search (ArrayList<String> arr, String str) {
	// 	for (String x : arr) {
	// 		if (x.equals(str))
	// 			return true;
	// 	}

	// 	return false;
	// }

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
		String[][] arr = new String[count][3];
		int i = 0;

		for (String s : map.keySet()) {
			if (map.get(s) > _k) {
				// searchForEntities(s);
				arr[i][0] = Integer.toString(i+1);
				arr[i][1] = s;
				arr[i][2] = Integer.toString(map.get(s));
				// System.out.println(s + "  " + map.get(s));
				i++;	
			}
		}

		return arr;
	}


	public int getCount() {
		return count;
	}

	public ArrayList<String> getAuthorAlias() {
		return authorAlias;
	}
}