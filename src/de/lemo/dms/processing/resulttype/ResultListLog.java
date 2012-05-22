package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

@XmlRootElement
public class ResultListLog {

    @XmlElement
    private JSONArray logs = new JSONArray();

    public ResultListLog() {
        /* JAXB no-arg default constructor */
    }

    public ResultListLog(List<ILogMining> dbLog) {
        try {
            for(ILogMining log : dbLog) {
                JSONObject logJSON = new JSONObject();
                logJSON.put("id", log.getId());
                logJSON.put("time", log.getTimestamp());
                logJSON.put("action", log.getAction());
                logJSON.put("course", log.getCourse());
                logJSON.put("user", log.getUser());
                
                logs.put(logJSON);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
