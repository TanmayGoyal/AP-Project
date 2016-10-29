import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserDemo {
	public static void main(String[] args) {
		try {
			File inputFile = new File("dblp.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser saxParser = factory.newSAXParser();
        	ArticleHandler articlehandler = new ArticleHandler();
        	saxParser.parse(inputFile, articlehandler);
		}
		catch (Exception e) {
        	e.printStackTrace();
    	}
	}
}