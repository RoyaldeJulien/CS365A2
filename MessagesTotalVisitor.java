public class MessagesTotalVisitor implements Visitor {
    private int totalMessages;

    public MessagesTotalVisitor() {
        totalMessages = 0;
    }

    public int getTotal() {
        return totalMessages;
    }

    @Override
    public void atGroup(Group G) {
        return;
    }

    @Override
    public void atUser(User U) {
        totalMessages += U.getMessages().size();
    }
}
