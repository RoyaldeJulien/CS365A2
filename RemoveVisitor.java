public class RemoveVisitor implements Visitor {
    private Group toBeRemoved;

    public RemoveVisitor(Group G) {
        toBeRemoved = G;
    }

    @Override
    public void atGroup(Group G) {
        if (G.hasChild(toBeRemoved)) {
            G.removeChild(toBeRemoved);
        }
    }

    @Override
    public void atUser(User U) {
        return;
    }
}
