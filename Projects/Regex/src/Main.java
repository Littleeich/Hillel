
public class Main {

    public static void main(String[] args) {
	 String first = "sfs44sf@gmail.com " + "vlad.hil@gmail.com" +
             "sghvh@yandex.ru";
	 String second = "1265";
	 String third = "1800";

	 unitTestForEmailTrue(first);
	 unitTestForEmailFalse(first);
	 unitTestFor1450True(second);
	 unitTestFor1450False(second);
	 unitTestFor100True(third);
	 unitTestFor100False(third);
    }

    public static boolean checkCompanyEmail(String str){
        return str.matches("(?:(?:\\w)+[.-]?(?:\\w)+(?:@gmail\\.com|@yandex\\.ru),? ?)+");
    }

    public static boolean checkNotLessThan1450(String str){
        return str.matches("(?:(?:\\d{5,}|[2-9]\\d{3}|1[5-9]\\d{2}|14[6-9][0-9]|145[\\d])(?:.\\d+)?,? ?)+[^.\\w\\d]?");
    }

    public static boolean checkFrom0To100 (String str){
        return str.matches("^(?:\\d{1,2}|100)(?:\\.\\d+)? $");
    }

    public static void unitTestForEmailTrue(String data) {
        if(checkCompanyEmail(data))
            System.out.println("unitTestForEmailTrue is OK");
        else
            System.out.println("unitTestForEmailTrue is not OK");
    }

    public static void unitTestForEmailFalse(String data) {
        if(!checkCompanyEmail(data))
            System.out.println("unitTestForEmailFalse is OK");
        else
            System.out.println("unitTestForEmailFalse is not OK");
    }

    public static void unitTestFor1450True(String number) {
        if(checkNotLessThan1450(number))
            System.out.println("unitTestFor1450True is OK");
        else
            System.out.println("unitTestFor1450True is not OK");
    }

    public static void unitTestFor1450False(String number) {
        if(!checkNotLessThan1450(number))
            System.out.println("unitTestFor1450False is OK");
        else
            System.out.println("unitTestFor1450False is not OK");
    }

    public static void unitTestFor100True(String number) {
        if(checkFrom0To100(number))
            System.out.println("unitTestFor100True is OK");
        else
            System.out.println("unitTestFor100True is not OK");
    }

    public static void unitTestFor100False(String number) {
        if(!checkFrom0To100(number))
            System.out.println("unitTestFor100False is OK");
        else
            System.out.println("unitTestFor100False is not OK");
    }

}
