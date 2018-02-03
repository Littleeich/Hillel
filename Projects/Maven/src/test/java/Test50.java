import org.testng.Assert;
import org.testng.annotations.Test;

public class Test50{

    @Test
    public static void unitTestForEmailTrue() {
        Assert.assertTrue(Regex.checkCompanyEmail("sfs44sf@gmail.com "));
    }
    @Test
    public static void unitTestForEmailFalse() { Assert.assertFalse(Regex.checkCompanyEmail("sfs44sf@mail.com "));
    }
    @Test
    public static void unitTestForEmailSeveralTrue() { Assert.assertTrue(Regex.checkCompanyEmail("aadad@gmail.com, vova@yandex.ru"));
    }
    @Test
    public static void unitTestForSeveralFalseEmails() { Assert.assertFalse(Regex.checkCompanyEmail("sanya@gmail.com, kolya@wwww.ww"));
    }
    @Test
    public static void unitTestForEmailEmpty() { Assert.assertFalse(Regex.checkCompanyEmail(""));
    }
    @Test
    public static void unitTestForNumberInsteadEmail() {Assert.assertFalse(Regex.checkCompanyEmail("1125874963"));}

    @Test
    public static void unitTestFor1450True() {
        Assert.assertTrue(Regex.checkNotLessThan1450("10000"));
    }
    @Test
    public static void unitTestFor1450withPointTrue() {
        Assert.assertTrue(Regex.checkNotLessThan1450("4568.88"));
    }
    @Test
    public static void unitTestFor1450False() {
        Assert.assertFalse(Regex.checkNotLessThan1450("190"));
    }
    @Test
    public static void unitTestFor1450withPointFalse() {
        Assert.assertFalse(Regex.checkNotLessThan1450("190.7"));
    }
    @Test
    public static void unitTestFor1450withZero(){
        Assert.assertFalse(Regex.checkNotLessThan1450("0"));
    }
    @Test
    public static void unitTestFor1450withLetters(){
        Assert.assertFalse(Regex.checkNotLessThan1450("qwerty"));
    }

    @Test
    public static void unitTestFor100True() {
        Assert.assertTrue(Regex.checkFrom0To100("15.55"));
    }
    @Test
    public static void unitTestFor100withNegativeNumber() {Assert.assertFalse(Regex.checkFrom0To100("-965.65"));}
    @Test
    public static void unitTestFor100withZero() {Assert.assertTrue(Regex.checkFrom0To100("0"));}
    @Test
    public static void unitTestFor100False() {
        Assert.assertFalse(Regex.checkFrom0To100("10000"));
    }
    @Test
    public static void unitTestFor100Empty() {Assert.assertFalse(Regex.checkFrom0To100(""));}
    @Test
    public static void unitTestFor100var2() {Assert.assertTrue(Regex.checkFrom0To100("29"));}
    @Test
    public static void unitTestFor100Letters() {Assert.assertFalse(Regex.checkFrom0To100("qwerty"));}

}
