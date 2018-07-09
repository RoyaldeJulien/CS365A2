import java.util.ArrayList;
import java.util.List;

public class PositivePercentageTotalVisitor implements Visitor {
    private int totalMessages;
    private int positiveMessages;
    private List<String> positiveWords;

    public PositivePercentageTotalVisitor() {
        totalMessages = 0;
        positiveMessages = 0;
        positiveWords = new ArrayList<>();
        positiveWords.add(":)");
        positiveWords.add(":D");
        positiveWords.add("yay");
        positiveWords.add(":-)");
        positiveWords.add("rofl");
        positiveWords.add("<3");
        positiveWords.add("lol");
        positiveWords.add("Lol");
        positiveWords.add("LOL");
    }

    public double getPercentage() {
        return positiveMessages/(double)totalMessages;
    }

    @Override
    public void atGroup(Group G) {
        return;
    }

    @Override
    public void atUser(User U) {
        totalMessages+= U.getMessages().size();
        for(String message: U.getMessages()) {
            for(String word : positiveWords) {
                if(message.contains(word)) {
                    positiveMessages++;
                    break;
                }
            }
        }
    }
}
