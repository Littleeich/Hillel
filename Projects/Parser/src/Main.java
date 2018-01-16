import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static int amountOfDaysInSegment = 14;

    public static void main(String[] args) throws ParseException {

        // PLEASE MAKE AUTOMATIC LOG FILE DETECTION WITHIN SUPPLIED FOLDER

        ArrayList<String> pathToFiles = new ArrayList<>();
        pathToFiles.add("C:\\Users\\Eich\\Downloads\\transactionsLog_0.txt");
        pathToFiles.add("C:\\Users\\Eich\\Downloads\\transactionsLog_1.txt");
        String[] path = pathToFiles.toArray(new String[pathToFiles.size()]);
//        String[] path = args;

        ArrayList<Date> dateCollect;
        HashSet<String> filteredFile = transactionFilter(readFile(path));

        dateCollect = dateCollection(filteredFile, amountOfDaysInSegment);
//        System.out.println(dateCollect);

        HashMap<Date, HashMap<String, Integer>> beforeResult;
        beforeResult = parseLogReader(stringConcatinator(dateCollect, filteredFile));
        putResultToTheConsole(dateCollect, beforeResult);
        putResultToTheFile("C:\\Users\\Eich\\IdeaProjects\\Lesson4\\file404.txt", dateCollect, beforeResult);

    }

    private static String readFile(String[] path){

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < path.length; i++)
            {
            String FILENAME = path[i];

            BufferedReader br = null;
            FileReader fr = null;

            try {
                fr = new FileReader(FILENAME);
                br = new BufferedReader(fr);

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    if(sCurrentLine.trim().startsWith("Warning") || sCurrentLine.trim().startsWith("Asset"));
                    else
                        sb.append(sCurrentLine + "\n");
                }

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (br != null)
                        br.close();

                    if (fr != null)
                        fr.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }

            }
        }
        return sb.toString();
    }

    private static HashSet<String> transactionFilter(String allText){
        HashSet<String> result = new HashSet<>();
        String[] temp = allText.split("Log ");
        for(String t : temp) {
            if(t.contains("Captured transactions:") && !t.contains("Captured transactions: None"))
                result.add("Log " + t);
        }
        return result;
    }

    private static String getIDfromString(String temp){
        String[] array = temp.split("Captured transactions: ");
        StringBuffer result = new StringBuffer();
        for(int i = 1; i < array.length; i++){
            result.append(array[i].trim());
            result.append(",");
        }
        return result.toString();
    }

    private static Date convertStringToDate(String d) throws ParseException{
        String sDate = d.replace("Log time: ", "").split(" GMT")[0];
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.US);
        return format.parse(sDate);
    }

    private static Date secondConvertStringToDate(String d) throws ParseException{
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return format.parse(d);
    }

    private static String convertDateToString(Date date){
        DateFormat last = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return last.format(date);
    }

    private static Date addDaysToDate(Date previousDate, int daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(previousDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }

    private static ArrayList<Date> dateCollection(HashSet<String> temp, int daysToAdd) throws ParseException {
        HashSet<Date> dates = new HashSet<>();
        for(String d : temp){
            Date date = convertStringToDate(d);
            dates.add(date);
        }

        ArrayList<Date> result = new ArrayList<>();
        Date min = Collections.min(dates);
        //to avoid the situations when the data on the borders was
        //put to the wrong segment
        String tempDateConv = convertDateToString(min);
        min = secondConvertStringToDate(tempDateConv);
        Date max = Collections.max(dates);
        result.add(min);

        while(min.compareTo(max) < 0){
        //add 2 weeks to the date
            min = addDaysToDate(min, daysToAdd);
            result.add(min);
        }
        Collections.sort(result);
        return result;
    }

    private static HashMap<Date, String> stringConcatinator (ArrayList<Date> col, HashSet<String> filteredFile) throws ParseException{
        HashMap<Date, String> result = new HashMap<>();
            for(int i = 0; i < col.size() - 1; i++){
                StringBuffer temp = new StringBuffer();
                for(String date : filteredFile) {
                    Date newDate = convertStringToDate(date);
                    if (newDate.compareTo(col.get(i)) >= 0 && newDate.compareTo(col.get(i + 1)) < 0){
                        temp.append(getIDfromString(date));
                    }
                }
                if(!temp.equals(""))
                    result.put(col.get(i), temp.toString());
            }
        return result;
    }

    private static HashMap<String, Integer> idCounter(String id){
        HashMap<String, Integer> col = new HashMap<String, Integer>();
        String[] idArray = id.split(",");

        for(String num : idArray){
            if(col.get(num) == null)
                col.put(num, 1);
            else{
                Integer am = col.get(num);
                col.put(num, am + 1);
            }
        }
        return col;
    }

    private static HashMap<Date, HashMap<String, Integer>> parseLogReader(HashMap<Date, String> col){
        HashMap<Date, HashMap<String, Integer>> parseResult = new HashMap<>();
        for(Date d : col.keySet()){
            HashMap<String, Integer> tempMap = idCounter(col.get(d));
            parseResult.put(d, tempMap);
        }
        return parseResult;
    }

    public static  StringBuffer convertResultToTheRightFormat(ArrayList<Date> collection, HashMap<Date, HashMap<String, Integer>> beforeResult){
        StringBuffer content = new StringBuffer();

        for(Date d : collection){
            if(beforeResult.get(d) != null){
                content.append(convertDateToString(d) + " - " + convertDateToString(addDaysToDate(d, amountOfDaysInSegment - 1)) + "\n");
                for(String t : beforeResult.get(d).keySet())
                    content.append(t + ": " + beforeResult.get(d).get(t) + ", ");
                content.append("\n\n");
            }
        }
        return content;
    }

    private static void putResultToTheConsole(ArrayList<Date> collection, HashMap<Date, HashMap<String, Integer>> beforeResult){

        StringBuffer content = convertResultToTheRightFormat(collection, beforeResult);

        System.out.println(content);
    }

    private static void putResultToTheFile(String pathToTheFile, ArrayList<Date> collection, HashMap<Date, HashMap<String, Integer>> beforeResult){
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            //String content = "";
            StringBuffer content = convertResultToTheRightFormat(collection, beforeResult);

            fw = new FileWriter(pathToTheFile);
            bw = new BufferedWriter(fw);
            bw.write(content.toString());

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
}
