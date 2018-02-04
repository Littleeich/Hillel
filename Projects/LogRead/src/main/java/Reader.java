import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    public static void main(String[] args){

        DataLog r = new DataLog();

        String inlineArray[] = r.inline.split("\n");
        for (String i : inlineArray)
            System.out.println(LogCleaner(i));
}

    public static StringBuffer LogCleaner(String inline){
        String[] tags = {"Login Username", "Data Object", "Records", "User Action", "User Action Status", "Labels", "Service type",
                "Mapping Ids", "URI"};

        StringBuffer temp = new StringBuffer();

        temp.append(inline.substring(0, inline.indexOf("skyfence")));
        for(String t : tags){
            Pattern pattern = Pattern.compile("\\[" + t + "[^\\]]+\\]");
            Matcher matcher = pattern.matcher(inline);
            if(matcher.find())
                temp.append(matcher.group());
        }
        return temp;
    }
}
