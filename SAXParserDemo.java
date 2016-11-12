import org.xml.sax.InputSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class SAXParserDemo {
	public static void main(String[] args) {
		try {
			File inputFile = new File("dblp.xml");
			InputStream inputStream = new FileInputStream(inputFile);
			Reader reader = new InputStreamReader(inputStream);
			InputSource is = new InputSource(reader);
			is.setEncoding("ISO-8859-1");
			System.setProperty("jdk.xml.entityExpansionLimit", "0");
			SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser saxParser = factory.newSAXParser();
        	ArticleHandler articlehandler = new ArticleHandler();
        	saxParser.parse(is, articlehandler);
		}
		catch (Exception e) {
        	e.printStackTrace();
    	}
	}
}