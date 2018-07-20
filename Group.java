import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {
	private long creationTime;
    private String ID;
    private Set<Group> children;

    public Group(String ID) {
        this.ID = ID;
        children = new HashSet<>();
        creationTime = System.currentTimeMillis();
    }

    public Long getCreationTime() {
    	return creationTime;
    }

    public String getID() {
        return ID;
    }

    public void addChild(Group G) {
        this.children.add(G);
    }

    public void removeChild(Group G) {
        this.children.remove(G);
    }

    public boolean hasChild(Group G) {
        return this.children.contains(G);
    }

    @Override
    public boolean equals(Object G) {
        if(G instanceof Group) {
            if(this.ID.equals(((Group)G).ID)) {
                return true;
            }
        }
        return false;
    }

    public void accept(Visitor V) {
        V.atGroup(this);
        for(Group subgroup: children) {
            subgroup.accept(V);
        }
    }

    public String toString() {
        return String.join("\n", toStringList());
    }

    public String[] toStringArray() {
        return toStringList().toArray(new String[0]);
    }

    public List<String> toStringList() {
        List<String> list = new ArrayList<>();
        toStringListRecurse(list, this, "");
        return list;
    }

    private void toStringListRecurse(List<String> list, Group G, String Buffer) {
        list.add(Buffer + G.ID);
        for(Group g : G.children) {
            toStringListRecurse(list, g, Buffer + "    ");
        }
    }
}
