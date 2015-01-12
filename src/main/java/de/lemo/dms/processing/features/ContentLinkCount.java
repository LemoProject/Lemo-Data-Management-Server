package de.lemo.dms.processing.features;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.lemo.dms.db.mapping.LearningAttribute;

public class ContentLinkCount extends ContentProcessor {

	public ContentLinkCount() {
		super("Content_Linkcount");
	}

	@Override
	protected void processContent(List<LearningAttribute> contentAttributes) {
		String regexp = generateRegExp();
		Pattern pattern = Pattern.compile(regexp);
		int counter;
		for(LearningAttribute learningAttribute : contentAttributes){
			counter = 0;
			Matcher matcher = pattern.matcher(learningAttribute.getValue());
			while (matcher.find()){
				counter++;				
			}
			learningAttribute.setValue(Integer.toString(counter));
		}
	}

	public String generateRegExp() {
		String[] regexp = {
				"(",
				"\\\\s|[^a-zA-Z0-9.\\\\+_\\\\/\"\\\\>\\\\-]|^",
				")(?:", // Main group
				"(", // 2. Email address (optional)
				"[a-zA-Z0-9\\\\+_\\\\-]+",
				"(?:",
				"\\\\.[a-zA-Z0-9\\\\+_\\\\-]+",
				")*@",
				")?(", // 3. Protocol (optional)
				"http:\\\\/\\\\/|https:\\\\/\\\\/|ftp:\\\\/\\\\/",
				")?(", // 4. Domain & Subdomains
				"(?:(?:[a-z0-9][a-z0-9_%\\\\-_+]*\\\\.)+)",
				")(", // 5. Top-level domain -
						// http://en.wikipedia.org/wiki/List_of_Internet_top-level_domains
				"(?:com|ca|co|edu|gov|net|org|dev|biz|cat|int|pro|tel|mil|aero|asia|coop|info|jobs|mobi|museum|name|post|travel|local|[a-z]{2})",
				")(", // 6. Port (optional)
				"(?::\\\\d{1,5})",
				")?(", // 7. Query string (optional)
				"(?:", "[\\\\/|\\\\?]", "(?:",
				"[\\\\-a-zA-Z0-9_%#*&+=~!?,;:.\\\\/]*", ")*", ")",
				"[\\\\-\\\\/a-zA-Z0-9_%#*&+=~]", "|", "\\\\/?", ")?", ")(", // 7.
																			// Character
																			// after
																			// the
																			// link
				"[^a-zA-Z0-9\\\\+_\\\\/\"\\\\<\\\\-]|$", ")" };
		StringBuilder builder = new StringBuilder();
		for (String s : regexp) {
			builder.append(s);
		}

		/*************************************************************
		 * \\S for not whitespace characters ^\" for all characters except "
		 * (add ? to list...) && for intersection of two character classes + for
		 * one or more occurrences
		 *************************************************************/
		String regexp2 = "http://[[\\S]&&[^\"]]+";
		String regexp3 = "[^.^@\\s]+\\.(com|de|it|us|ca|co|edu|gov|net|org|dev|biz|cat|int|pro|tel|mil|aero|asia|coop|info|jobs|mobi|museum|name|post|travel|local)";
		String regexp4 = "[^.\\s]+\\.(png|bmp|gif|jpg)";
		return regexp3;
	}

}
