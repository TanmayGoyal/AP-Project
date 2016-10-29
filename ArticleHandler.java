import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ArticleHandler extends DefaultHandler {
	boolean barticle = false;
	boolean bauthor = false;
	boolean btitle = false;
	boolean bpages = false;
	boolean byear = false;
	boolean bvolume = false;
	boolean bjournal = false;
	boolean bnumber = false;
	boolean burl = false;
	boolean bee = false;

	int count = 1;
	// public ArticleHandler() {
	// 	System.out.println("ola");
	// }

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		// System.out.println("yo   "+qName);
		// System.out.println(localName);
		
		if (qName.equalsIgnoreCase("article")) {
			// System.out.println("hehe");
			// String date = attributes.getValue("mdate");
			// System.out.println("Date: " + date);
			// String key = attributes.getValue("key");
			// System.out.println("Key: " + key);


			System.out.print(count + "  ");

			count++;

			barticle = true;
		}

		if (barticle) {

			
			if (qName.equalsIgnoreCase("author")) {
				bauthor = true;
			}
			else if (qName.equalsIgnoreCase("title")) {
				btitle = true;
			}
			else if (qName.equalsIgnoreCase("pages")) {
				bpages = true;
			}
			else if (qName.equalsIgnoreCase("year")) {
				byear = true;
			}
			else if (qName.equalsIgnoreCase("volume")) {
				bvolume = true;
			}
			else if (qName.equalsIgnoreCase("journal")) {
				bjournal = true;
			}
			else if (qName.equalsIgnoreCase("number")) {
				bnumber = true;
			}
			else if (qName.equalsIgnoreCase("url")) {
				burl = true;
			}
			else if (qName.equalsIgnoreCase("ee")) {
				bee = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("article")) {
			System.out.println("End element: " + qName);
			barticle = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (bauthor) {
			System.out.println("Author: " + new String(ch, start, length));
			bauthor = false;
		}
		else if (btitle) {
			// System.out.println("Title: " + new String(ch, start, length));
			btitle = false;
		}
		else if (bpages) {
			// System.out.println("Pages: " + new String(ch, start, length));
			bpages = false;
		}
		else if (byear) {
			// System.out.println("Year: " + new String(ch, start, length));
			byear = false;
		}
		else if (bvolume) {
			// System.out.println("Volume: " + new String(ch, start, length));
			bvolume = false;
		}
		else if (bjournal) {
			// System.out.println("Journal: " + new String(ch, start, length));
			bjournal = false;
		}
		else if (bnumber) {
			// System.out.println("Number: " + new String(ch, start, length));
			bnumber = false;
		}
		else if (burl) {
			// System.out.println("URL: " + new String(ch, start, length));
			burl = false;
		}
		else if (bee) {
			// System.out.println("EE: " + new String(ch, start, length));
			bee = false;
		}
	}
}