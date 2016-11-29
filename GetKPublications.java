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
	// int count = 0;
	ArrayList<AuthorNames> authorEntities = new ArrayList<AuthorNames>();
	ArrayList<String> authorAlias = new ArrayList<String>();

	/**
   	* This is an constructor where map and key 
   	* values are set to instance variables.
   	*/
	public GetKPublications(HashMap<String,Integer> map, int k, ArrayList<AuthorNames> authorEntities) {//, ArrayList<AuthorNames> authorEntities) {
		this.map = map;
		this.k = k;
		this.authorEntities = authorEntities;
		// searchForEntities(authorEntities);
	}

	///This methos will merge all the alias of one author in the map
	public void mergeAliasMap() {

		for (AuthorNames x : authorEntities) {
			int res = 0;
			int i = 0;
			String foo = null;
			int flag = 0;

			for (String y : x.getAlias()) {

				if (map.get(y)!=null) {
					// System.out.println("yo : " + map.get(y));
					res += map.get(y);
					map.remove(y);

					if (i==0)
						foo = y;
					i++;
				}
				else {
					flag = 1;
				}
				
			}
			if (flag != 1 && foo != null) {
				// System.out.println("hi : " + foo + " " + res);
				map.put(foo,res);
			}


		}

		printMap();
	}


	public void searchForEntities(ArrayList<AuthorNames> authorEntities, String recAuthor) {
		// this.authorEntities = authorEntities;

		for (AuthorNames a : authorEntities) {
			if (search(a.getAlias(), recAuthor)) {
				authorAlias = a.getAlias();
			}
		}
	}

	public boolean search (ArrayList<String> arr, String str) {
		for (String x : arr) {
			if (x.equals(str))
				return true;
		}

		return false;
	}

	///This is an overridden method from DefaultHandler which reads the starting tag of an element in the XML file.
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("author")) {
			bAuthor = true;
			partAuthor = new ArrayList<String>();
		}
		else if (qName.equalsIgnoreCase("www")) {
			bIgnore = true;
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

		else if (qName.equalsIgnoreCase("dblp")) {
			mergeAliasMap();
			
		}
	}

	///This method returns a 2D array of type String with name of authors and the number of publications.
	public String[][] getAuthors (int _k) {
		int count = 0;
		// System.out.println("hello from getAuthors : " + count);
		System.out.println(_k);
		for (String s : map.keySet()) {
			// System.out.println("inside loop : " + map.get(s) + "  " + s);
			if (map.get(s) > _k) {
				count++;
			}
		}

		// System.out.println("hello from getAuthors : " + count);
		String[][] arr = new String[count][3];
		int i = 0;

		for (String s : map.keySet()) {
			// searchForEntities(authorEntities, s);
			if (map.get(s) /*totalAuthorAlias()*/ > _k) {
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
	///This method returns sum of all publications by author alias
	public int totalAuthorAlias() {
		int result = 0;
		for (String x : authorAlias)
			result+=map.get(x);

		return result;
	}

	///This method returns a counter value.
	// public int getCount() {
	// 	return count;
	// }

	///This method returns an ArrayList<String>.
	public ArrayList<String> getAuthorAlias() {
		return authorAlias;
	}

	///This methos prints map
	public void printMap() {
		for (String name : map.keySet()){

            String key = name.toString();
            String value = map.get(name).toString();  
            System.out.println(key + " " + value);  
        }
	}
}