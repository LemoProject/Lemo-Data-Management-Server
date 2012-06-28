package de.lemo.dms.processing.resulttype;

public class UserPathNode {

    private String name;
    private Long value;
    private Long group;

    public UserPathNode() {
    }

    public UserPathNode(UserPathObject path) {
        this.name = path.getTitle();
        this.value = path.getWeight();
        this.group = path.getGroup();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

}
