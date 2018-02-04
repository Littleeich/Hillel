import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    public static void main(String[] args){

        DataLog r = new DataLog();

        System.out.println(LogCleaner(r.inline));
}

    public static String LogCleaner(String inline){
        StringBuffer temp = new StringBuffer();

        String inlineArray[] = inline.split("\n");
        for (String i : inlineArray)
            temp.append(LineCleaner(i) + "\n");

        return temp.toString();
    }

    public static String LineCleaner(String inline){
        String[] tags = {"Login Username", "Data Object", "Records", "User Action", "User Action Status", "Labels", "Service type",
                "Mapping Ids", "URI"};

        StringBuffer temp = new StringBuffer();
        try {temp.append(inline.substring(0, inline.indexOf("skyfence")));}
            catch (Exception e) {return "";}

        for(String t : tags){
            Pattern pattern = Pattern.compile("\\[" + t + "[^\\]]+\\]");
            Matcher matcher = pattern.matcher(inline);
            if(matcher.find())
                temp.append(matcher.group());
        }
        return temp.toString();
    }
}
