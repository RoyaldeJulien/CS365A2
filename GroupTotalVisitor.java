public class GroupTotalVisitor implements Visitor {
    private int groupTotal;

    public GroupTotalVisitor() {
        groupTotal = 0;
    }

    public int getTotal() {
        return groupTotal;
    }

    @Override
    public void atGroup(Group G) {
        groupTotal++;
    }

    @Override
    public void atUser(User U) {
        return;
    }
}