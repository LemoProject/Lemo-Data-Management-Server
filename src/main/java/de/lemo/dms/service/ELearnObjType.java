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
 * <em>Learning Object</em> types with corresponding {@link ILogMining}
 * implementations.
 * 
 * @author Leonard Kappe
 */
// TODO class name contains uncommon abbreviations and should be changed.
// Proposals: ELearningObjects or ELearningObjectTypes
// TODO class seems misplaced in this package, maybe move to processing where it
// is used the most
public enum ELearnObjType {

	ASSIGNMENT(AssignmentLogMining.class),
	COURSE(CourseLogMining.class),
	FORUM(
			ForumLogMining.class),
	QUESTION(QuestionLogMining.class),
	QUIZ(
			QuizLogMining.class),
	RESOURCE(ResourceLogMining.class),
	SCORM(
			ScormLogMining.class),
	WIKI(WikiLogMining.class),
	UNKNOWN(null);

	private Class<? extends ILogMining> type;

	private ELearnObjType(Class<? extends ILogMining> type) {
		this.type = type;
	}

	/**
	 * Gets the type that is used to store the Learning Object's log entries.
	 * 
	 * @return the type of the corresponding {@link ILogMining} implementation
	 */
	// TODO name is ambiguous, needs renaming
	public Class<? extends ILogMining> getType() {
		return type;
	}

	// TODO simplify this, we can simply loop though each enum instance without
	// performance overhead of hashmaps.
	private final static EnumHashBiMap<ELearnObjType, Class<? extends ILogMining>> typeMap = EnumHashBiMap
			.create(ELearnObjType.class);
	static {
		// static block is called after enum initialization
		for (ELearnObjType enumValue : values())
			if (enumValue.type != null)
				typeMap.put(enumValue, enumValue.type);
	}

	/**
	 * Maps enum constants to {@link ILogMining} classes.
	 * 
	 * @param log
	 *            an ILogMining implementation
	 * @return the corresponding enum constant
	 */
	// TODO should be renamed to avoid confusion with static
	// Enum.valueOf(String). Maybe to getEnum
	public static ELearnObjType valueOf(ILogMining log) {
		ELearnObjType type = typeMap.inverse().get(log.getClass());
		if (type == null)
			type = UNKNOWN;
		return type;
	}

	// TODO better just use valueOf(ILogMining) and check for !UNKNOWN
	@Deprecated
	public static boolean validate(ILogMining log, String typeName) {
		return valueOf(log).toString().equals(typeName);
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
