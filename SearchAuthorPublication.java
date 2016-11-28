import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class SearchAuthorPublication extends DefaultHandler {
	boolean bAuthor = false;
	boolean bTitle = false;

	boolean bTitleMatch = false;
	boolean bSince = false;
	boolean bArticle = false;

	boolean bYearMatch = false;
	boolean bYear = false;

	String recAuthor;
	String recTitle;

	int recYear;
	int recStartYear;
	int recEndYear;
	int year;

	String author;
	String title;

	ArrayList<String> partAuthor;
	ArrayList<String> partTitle;

	public SearchAuthorPublication (String author, String year) {
		recAuthor = author;
		recYear = Integer.parseInt(year);

		bSince = true;
	}

	public SearchAuthorPublication (String author, String startYear, String endYear) {
		
		recAuthor = author;
		recStartYear = Integer.parseInt(startYear);
		recEndYear = Integer.parseInt(endYear);

		bSince = false;
	}




	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("phdthesis")|| qName.equalsIgnoreCase("mastersthesis")|| qName.equalsIgnoreCase("www")|| qName.equalsIgnoreCase("incollection")|| qName.equalsIgnoreCase("book")|| qName.equalsIgnoreCase("proceedings")|| qName.equalsIgnoreCase("inproceedings")) {
			bArticle = true;
		}

		if (qName.equalsIgnoreCase("author")) {
			bAuthor = true;
			partAuthor = new ArrayList<String>();
		}

		else if (qName.equalsIgnoreCase("title")) {
			bTitle = true;
			partTitle = new ArrayList<String>();
		}

		else if (qName.equalsIgnoreCase("year")) {
			bYear = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// System.out.println(qName);
		if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("phdthesis")|| qName.equalsIgnoreCase("mastersthesis")|| qName.equalsIgnoreCase("www")|| qName.equalsIgnoreCase("incollection")|| qName.equalsIgnoreCase("book")|| qName.equalsIgnoreCase("proceedings")|| qName.equalsIgnoreCase("inproceedings")) {
			if (bTitleMatch && bYearMatch) {
				System.out.println(title);

				bArticle = false;
				

				title = null;
			}

			bTitleMatch = false;
			bYearMatch = false;
		}

		else if (qName.equalsIgnoreCase("author")) {
			StringBuilder listString = new StringBuilder();

			for (String s : partAuthor)
     			listString.append(s);
			
			bAuthor = false;
			// System.out.println("author:"+listString.toString() + listString.toString().equals(recAuthor));
			if (listString.toString().equals(recAuthor)) {
				bTitleMatch = true;
			}
		}

		else if (qName.equalsIgnoreCase("title")) {
			StringBuilder listString = new StringBuilder();

			for (String s : partTitle)
     			listString.append(s);
			
			bTitle = false;

			// if (bTitleMatch) {
				title = listString.toString();
				// bTitleMatch = false;
			// }

			bYear = false;

		}

		else if (qName.equalsIgnoreCase("year")) {
			bYear = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (bAuthor) {
			partAuthor.add(new String(ch, start, length));
		}

		else if (bTitle) {
			partTitle.add(new String(ch, start, length));
		}

		else if (bYear) {
			year = Integer.parseInt(new String(ch, start, length));
			// System.out.println("year:" + year);
			if (bSince) {
				if (year > recYear) {
					bYearMatch = true;
				}
			}
			else if (!bSince) {
				if (year > recStartYear && year < recEndYear) {
					bYearMatch = true;
				}
			}
		}
	}
}