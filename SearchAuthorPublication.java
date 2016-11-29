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
	boolean bPages = false;
	boolean bVolume= false;
	boolean bJournal = false;
	boolean bUrl= false;

	String recAuthor;
	String recTitle;

	int recYear;
	int recStartYear;
	int recEndYear;
	int year;

	String author;
	String title;

	ArrayList<Query1DisplayStructure> q1Display = new ArrayList<Query1DisplayStructure>();
	ArrayList<String> partAuthor;
	ArrayList<String> partTitle;
	Query1DisplayStructure q1Object;

	ArrayList<String> authorAlias = new ArrayList<String>();
	ArrayList<AuthorNames> authorEntities = new ArrayList<AuthorNames>();

	public SearchAuthorPublication (String author, String year, ArrayList<AuthorNames> authorEntities) {
		recAuthor = author;
		recYear = Integer.parseInt(year);

		bSince = true;

		searchForEntities(authorEntities);
	}

	public SearchAuthorPublication (String author, String startYear, String endYear, ArrayList<AuthorNames> authorEntities) {
		
		recAuthor = author;
		recStartYear = Integer.parseInt(startYear);
		recEndYear = Integer.parseInt(endYear);

		bSince = false;

		searchForEntities(authorEntities);

	}

	public void searchForEntities(ArrayList<AuthorNames> authorEntities) {
		this.authorEntities = authorEntities;

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


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("phdthesis")|| qName.equalsIgnoreCase("mastersthesis")|| qName.equalsIgnoreCase("www")|| qName.equalsIgnoreCase("incollection")|| qName.equalsIgnoreCase("book")|| qName.equalsIgnoreCase("proceedings")|| qName.equalsIgnoreCase("inproceedings")) {
			bArticle = true;
			q1Object = new Query1DisplayStructure();
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

		else if (qName.equalsIgnoreCase("pages")) {
			bPages = true;
		}
		else if (qName.equalsIgnoreCase("volume")) {
			bVolume = true;	
		}
		else if (qName.equalsIgnoreCase("journal")) {
			bJournal = true;
		}
		else if (qName.equalsIgnoreCase("url")) {
			bUrl = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// System.out.println(qName);
		if (qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("phdthesis")|| qName.equalsIgnoreCase("mastersthesis")|| qName.equalsIgnoreCase("www")|| qName.equalsIgnoreCase("incollection")|| qName.equalsIgnoreCase("book")|| qName.equalsIgnoreCase("proceedings")|| qName.equalsIgnoreCase("inproceedings")) {
			if (bTitleMatch && bYearMatch) {
				// System.out.println(title);
				q1Display.add(q1Object);

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
			if (search(authorAlias,listString.toString())) {
				bTitleMatch = true;
			}

			q1Object.allAuthors.add(listString.toString()); 
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

			q1Object.title = listString.toString(); 

		}

		else if (qName.equalsIgnoreCase("year")) {
			bYear = false;
		}

		else if (qName.equalsIgnoreCase("pages")) {
			bPages = false;
		}
		else if (qName.equalsIgnoreCase("volume")) {
			bVolume = false;	
		}
		else if (qName.equalsIgnoreCase("journal")) {
			bJournal = false;
		}
		else if (qName.equalsIgnoreCase("url")) {
			bUrl = false;
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
			q1Object.year = new String(ch, start, length);
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

		else if (bPages) {
			q1Object.pages = new String(ch, start, length);
		}
		else if (bVolume) {
			q1Object.volume = new String(ch, start, length);
		}
		else if (bJournal) {
			q1Object.journal = new String(ch, start, length);
		}
		else if (bUrl) {
			q1Object.url = new String(ch, start, length);
		}

	}

	public void printData() {
		for (Query1DisplayStructure x : q1Display) {
			x.printData();			
		}
	}

	public String[][] getArray() {
		String[][] arr = new String[q1Display.size()][8];

		for (int i = 0;i<q1Display.size();i++) { //SNO, AUTHORS, TITLE, PAGES, YEAR, VOLUME, JOURNAL, URL
			arr[i][0] = Integer.toString(i+1);
			arr[i][1] = q1Display.get(i).allAuthorsToString();
			arr[i][2] = q1Display.get(i).title;
			arr[i][3] = q1Display.get(i).pages;
			arr[i][4] = q1Display.get(i).year;
			arr[i][5] = q1Display.get(i).volume;
			arr[i][6] = q1Display.get(i).journal;
			arr[i][7] = q1Display.get(i).url;
		}

		Arrays.sort(arr, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                int time1 = Integer.parseInt(entry1[4]);
                int time2 = Integer.parseInt(entry2[4]);
                return time2-time1;
            }
        });

		// arr = sort(arr, 4);

		return arr;
	}

	// public void sort (String[][] arr, int 4) {
		
	// }

	// public String[][] sort(String[][] arr, int col) {

	// }
}