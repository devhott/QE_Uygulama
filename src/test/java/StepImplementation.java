
import org.openqa.selenium.NoSuchElementException;
import utils.ElementReader;
import utils.ElementInfo;
import utils.DriverFactory;
import com.thoughtworks.gauge.Step;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepImplementation {
    private WebDriver driver = DriverFactory.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static final Logger logger = LogManager.getLogger(StepImplementation.class);

    @Step({"Navigate to <url>",
    "<url> sayfasına git."})
    public void navigateTo(String url) {
        logger.info("Navigating to: " + url);
        driver.get(url);
    }

    @Step({"Enter <text> into <key>", "<key> elementine <text> text değerini yaz"})
    public void enterTextIntoElement(String key, String text) {
        logger.info("📝 '{}' elementine '{}' değerini yazıyorum.", key, text);

        try {
            WebElement element = findElement(key);
            element.clear();
            element.sendKeys(text);
            logger.info("✅ '{}' elementine '{}' başarıyla yazıldı.", key, text);
        } catch (Exception e) {
            logger.error("❌ HATA: '{}' elementine '{}' yazılamadı!", key, text, e);
            throw new RuntimeException("Element not found or failed to send keys: " + key, e);
        }
    }


    @Step({"Click <key>",
    "<key> elementine tıkla."})
    public void clickElement(String key) {
        WebElement element = findElement(key);
        logger.info("Clicking on element: " + key);
        element.click();
    }

    @Step({"Verify <key> is displayed",
    "<key> elementinin görünür olduğunu kontrol et"})
    public boolean isElementDisplayed(String key) {
        WebElement element = findElement(key);
        boolean isDisplayed = element.isDisplayed();
        logger.info("Element " + key + " is displayed: " + isDisplayed);
        return isDisplayed;
    }
    @Step({"Wait <int> seconds",
            "<int> saniye bekle"})
    public void waitBySeconds(int seconds) {
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Step({"Clear text of element <key>",
            "<key> elementinin text alanını temizle"})
    public void clearInputArea(String key) {
        findElement(key).clear();
    }

    @Step({"Check if element <key> contains text <text>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String text) {

        Boolean containsText = findElement(key).getText().contains(text);
        assertTrue(containsText, "Expected text is not contained");
        logger.info(key + " elementi" + text + "değerini içeriyor.");
    }
    @Step("Transferden önce <key> da kalan hesabı <amount> kontrol et")
    public void transferAndVerifyBalance(String key,String amount) {
        // 1️⃣ Mevcut bakiyeyi al
        String balanceBeforeTransfer = findElement(key).getText();
        double initialBalance = parseBalance(balanceBeforeTransfer);

        logger.info("💰 Mevcut Bakiye: {} TL", initialBalance);
    }
    private double initialBalance;
    private double newBalance;
    private double amountTransferred;
    @Step({"Mevcut bakiye alınır.",
            "Hesap bakiyesi kontrol edilir."})
    public void getInitialBalance() {
        WebElement balanceElement = findElement("accountInfoAmount");
        String balanceText = balanceElement.getText();
        initialBalance = parseBalance(balanceText);

        logger.info("💰 Mevcut Bakiye: {} TL", initialBalance);
    }

    @Step({"Yeni bakiye alınır.",
            "Transfer sonrası bakiye kontrol edilir."})
    public void getNewBalance() {
        wait.until(ExpectedConditions.presenceOfElementLocated(getBy("xpath","//*[@id='root']/div/div[2]/div[2]/div[1]/div/div/div/div[3]/div[7]/div[2]/div")));
        WebElement newBalanceElement = findElement("accountInfoAmount");
        newBalance = parseBalance(newBalanceElement.getText());

        logger.info("💳 Yeni Bakiye: {} TL", newBalance);
    }
    @Step({"Kullanıcı <text> TL gönderir.",
            "<text> TL transfer edilir."})
    public void saveTransferAmount(String text) {
        amountTransferred = Double.parseDouble(text);
        logger.info("💸 Transfer Edilen Tutar: {} TL olarak kaydedildi.", amountTransferred);
    }

    @Step({"Bakiye doğrulaması yapılır.",
            "Beklenen bakiye hesaplanır ve karşılaştırılır."})
    public void verifyBalanceforTransfer() {
        double expectedBalance = initialBalance - amountTransferred;

        logger.info("🎯 Beklenen Bakiye: {} TL", expectedBalance);
        logger.info("📌 Gerçek Bakiye: {} TL", newBalance);

        if (Math.abs(newBalance - expectedBalance) > 0.01) {
            logger.error("❌ Bakiye yanlış! Beklenen: {}, Görülen: {}", expectedBalance, newBalance);
            throw new AssertionError("Bakiye yanlış! Beklenen: " + expectedBalance + " TL, Görülen: " + newBalance + " TL");
        } else {
            logger.info("✅ Bakiye doğru! Beklenen ve görülen değer eşleşiyor.");
        }
    }
    @Step({"Eklenen bakiye hesaplanır ve karşılaştırılır."})
    public void verifyBalanceforAdd() {
        double expectedBalance = initialBalance + amountTransferred;

        logger.info("🎯 Beklenen Bakiye: {} TL", expectedBalance);
        logger.info("📌 Gerçek Bakiye: {} TL", newBalance);

        if (Math.abs(newBalance + expectedBalance) > 0.01) {
            logger.error("❌ Bakiye yanlış! Beklenen: {}, Görülen: {}", expectedBalance, newBalance);
            throw new AssertionError("Bakiye yanlış! Beklenen: " + expectedBalance + " TL, Görülen: " + newBalance + " TL");
        } else {
            logger.info("✅ Bakiye doğru! Beklenen ve görülen değer eşleşiyor.");
        }
    }

    private double parseBalance(String balanceText) {
        logger.info("💳 Orijinal bakiye metni: {}", balanceText);

        // Sayı olmayan karakterleri temizle (Boşluklar, para birimi sembolleri vb.)
        balanceText = balanceText.replaceAll("[^0-9,.]", "");


        if (balanceText.matches("\\d{1,3}(,\\d{3})*(\\.\\d+)?")) {
            balanceText = balanceText.replace(",", ""); // Binlik ayracı kaldır
        }

        // Eğer sadece virgül varsa, onu nokta ile değiştir (Avrupa formatı düzeltme)
        if (balanceText.contains(",") && !balanceText.contains(".")) {
            balanceText = balanceText.replace(",", ".");
        }

        logger.info("📊 Düzeltilmiş bakiye metni: {}", balanceText);

        return Double.parseDouble(balanceText);
    }



    private WebElement findElement(String key) {
        logger.info("🔍 Element bulunuyor: key='{}'", key);

        ElementInfo elementInfo = ElementReader.getElement(key);
        By locator = getBy(elementInfo.getType(), elementInfo.getValue());

        logger.info("✅ Element başarıyla bulundu: type='{}', value='{}'", elementInfo.getType(), elementInfo.getValue());

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    private By getBy(String type, String value) {
        type = type.toLowerCase().trim();  // Normalize et

        switch (type) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "css":
            case "cssselector":
                return By.cssSelector(value);
            case "xpath":
                return By.xpath(value);
            case "classname":
                return By.className(value);
            case "linktext":
                return By.linkText(value);
            case "partiallinktext":
                return By.partialLinkText(value);
            case "tagname":
                return By.tagName(value);
            default:
                logger.error("❌ HATA: Geçersiz locator tipi: '{}'. XPath olarak devam ediliyor.", type);
                return By.xpath(value);  // Varsayılan olarak XPath kullan
        }
    }

}
