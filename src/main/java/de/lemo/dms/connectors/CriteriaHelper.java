package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Restrictions;

public class CriteriaHelper {

private static int MAX = 999;

@SuppressWarnings("rawtypes")
public static org.hibernate.criterion.Criterion in(String propertyName, Collection<?> values) {
    org.hibernate.criterion.Criterion criterion = null;

    @SuppressWarnings("unchecked")
	List<?> l = new ArrayList(values);
    int listSize = l.size();
    for (int i = 0; i < l.size(); i += MAX) {
        List<?> subList;
        if (listSize > i + MAX) {
            subList = l.subList(i, (i + MAX));
        } else {
            subList = l.subList(i, listSize);
        }
        if (criterion != null) {
            criterion = Restrictions.or(criterion, Restrictions.in(propertyName, subList));
        } else {
            criterion = Restrictions.in(propertyName, subList);
        }
    }
    return criterion;
}
}
