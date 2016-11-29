import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class ParseEntityResolution extends DefaultHandler{
	ArrayList<AuthorNames> authors = new ArrayList<AuthorNames>();
	boolean bwww = false;
	boolean bAuthor = false;

	// ArrayList<String> partwww = new ArrayList<String>();
	ArrayList<String> partAuthor = new ArrayList<String>();

	AuthorNames authorNames;

	///This is an overridden method from DefaultHandler which reads the starting tag of an element in the XML file.
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("www")) {
			bwww = true;
			// partwww.add(attributes.getValue("key"));

			authorNames = new AuthorNames();
			
		}
		else if (qName.equalsIgnoreCase("author") && bwww == true) {
			partAuthor = new ArrayList<String>();
			bAuthor = true;
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
		if (qName.equalsIgnoreCase("www")) {
			bAuthor = false;
			bwww = false;

			// StringBuilder listString = new StringBuilder();

			// for (String s : partwww)
   //   			listString.append(s);

			// authorNames.getAlias().add(listString.toString());
			authors.add(authorNames);

			// partwww = new ArrayList<String>();
			authorNames = new AuthorNames();
		}

		else if (qName.equalsIgnoreCase("author") && bwww == true) {
			StringBuilder listString = new StringBuilder();

			for (String s : partAuthor)
     			listString.append(s);
			
			bAuthor = false;
			authorNames.getAlias().add(listString.toString());
		}
	}

	///This method is used to print the output on the console. (Just for checking purposes)
	public void printData() {

		for (AuthorNames x : authors) {
			for (String y : x.getAlias()) {
				System.out.println(y);
			}
			System.out.println();
		}
	}

	///This method returns an ArrayList<AuthorName>.
	public ArrayList<AuthorNames> getEntityAuthors() {
		return authors;
	}
}