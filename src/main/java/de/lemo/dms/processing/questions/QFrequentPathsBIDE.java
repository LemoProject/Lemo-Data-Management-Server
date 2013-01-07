package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.MAX_LENGTH;
import static de.lemo.dms.processing.MetaParam.MIN_LENGTH;
import static de.lemo.dms.processing.MetaParam.MIN_SUP;
import static de.lemo.dms.processing.MetaParam.SESSION_WISE;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;
import static de.lemo.dms.processing.MetaParam.USER_IDS;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ca.pfv.spmf.sequentialpatterns.AlgoBIDEPlus;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

@Path("frequentPaths")
public class QFrequentPathsBIDE extends Question {

    private static HashMap<String, ILogMining> idToLogM = new HashMap<String, ILogMining>();
    private static HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();

    @POST
    public ResultListUserPathGraph compute(
            @FormParam(COURSE_IDS) List<Long> courses,
            @FormParam(USER_IDS) List<Long> users,
            @FormParam(TYPES) List<String> types,
            @FormParam(MIN_LENGTH) Long minLength,
            @FormParam(MAX_LENGTH) Long maxLength,
            @FormParam(MIN_SUP) Double minSup,
            @FormParam(SESSION_WISE) boolean sessionWise,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime) {

        ArrayList<UserPathNode> nodes = Lists.newArrayList();
        ArrayList<UserPathLink> links = Lists.newArrayList();

        if(courses != null && courses.size() > 0)
        {
            System.out.print("Parameter list: Courses: " + courses.get(0));
            for(int i = 1; i < courses.size(); i++)
                System.out.print(", " + courses.get(i));
            System.out.println();
        }
        if(users != null && users.size() > 0)
        {
            System.out.print("Parameter list: Users: " + users.get(0));
            for(int i = 1; i < users.size(); i++)
                System.out.print(", " + users.get(i));
            System.out.println();
        }
        if(types != null && types.size() > 0)
        {
            System.out.print("Parameter list: Types: : " + types.get(0));
            for(int i = 1; i < types.size(); i++)
                System.out.print(", " + types.get(i));
            System.out.println();
        }
        if(minLength != null && maxLength != null && minLength < maxLength)
        {
            System.out.println("Parameter list: Minimum path length: : " + minLength);
            System.out.println("Parameter list: Maximum path length: : " + maxLength);
        }
        System.out.println("Parameter list: Minimum Support: : " + minSup);
        System.out.println("Parameter list: Session Wise: : " + sessionWise);
        System.out.println("Parameter list: Start time: : " + startTime);
        System.out.println("Parameter list: End time: : " + endTime);

        try {

            // Session session = dbHandler.getMiningSession();

            /**
             * FileWriter out = new FileWriter("./"+System.currentTimeMillis()+"_BIDEresults.txt"); PrintWriter pout = new
             * PrintWriter(out);
             * 
             * //Write header for the output file pout.println("# LeMo - Sequential pattern data");
             **/

            SequenceDatabase sequenceDatabase = new SequenceDatabase();

            if(!sessionWise)
                sequenceDatabase.loadLinkedList(generateLinkedList(courses, users, types, minLength, maxLength,
                    startTime, endTime));
            // sequenceDatabase.loadFile(generateInputFile(courseIds, userIds, startTime, endTime));
            else
                // sequenceDatabase.loadFile(generateInputFileSessionBound(courseIds, userIds, startTime, endTime));
                sequenceDatabase.loadLinkedList(generateLinkedListSessionBound(courses, users, types, minLength,
                    maxLength, startTime, endTime));

            AlgoBIDEPlus algo = new AlgoBIDEPlus(minSup);

            // execute the algorithm
            Clock c = new Clock();
            Sequences res = algo.runAlgorithm(sequenceDatabase);
            System.out.println("Time for BIDE-calculation: " + c.get());

            LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
            Long pathId = 0L;
            System.out.println();
            for(int i = 0; i < res.getLevelCount(); i++)
            {
                for(int j = 0; j < res.getLevel(i).size(); j++)
                {
                    String predecessor = null;
                    Long absSup = Long.valueOf(res.getLevel(i).get(j).getAbsoluteSupport());
                    pathId++;
                    System.out.println("New " + i + "-Sequence. Support : "
                            + res.getLevel(i).get(j).getAbsoluteSupport());
                    for(int k = 0; k < res.getLevel(i).get(j).size(); k++)
                    {

                        String obId = String.valueOf(res.getLevel(i).get(j).get(k).getItems().get(0).getId());
                        ILogMining ilo = idToLogM.get(obId.substring(0, 2) + " " + obId.substring(2));

                        String type = ilo
                                .getClass()
                                .toString()
                                .substring(ilo.getClass().toString().lastIndexOf(".") + 1,
                                    ilo.getClass().toString().lastIndexOf("Log"));

                        String posId = String.valueOf(pathObjects.size());

                        if(predecessor != null)
                        {
                            pathObjects.put(
                                posId,
                                new UserPathObject(posId, ilo.getTitle(), absSup, type,
                                        Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId,
                                        Long.valueOf(requests.get(obId).size()), Long.valueOf(new HashSet<Long>(
                                                requests.get(obId)).size())));

                            // Increment or create predecessor edge
                            pathObjects.get(predecessor).addEdgeOrIncrement(posId);

                        }
                        else
                        {
                            pathObjects.put(
                                posId,
                                new UserPathObject(posId, ilo.getTitle(), absSup,
                                        type, Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId, Long
                                                .valueOf(requests.get(obId).size()), Long.valueOf(new HashSet<Long>(
                                                requests.get(obId)).size())));
                        }

                        predecessor = posId;
                    }

                }
            }
            System.out.println("\n");

            for(UserPathObject pathEntry : pathObjects.values()) {

                UserPathObject path = pathEntry;
                path.setWeight(path.getWeight());
                path.setPathId(pathEntry.getPathId());
                nodes.add(new UserPathNode(path, true));
                String sourcePos = path.getId();

                for(Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet()) {
                    UserPathLink link = new UserPathLink();
                    link.setSource(sourcePos);
                    link.setPathId(path.getPathId());
                    link.setTarget(linkEntry.getKey());
                    link.setValue(String.valueOf(linkEntry.getValue()));
                    links.add(link);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            requests.clear();
            idToLogM.clear();
        }
        return new ResultListUserPathGraph(nodes, links);
    }

    /**
     * Generates the necessary input file, containing the sequences (user paths) for the BIDE+ algorithm
     * 
     * @param courses
     *            Course-Ids
     * @param users
     *            User-Ids
     * @param starttime
     *            Start time
     * @param endtime
     *            End time
     * 
     * @return The path to the generated file
     */
    @SuppressWarnings("unchecked")
    private static String generateInputFile(List<Long> courses, List<Long> users, Long starttime, Long endtime)
    {
        String output = "./" + System.currentTimeMillis() + "_BIDEInput.txt";
        try {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

            Session session = dbHandler.getMiningSession();

            Criteria criteria = session.createCriteria(ILogMining.class, "log");
            if(courses.size() > 0)
                criteria.add(Restrictions.in("log.course.id", courses));
            if(users.size() > 0)
                criteria.add(Restrictions.in("log.user.id", users));
            criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
            ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

            System.out.println("Read " + list.size() + " logs.");

            int max = 0;

            HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i) != null && list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
                    if(logMap.get(list.get(i).getUser().getId()) == null)
                    {
                        ArrayList<ILogMining> a = new ArrayList<ILogMining>();
                        a.add(list.get(i));
                        logMap.put(list.get(i).getUser().getId(), a);
                        if(logMap.get(list.get(i).getUser().getId()).size() > max)
                            max = logMap.get(list.get(i).getUser().getId()).size();
                    }
                    else
                    {
                        logMap.get(list.get(i).getUser().getId()).add(list.get(i));
                        Collections.sort(logMap.get(list.get(i).getUser().getId()));
                        if(logMap.get(list.get(i).getUser().getId()).size() > max)
                            max = logMap.get(list.get(i).getUser().getId()).size();
                    }
            }

            ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>(logMap.values());

            System.out.println("Generated " + uhis.size() + " user histories. Max length @ " + max);

            Integer[] lengths = new Integer[max / 10 + 1];
            for(int i = 0; i < lengths.length; i++)
                lengths[i] = 0;

            for(int i = 0; i < uhis.size(); i++)
            {
                lengths[uhis.get(i).size() / 10]++;
            }

            FileWriter out = new FileWriter(output);

            PrintWriter pout = new PrintWriter(out);

            pout.println("# LeMo - Sequential pattern data");
            pout.println("#");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy - HH:mm:ss");

            pout.println("# Generated @ " + sdf.format(d));
            pout.println("# Containing " + uhis.size() + " user histories. Max length @ " + max);
            pout.println("#");
            for(int i = 0; i < lengths.length; i++)
                if(lengths[i] != 0)
                {
                    pout.println("# Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                    System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                }
            pout.println("#");

            int z = 0;
            for(Iterator<ArrayList<ILogMining>> iter = uhis.iterator(); iter.hasNext();)
            {
                ArrayList<ILogMining> l = iter.next();
                if(l.size() > 5)
                {
                    for(int i = 0; i < l.size(); i++)
                    {
                        if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
                            idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
                        pout.print(l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ");
                    }
                    pout.println("-2");
                    z++;
                }
            }

            // session.close();
            pout.close();
            System.out.println("Wrote " + z + " logs.");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Generates the necessary input file, containing the sequences (user paths) for the BIDE+ algorithm
     * 
     * @param courses
     *            Course-Ids
     * @param users
     *            User-Ids
     * @param starttime
     *            Start time
     * @param endtime
     *            End time
     * 
     * @return The path to the generated file
     */
    @SuppressWarnings("unchecked")
    private static String generateInputFileSessionBound(List<Long> courses, List<Long> users, Long starttime,
            Long endtime)
    {
        String output = "./" + System.currentTimeMillis() + "_BIDEInput_sb.txt";
        try {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

            Session session = dbHandler.getMiningSession();

            Criteria criteria = session.createCriteria(ILogMining.class, "log");
            if(courses.size() > 0)
                criteria.add(Restrictions.in("log.course.id", courses));
            if(users.size() > 0)
                criteria.add(Restrictions.in("log.user.id", users));
            criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
            ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

            System.out.println("Read " + list.size() + " logs.");
            ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();

            HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
            int max = 0;
            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
                    if(logMap.get(list.get(i).getUser().getId()) == null)
                    {
                        ArrayList<ILogMining> a = new ArrayList<ILogMining>();
                        a.add(list.get(i));
                        logMap.put(list.get(i).getUser().getId(), a);
                        if(logMap.get(list.get(i).getUser().getId()).size() > max)
                            max = logMap.get(list.get(i).getUser().getId()).size();
                    }
                    else
                    {

                        logMap.get(list.get(i).getUser().getId()).add(list.get(i));
                        Collections.sort(logMap.get(list.get(i).getUser().getId()));
                        if(logMap.get(list.get(i).getUser().getId()).size() > max)
                            max = logMap.get(list.get(i).getUser().getId()).size();
                        if(list.get(i).getDuration() == -1)
                        {
                            uhis.add(new ArrayList<ILogMining>(logMap.get(list.get(i).getUser().getId())));
                            logMap.remove(list.get(i).getUser().getId());
                        }
                    }
            }

            uhis.addAll(logMap.values());

            LinkedList<String> result = new LinkedList<String>();

            Integer[] lengths = new Integer[max + 1];
            for(int i = 0; i < lengths.length; i++)
                lengths[i] = 0;

            for(int i = 0; i < uhis.size(); i++)
            {
                lengths[uhis.get(i).size() / 10 + 1]++;
            }

            System.out.println("Generated " + uhis.size() + " user histories. Max length @ " + max);
            FileWriter out = new FileWriter(output);
            PrintWriter pout = new PrintWriter(out);

            // Write header for the output file
            pout.println("# LeMo - Sequential pattern data");

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy - HH:mm:ss");

            pout.println("# Generated @ " + sdf.format(d));
            pout.println("# Containing " + uhis.size() + " user histories. Max length @ " + max);

            for(int i = 0; i < lengths.length; i++)
                if(lengths[i] != 0)
                {
                    pout.println("# Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                    System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                }

            int z = 0;

            // Write data to output file
            for(Iterator<ArrayList<ILogMining>> iter = uhis.iterator(); iter.hasNext();)
            {
                ArrayList<ILogMining> l = iter.next();
                if(l.size() > 5)
                {
                    String line = "";
                    for(int i = 0; i < l.size(); i++)
                    {
                        if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
                            idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
                        line += l.get(i).getLearnObjId() + " -1 ";
                        pout.print(l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ");
                    }
                    line += "-2";
                    pout.println("-2");
                    result.add(line);
                    z++;
                }

            }

            // session.close();
            pout.close();
            System.out.println("Wrote " + z + " user histories.");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * This doesn't work at all; you shouldn't use it without major overhauling Generates the necessary input file,
     * containing the sequences (user paths) for the BIDE+ algorithm
     * 
     * @param courses
     *            Course-Ids
     * @param users
     *            User-Ids
     * @param starttime
     *            Start time
     * @param endtime
     *            End time
     * 
     * @return The path to the generated file
     */
    @SuppressWarnings("unchecked")
    private static LinkedList<String> generateLinkedListSessionBound(List<Long> courses, List<Long> users,
            List<String> types, Long minLength, Long maxLength, Long starttime, Long endtime)
    {
        LinkedList<String> result = new LinkedList<String>();
        try {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

            Session session = dbHandler.getMiningSession();

            Criteria criteria = session.createCriteria(ILogMining.class, "log");
            if(courses.size() > 0)
                criteria.add(Restrictions.in("log.course.id", courses));
            if(users.size() > 0)
                criteria.add(Restrictions.in("log.user.id", users));
            criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
            ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

            System.out.println("Read " + list.size() + " logs.");
            ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();
            HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
            int max = 0;
            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
                    if(logMap.get(list.get(i).getUser().getId()) == null)
                    {

                        ArrayList<ILogMining> a = new ArrayList<ILogMining>();
                        a.add(list.get(i));
                        logMap.put(list.get(i).getUser().getId(), a);
                    }
                    else
                    {
                        logMap.get(list.get(i).getUser().getId()).add(list.get(i));
                        Collections.sort(logMap.get(list.get(i).getUser().getId()));
                        if(list.get(i).getDuration() == -1)
                        {
                            uhis.add(new ArrayList<ILogMining>(logMap.get(list.get(i).getUser().getId())));
                            logMap.remove(list.get(i).getUser().getId());
                        }
                    }
            }

            // Just changing the container for the user histories
            if(types == null || types.size() == 0)
                uhis = new ArrayList<ArrayList<ILogMining>>(logMap.values());
            else
            {
                uhis = new ArrayList<ArrayList<ILogMining>>();
                for(ArrayList<ILogMining> uh : logMap.values())
                {
                    boolean hasTypes = false;
                    for(ILogMining log : uh)
                    {
                        for(String s : types)
                        {
                            if(log.getClass().getName().toLowerCase().contains(s.toLowerCase()))
                                hasTypes = true;
                            if(hasTypes)
                                break;
                        }
                        if(hasTypes)
                        {
                            uhis.add(uh);
                            break;
                        }
                    }
                }
            }

            uhis.addAll(logMap.values());

            // Determine whether minimum and maximum path length are set
            boolean hasBorders = minLength != null && maxLength != null && maxLength > 0 && minLength < maxLength;

            // Determine longest path
            for(int i = 0; i < uhis.size(); i++)
            {
                int s = uhis.get(i).size();
                if(hasBorders)
                {
                    if(s >= minLength && s <= maxLength && s > max)
                        max = s;
                } else if(s > max)
                    max = s;
            }

            // This part is only for statistics - group histories of similar length together and display there respective
            // lengths
            Integer[] lengths = new Integer[max / 10 + 1];
            for(int i = 0; i < lengths.length; i++)
                lengths[i] = 0;
            int uhisCount = 0;

            for(int i = 0; i < uhis.size(); i++)
            {
                if(hasBorders)
                {
                    if(uhis.size() >= minLength && uhis.size() <= maxLength)
                    {
                        lengths[uhis.get(i).size() / 10]++;
                        uhisCount++;
                    }
                }
                else
                {
                    lengths[uhis.get(i).size() / 10]++;
                    uhisCount++;
                }

            }

            for(int i = 0; i < uhis.size(); i++)
            {
                if(hasBorders)
                {
                    if(uhis.size() >= minLength && uhis.size() <= maxLength)
                    {
                        lengths[uhis.get(i).size() / 10]++;
                        uhisCount++;
                    }
                }
                else
                {
                    lengths[uhis.get(i).size() / 10]++;
                    uhisCount++;
                }
            }

            for(int i = 0; i < lengths.length; i++)
                if(lengths[i] != 0)
                {
                    System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                }

            System.out.println("Generated " + uhisCount + " user histories. Max length @ " + max);

            int z = 0;

            // Write data to output file
            for(ArrayList<ILogMining> l : uhis)
            {
                if(!hasBorders || (l.size() >= minLength && l.size() <= maxLength))
                {
                    String line = "";
                    for(int i = 0; i < l.size(); i++)
                    {
                        if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
                            idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));

                        if(requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
                        {
                            ArrayList<Long> us = new ArrayList<Long>();
                            us.add(l.get(i).getUser().getId());
                            requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
                        }
                        else
                            requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(
                                l.get(i).getUser().getId());

                        line += l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ";
                    }
                    line += "-2";
                    result.add(line);
                    z++;
                }

            }
            System.out.println("Wrote " + z + " user histories.");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Generates the necessary input file, containing the sequences (user paths) for the BIDE+ algorithm
     * 
     * @param courses
     *            Course-Ids
     * @param users
     *            User-Ids
     * @param starttime
     *            Start time
     * @param endtime
     *            End time
     * 
     * @return The path to the generated file
     */
    @SuppressWarnings("unchecked")
    private static LinkedList<String> generateLinkedList(List<Long> courses, List<Long> users, List<String> types,
            Long minLength, Long maxLength, Long starttime, Long endtime)
    {
        LinkedList<String> result = new LinkedList<String>();
        try {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();

            Session session = dbHandler.getMiningSession();

            Criteria criteria = session.createCriteria(ILogMining.class, "log");
            if(courses.size() > 0)
                criteria.add(Restrictions.in("log.course.id", courses));
            if(users.size() > 0)
                criteria.add(Restrictions.in("log.user.id", users));
            criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
            ArrayList<ILogMining> list = (ArrayList<ILogMining>) criteria.list();

            System.out.println("Read " + list.size() + " logs.");

            int max = 0;

            HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();

            // int pre = 1000;

            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
                    // If there isn't a user history for this user-id create a new one
                    if(logMap.get(list.get(i).getUser().getId()) == null)
                    {
                        // User histories are saved in an ArrayList of ILogMining-objects
                        ArrayList<ILogMining> a = new ArrayList<ILogMining>();
                        // Add current ILogMining-object to user-history
                        a.add(list.get(i));
                        // Add user history to the user history map
                        logMap.put(list.get(i).getUser().getId(), a);

                        // If it is the longest user history, save its length
                        if(logMap.get(list.get(i).getUser().getId()).size() > max)
                            max = logMap.get(list.get(i).getUser().getId()).size();
                    }
                    else
                    {
                        // Add current ILogMining-object to user-history
                        logMap.get(list.get(i).getUser().getId()).add(list.get(i));
                        // Sort the user's history (by time stamp)
                        Collections.sort(logMap.get(list.get(i).getUser().getId()));

                    }
            }

            ArrayList<ArrayList<ILogMining>> uhis;

            // Just changing the container for the user histories
            if(types == null || types.size() == 0)
                uhis = new ArrayList<ArrayList<ILogMining>>(logMap.values());
            else
            {
                uhis = new ArrayList<ArrayList<ILogMining>>();
                for(ArrayList<ILogMining> uh : logMap.values())
                {
                    boolean hasTypes = false;
                    for(ILogMining log : uh)
                    {
                        for(String s : types)
                        {
                            if(log.getClass().getName().toLowerCase().contains(s.toLowerCase()))
                                hasTypes = true;
                            if(hasTypes)
                                break;
                        }
                        if(hasTypes)
                        {
                            uhis.add(uh);
                            break;
                        }
                    }
                }
            }

            // Determine whether minimum and maximum path length are set
            boolean hasBorders = minLength != null && maxLength != null && maxLength > 0 && minLength < maxLength;

            // Determine longest path
            for(int i = 0; i < uhis.size(); i++)
            {
                int s = uhis.get(i).size();
                if(hasBorders)
                {
                    if(s >= minLength && s <= maxLength && s > max)
                        max = s;
                } else if(s > max)
                    max = s;
            }

            // This part is only for statistics - group histories of similar length together and display there respective
            // lengths
            Integer[] lengths = new Integer[max / 10 + 1];
            for(int i = 0; i < lengths.length; i++)
                lengths[i] = 0;
            int uhisCount = 0;

            for(int i = 0; i < uhis.size(); i++)
            {
                int s = uhis.get(i).size();
                if(hasBorders)
                {
                    if(s >= minLength && s <= maxLength)
                    {
                        lengths[s / 10]++;
                        uhisCount++;
                    }
                }
                else
                {
                    lengths[s / 10]++;
                    uhisCount++;
                }

            }
            for(int i = 0; i < lengths.length; i++)
                if(lengths[i] != 0)
                {
                    System.out.println("Paths of length " + i + "0 - " + (i + 1) + "0: " + lengths[i]);
                }

            System.out.println("Generated " + uhisCount + " user histories. Max length @ " + max);

            int z = 0;

            // Convert all user histories or "paths" into the format, that is requested by the BIDE-algorithm-class
            for(ArrayList<ILogMining> l : uhis)
            {
                if(!hasBorders || (l.size() >= minLength && l.size() <= maxLength))
                {
                    String line = "";
                    for(int i = 0; i < l.size(); i++)
                    {
                        if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
                            idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));

                        // Update request numbers
                        if(requests.get(l.get(i).getPrefix() + "" + l.get(i).getLearnObjId()) == null)
                        {
                            ArrayList<Long> us = new ArrayList<Long>();
                            us.add(l.get(i).getUser().getId());
                            requests.put(l.get(i).getPrefix() + "" + l.get(i).getLearnObjId(), us);
                        }
                        else
                            requests.get(l.get(i).getPrefix() + "" + l.get(i).getLearnObjId()).add(
                                l.get(i).getUser().getId());
                        // The id of the object gets the prefix, indicating it's class. This is important for distinction
                        // between objects of different ILogMining-classes but same ids
                        line += l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ";
                    }
                    line += "-2";
                    result.add(line);
                    z++;
                }
            }
            System.out.println("Wrote " + z + " logs.");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
