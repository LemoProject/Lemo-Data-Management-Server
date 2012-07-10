package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathLink {

    private long source;
    private long target;
    private long value;

    public UserPathLink() {
        // TODO Auto-generated constructor stub
    }
    
    public UserPathLink(long source, long target) {
        this.source = source;
        this.target = target;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public long getTarget() {
        return target;
    }

    public void setTarget(long target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (source ^ (source >>> 32));
        result = prime * result + (int) (target ^ (target >>> 32));
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
