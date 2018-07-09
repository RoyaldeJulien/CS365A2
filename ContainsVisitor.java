public class ContainsVisitor implements Visitor {
    private Group target;
    private boolean found;

    public ContainsVisitor(Group G) {
        target = G;
    }

    public boolean found() {
        return found;
    }

    @Override
    public void atGroup(Group G) {
        if (target.equals(G)) {
            found = true;;
        }
    }

    @Override
    public void atUser(User U) {
        atGroup(U);
    }
}
