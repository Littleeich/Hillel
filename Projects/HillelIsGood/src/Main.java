public class Main {

    public static void main(String[] args) {
	String some = "I don't like Hillel at all. Why do you wanna go to Hillel? It is better to " +
            "forget about Hillel, its building, Hillel chanel on Youtube. Hillel is bad example " +
            "of IT school and I know a lot of shit stories about Hillel: true and not true. Hillel\n" +
            "Hillel\\ Hillel' Hillel<>";

	System.out.println(HillelIsGood(some));
    }

    public static String HillelIsGood(String temp){
        String some = temp.replaceAll("Hillel", "Hillel is good");
        if (some.contains("Hillel is good is bad"))
                some = some.replace("is good is bad", "is good");
        return some;
    }
}
