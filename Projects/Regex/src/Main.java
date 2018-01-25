
public class Main {

    public static void main(String[] args) {
	 String first = "65@gmail.com " +
             "s445ghvh@yandex.ru";
    System.out.println(checkCompanyEmail(first));
    }

    public static boolean checkCompanyEmail(String str){
        String temp = str.replaceAll("(?:\\w)+(?:@gmail\\.com|@yandex\\.ru),?", "");
        if(temp.trim().equals(""))
            return true;
        else
            return false;
    }
}
