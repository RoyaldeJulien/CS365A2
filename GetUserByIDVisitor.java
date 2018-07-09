public class GetUserByIDVisitor implements Visitor {
    private String targetID;
    private User target;

    public GetUserByIDVisitor(String ID) {
        targetID = ID;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public void atGroup(Group G) {
        return;
    }

    @Override
    public void atUser(User U) {
        if (U.getID().equals(targetID)) {
            target = U;
        }
    }
}
