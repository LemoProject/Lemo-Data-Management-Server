/**
 * File ./src/main/java/de/lemo/dms/processing/MetaParam.java
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
 * File ./main/java/de/lemo/dms/processing/MetaParam.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing;

/**
 * Common parameter names used in REST services.
 * 
 * @author Leonard Kappe
 */
public interface MetaParam {

	String COURSE_IDS = "cid";
	String DEGREE = "degree";
	String DEPARTMENT = "dep";
	String END_TIME = "end";
	String LEARNING_OBJECT = "lo";
	String LOG_OBJECT_IDS = "oid";
	String LOGOUT_FLAG = "logout";
	String MAX_LENGTH = "max_length";
	String MIN_LENGTH = "min_length";
	String MIN_SUP = "min_sup";
	String RESOLUTION = "resolution";
	String ROLE_IDS = "rid";
	String SESSION_WISE = "session_wise";
	String START_TIME = "start";
	String TYPES = "types";
	String UNIQUE_USER = "unique_user";
	String USER_IDS = "uid";
	String USER_NAME = "uname";
	String QUIZ_IDS = "quiz_ids";
	String SEARCH_TEXT = "stext";
	String RESULT_AMOUNT = "res_amount";
	String OFFSET = "offset";
	String GENDER = "gender";
	String LEMO_USER_ID = "lemo_uid";
	String LEARNING_OBJ_IDS = "learning_obj_ids";
}
