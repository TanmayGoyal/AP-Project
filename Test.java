import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class Test {
	public static void main(String[] args) {
		try {
			File inputFile = new File("input1.xml");
	        InputStream inputStream = new FileInputStream(inputFile);
	        Reader reader = new InputStreamReader(inputStream);
	        InputSource is = new InputSource(reader);
	            
	        is.setEncoding("ISO-8859-1");
	         
	        System.setProperty("jdk.xml.entityExpansionLimit", "0");
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();

	        // ParseEntityResolution publ = new ParseEntityResolution();
	        SearchAuthorPublications publ = new SearchAuthorPublications("data", "2013", "2016");

	        saxParser.parse(is,publ);

	        String arr[][] = publ.getArray();

	        Arrays.sort(arr, new Comparator<String[]>() {
            @Override
            public int compare (final String[] entry1, final String[] entry2) {
                final String time1 = entry1[0];
                final String time2 = entry2[0];
                return time1.compareTo(time2);
            }
        });

        for (final String[] s : data) {
            System.out.println(s[0] + " " + s[1]);
        }

	        // for (int i=0; arr[i][0] != null ;i++) {
	        // 	System.out.println(arr[i][0] + arr[i][1] + arr[i][2] + arr[i][3] + arr[i][4] + arr[i][5] + arr[i][6] + arr[i][7]);
	        // }
	        publ.printData();
	        // publ.printData();
         
       }
       catch (Exception e) {
         e.printStackTrace();
         // return saxParser;
       }
	}
}