
public class Main {

    public static void main(String[] args) {
	 String first = "sfs44sf@gmail.com " + "vlad.hil@gmail.com" +
             "sghvh@yandex.ru";
    System.out.println(checkCompanyEmail(first));
    }

    public static boolean checkCompanyEmail(String str){
        return str.matches("(?:(?:\\w)+[.-]?(?:\\w)+(?:@gmail\\.com|@yandex\\.ru),? ?)+");
    }

    public static boolean checkNotLessThan1450(String str){
        return str.matches("(?:(?:\\d{5,}|[2-9]\\d{3}|1[5-9]\\d{2}|14[6-9][0-9]|145[\\d])(?:.\\d+)?,? ?)+[^.\\w\\d]");
    }

    public static boolean checkFrom0To100 (String str){
        return str.matches("((?:\\d{1,2}|100)(?:\\.\\d+)?,? ?)+");
    }
}
