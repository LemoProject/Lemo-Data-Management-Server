package de.lemo.dms.core;

/**
 * TODO Remove this class and replace a few references in
 * {@link de.lemo.dms.processing.questions.QCumulativeUserAccess} with
 * {@link de.lemo.dms.service.ELearnObjType}
 */
@Deprecated
public enum LearningObjects {
	resource, assignment, chat, // kein chat, da keine verbindung zu kursen
	course, forum, question, quiz, scorm, wiki,
}
