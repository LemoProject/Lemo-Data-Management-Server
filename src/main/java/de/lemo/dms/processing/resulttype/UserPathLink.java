package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathLink {

    private String source;
    private String target;
    private String value;

    public UserPathLink() {
        // TODO Auto-generated constructor stub
    }
    
    public UserPathLink(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (source.length() ^ (source.length() >>> 32));
        result = prime * result + (int) (target.length() ^ (target.length() >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        UserPathLink other = (UserPathLink) obj;
        if(source != other.source)
            return false;
        if(target != other.target)
            return false;
        return true;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
