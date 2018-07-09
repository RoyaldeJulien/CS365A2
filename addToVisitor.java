public class addToVisitor implements Visitor {
    private Group groupToAdd;
    private Group containerToAddTo;

    public addToVisitor(Group G, Group C) {
        groupToAdd = G;
        containerToAddTo = C;
    }

    @Override
    public void atGroup(Group G) {
        if (G.equals(containerToAddTo)) {
            G.addChild(groupToAdd);
        }
    }
    @Override
    public void atUser(User U) {
        return;
    }
}