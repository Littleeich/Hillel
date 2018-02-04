import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Test50{

    @DataProvider(name = "emails")
    public Object[][] createData1() {
        return new Object[][] {
                { "sfs44sf@gmail.com", Boolean.TRUE},
                { "sfs44sf@yandex.ru", Boolean.TRUE},
                { "sfs44sf@gmail.com, vasya@yandex.ru", Boolean.TRUE},
                { "sfs44sf@mail.com", Boolean.FALSE},
                { "", Boolean.FALSE},
                { "vasya@yandex.ru, effwf@dee.ee", Boolean.FALSE},
        };
    }
    @Test (dataProvider = "emails")
    public void checkEmails(String emails, Boolean result){
        Assert.assertEquals((Boolean)Regex.checkCompanyEmail(emails), result);
    }

    @DataProvider(name = "numbersUnder100")
    public Object[][] createDataSmallNumbers() {
        return new Object[][] {
                { "100", Boolean.TRUE},
                { "0", Boolean.TRUE},
                { "60.85", Boolean.TRUE},
                { "60,85", Boolean.FALSE},
                { "760.85", Boolean.FALSE},
                { "-60.85", Boolean.FALSE}

        };
    }
    @Test (dataProvider = "numbersUnder100")
    public void checkSmallNumbers(String numbers, Boolean result){
        Assert.assertEquals((Boolean)Regex.checkFrom0To100(numbers), result);
    }

    @DataProvider(name = "numbers1450")
    public Object[][] createDataBigNumbers() {
        return new Object[][] {
                { "1450", Boolean.TRUE},
                { "966555", Boolean.TRUE},
                { "1500.77", Boolean.TRUE},
                { "60,85", Boolean.FALSE},
                { "760.85", Boolean.FALSE},
                { "-60.85", Boolean.FALSE},
                { "", Boolean.FALSE},

        };
    }
    @Test (dataProvider = "numbers1450")
    public void checkBigNumbers(String numbers, Boolean result){
        Assert.assertEquals((Boolean)Regex.checkNotLessThan1450(numbers), result);
    }

}
