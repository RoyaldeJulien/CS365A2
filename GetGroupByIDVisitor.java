public class GetGroupByIDVisitor implements Visitor {
    private String targetID;
    private Group target;

    public GetGroupByIDVisitor(String ID) {
        targetID = ID;
    }

    public Group getTarget() {
        return target;
    }

    @Override
    public void atGroup(Group G) {
        if (G.getID().equals(targetID)) {
            target = G;
        }
    }

    @Override
    public void atUser(User U) {
        return;
    }
}