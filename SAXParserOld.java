import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class SAXParserOld {
	public static void main(String[] args) {

		new SwingLayoutDemo();
		// try {
		// 	File inputFile = new File("dblp.xml");
		// 	InputStream inputStream = new FileInputStream(inputFile);
		// 	Reader reader = new InputStreamReader(inputStream);
		// 	InputSource is = new InputSource(reader);
		// 	is.setEncoding("ISO-8859-1");
		// 	System.setProperty("jdk.xml.entityExpansionLimit", "0");
		// 	SAXParserFactory factory = SAXParserFactory.newInstance();
  //       	SAXParser saxParser = factory.newSAXParser();
  //       	SearchAuthorPublication articlehandler = new SearchAuthorPublication("George W. Ernst", "1900");
  //       	saxParser.parse(is, articlehandler);

  //       	// for (ArrayList<Art(articlehandler.getArticleArray());
		// }
		// catch (Exception e) {
  //       	e.printStackTrace();
  //   	}
	}
}