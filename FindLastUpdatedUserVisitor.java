
public class FindLastUpdatedUserVisitor implements Visitor {
	private User lastUpdated;

	public FindLastUpdatedUserVisitor() {
		lastUpdated = null;
	}

	public User getResults() {
		return lastUpdated;
	}

	@Override
	public void atGroup(Group G) {
		return;
	}

	@Override
	public void atUser(User U) {
		if (lastUpdated == null) {
			lastUpdated = U;
		}
		else if (lastUpdated.getLastUpdateTime() < U.getLastUpdateTime()) {
			lastUpdated = U;
		}
	}
}
