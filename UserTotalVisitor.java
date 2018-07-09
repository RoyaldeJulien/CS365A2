public class UserTotalVisitor implements Visitor {
    private int userTotal;

    public UserTotalVisitor() {
        userTotal = 0;
    }

    public int getTotal() {
        return userTotal;
    }

    @Override
    public void atGroup(Group G) {
        return;
    }

    @Override
    public void atUser(User U) {
        userTotal++;
    }

}
