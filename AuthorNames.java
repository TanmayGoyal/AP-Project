import java.util.*;

/**
 *  The AuthorNames class.
 */
public class AuthorNames {
	ArrayList<String> alias = new ArrayList<String>();

	///This method adds a String to an ArrayList<String>.
	public void addAuthor(String name) {
		alias.add(name);
	}

	///This method returns an ArrayList<String>.
	public ArrayList<String> getAlias() {
		return alias;
	}
}