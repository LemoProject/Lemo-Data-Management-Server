package de.lemo.dms.connectors.chemgapedia.fizHelper;

public class SiteCounter implements Comparable<SiteCounter>{

	
	private String url;
	private int count = 1;
		
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public SiteCounter(String url)
	{
		this.url = url;
	}
	
	public SiteCounter(String url, int count)
	{
		this.url = url;
		this.count = count;
	}
	
	public void incCount()
	{
		this.count++;
	}
	
	@Override
	public boolean equals(Object sc)
	{
		try{
		if(sc == null)
			return false;
		SiteCounter l = (SiteCounter)sc;
		if(l.getUrl().equals(this.getUrl()))
			return true;
		}catch(Exception e)
		{
			return false;
		}
		return false;
	}


	@Override
	public int compareTo(SiteCounter arg0) {
		SiteCounter s;
		try{
			s = arg0;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.count > s.getCount())
			return 1;
		if(this.count < s.getCount())
			return -1;
		return 0;
	}
}
