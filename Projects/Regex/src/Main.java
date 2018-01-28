
public class Main {

    public static void main(String[] args) {
	 String first = "sfs44sf@gmail.com " + "vlad.hil@gmail.com" +
             "sghvh@yandex.ru";
    System.out.println(checkCompanyEmail(first));
    }

    public static boolean checkCompanyEmail(String str){
        return str.matches("(?:(?:\\w)+[.-]?(?:\\w)+(?:@gmail\\.com|@yandex\\.ru),? ?)+");
    }
}
