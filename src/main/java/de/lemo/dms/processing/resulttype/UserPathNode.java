package de.lemo.dms.processing.resulttype;

public class UserPathNode {

    private String name;
    private String title;
    private Long value;
    private Long group;
    private Long pathId;

    public Long getPathId() {
		return pathId;
	}

	public void setPathId(Long pathId) {
		this.pathId = pathId;
	}

	public UserPathNode() {
    }

    public UserPathNode(UserPathObject path) {
        this.name = path.getTitle();
        if(name == null || name.isEmpty())
            name = "?";
        this.value = path.getWeight();
        this.group = path.getGroup();
        this.pathId = path.getPathId();
    }
    
    public UserPathNode(UserPathObject path, Boolean directedGraph) {
        this.name = path.getId();
        this.title = path.getTitle();
        if(name == null || name.isEmpty())
            name = "?";
        this.value = path.getWeight();
        this.group = path.getGroup();
        this.pathId = path.getPathId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
