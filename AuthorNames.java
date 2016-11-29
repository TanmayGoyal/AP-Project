import java.util.*;

public class AuthorNames {
	ArrayList<String> alias = new ArrayList<String>();

	public void addAuthor(String name) {
		alias.add(name);
	}

	public ArrayList<String> getAlias() {
		return alias;
	}
}