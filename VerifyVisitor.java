import java.util.ArrayList;
import java.util.List;

public class VerifyVisitor implements Visitor {
	public List<String> badIDs;

	public VerifyVisitor() {
		badIDs = new ArrayList<>();
	}

	public String getResults() {
		return String.join(", ", badIDs);
	}

	@Override
	public void atGroup(Group G) {
		if(G.getID().contains(" ")) {
			badIDs.add(G.getID());
		}
	}

	@Override
	public void atUser(User U) {
		if(U.getID().contains(" ")) {
			badIDs.add(U.getID());
		}
	}
}
