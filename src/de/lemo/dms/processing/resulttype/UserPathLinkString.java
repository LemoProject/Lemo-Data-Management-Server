package de.lemo.dms.processing.resulttype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPathLinkString {

    private String source;
    private String target;
    private long value;

    public UserPathLinkString() {
        // TODO Auto-generated constructor stub
    }
    
    public UserPathLinkString(String source, String target) {
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
        UserPathLinkString other = (UserPathLinkString) obj;
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
