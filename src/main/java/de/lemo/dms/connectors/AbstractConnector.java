package de.lemo.dms.connectors;


public abstract class AbstractConnector implements IConnector {

    private Long id;
    private String name;
    private Long prefix;
    private ESourcePlatform platform;
    
    @Override
    public Long getPlatformId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getPrefix() {
        return prefix;
    }

    public void setPrefix(Long prefix) {
        this.prefix = prefix;
    }

    @Override
    public ESourcePlatform getPlattformType() {
        return platform;
    }

    public void getPlattformType(ESourcePlatform plattformType) {
        this.platform = plattformType;
    }

    @Override
    public String toString() {
        return id + "-" + platform + "-" + name;
    }

}
