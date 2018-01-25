
public class Main {

    public static void main(String[] args) {
	 String first = "joxid sfsef sgsgrd fgfhh";
    System.out.println(checkCompanyEmail(first));
    }

    public static boolean checkCompanyEmail(String str){
        String temp = str.replaceAll("(?:\\d?\\w)+(?:@gmail\\.com|@yandex\\.ru),?", "");
        if(temp.trim().equals(""))
            return true;
        else
            return false;
    }
}
