/**
 * File ./src/main/java/de/lemo/dms/processing/ELearningObjectType.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/processing/ELearningObjectType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

/**
 * <em>Learning Object</em> types with corresponding {@link ILogMining} implementations.
 * 
 * @author Leonard Kappe
 */
public enum ELearningObjectType {

	ASSIGNMENT(AssignmentLogMining.class),
	CHAT(ChatLogMining.class),
	FORUM(ForumLogMining.class),
	QUESTION(QuestionLogMining.class),
	QUIZ(QuizLogMining.class),
	RESOURCE(ResourceLogMining.class),
	SCORM(ScormLogMining.class),
	WIKI(WikiLogMining.class), ;

	private Class<? extends ILogMining> type;

	private ELearningObjectType(final Class<? extends ILogMining> type) {
		this.type = type;
	}

	/**
	 * Gets the type that is used to store this Learning Object's log entries.
	 * 
	 * @return the type of the corresponding {@link ILogMining} implementation
	 */
	public Class<? extends ILogMining> getLogMiningType() {
		return this.type;
	}

	/**
	 * Gets enum constants from corresponding {@link ILogMining} classes.
	 * 
	 * @param log
	 *            an ILogMining implementation
	 * @return the corresponding enum constant
	 */
	public static ELearningObjectType fromLogMiningType(final ILogMining log) {
		for (final ELearningObjectType learnObjectType : ELearningObjectType.values()) {
			if (log != null && learnObjectType.getLogMiningType().equals(log.getClass())) {
				return learnObjectType;
			}
		}
		return null;
	}

	/**
	 * Gets a list of enum constants for the specified names .
	 * 
	 * @param names
	 *            case-insensitive names of enum constants to get
	 * @return the enum constants
	 */
	public static Set<ELearningObjectType> fromNames(final Collection<String> names) {
		final EnumSet<ELearningObjectType> result = EnumSet.noneOf(ELearningObjectType.class);
		for (final String name : names) {
			result.add(ELearningObjectType.valueOf(name.toUpperCase()));
		}
		return result;
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
