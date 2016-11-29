import java.util.*;

public class Query1DisplayStructure {
	ArrayList<String> allAuthors = new ArrayList<String>();
	String title;
	String pages;
	String year;
	String volume;
	String journal;
	String url;

	public void printData () {
		System.out.print("Authors : ");
		for (String x : allAuthors)
			System.out.print(x + " ,");
		System.out.println();


		System.out.println("Title : " + title);
		System.out.println("Pages : " + pages);
		System.out.println("year : " + year);
		System.out.println("Volume : " + volume);
		System.out.println("journal ; " + journal);
		System.out.println("url : " + url);

		System.out.println();	
	}

	public String allAuthorsToString() {
		ArrayList<String> foo = new ArrayList<String>();

		for (int i = 0;i<allAuthors.size();i++) {
			if (i == allAuthors.size()-1)
				foo.add(allAuthors.get(i));

			else {
				foo.add(allAuthors.get(i));
				foo.add(", ");

			}

		}

		StringBuilder listString = new StringBuilder();

			for (String s : foo)
     			listString.append(s);

     	return listString.toString();
	}
}