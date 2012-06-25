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

    private final static EnumHashBiMap<ELearnObjType, Class<? extends ILogMining>> typeMap =
            EnumHashBiMap.create(ELearnObjType.class);
    static {
        // static is called after enum constructor
        for(ELearnObjType enumValue : values())
            if(enumValue.type != null)
                typeMap.put(enumValue, enumValue.type);
    }

    public static ELearnObjType valueOf(ILogMining log) {
        ELearnObjType type = typeMap.inverse().get(log.getClass());
        if(type == null)
            type = UNKNOWN;
        return type;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
