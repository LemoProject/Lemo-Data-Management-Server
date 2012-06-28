package de.lemo.dms.service;

import com.google.common.collect.EnumHashBiMap;

import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

/**
 * <em>Learn Object</em> types with corresponding {@link ILogMining} implementations.
 * 
 * @author Leonard Kappe
 * 
 */
public enum ELearnObjType {

    ASSIGNMENT(AssignmentLogMining.class),
    COURSE(CourseLogMining.class),
    FORUM(ForumLogMining.class),
    QUESTION(QuestionLogMining.class),
    QUIZ(QuizLogMining.class),
    RESOURCE(ResourceLogMining.class),
    SCORM(ScormLogMining.class),
    WIKI(WikiLogMining.class),
    UNKNOWN(null);

    private Class<? extends ILogMining> type;

    private ELearnObjType(Class<? extends ILogMining> type) {
        this.type = type;
    }

    public Class<? extends ILogMining> getType() {
        return type;
    }

    private final static EnumHashBiMap<ELearnObjType, Class<? extends ILogMining>> typeMap =
            EnumHashBiMap.create(ELearnObjType.class);
    static {
        // static block is called after enum initialization
        for(ELearnObjType enumValue : values())
            if(enumValue.type != null)
                typeMap.put(enumValue, enumValue.type);
    }

    /**
     * Maps enum constants to ILogMining classes
     * 
     * @param log
     *            an ILogMining implementation
     * @return the corresponding enum constant
     */
    public static ELearnObjType valueOf(ILogMining log) {
        ELearnObjType type = typeMap.inverse().get(log.getClass());
        if(type == null)
            type = UNKNOWN;
        return type;
    }

    /**
     * 
     * 
     * @param log
     * @param typeName
     * @return
     */
    public static boolean validate(ILogMining log, String typeName) {
        return valueOf(log).toString().equals(typeName);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
