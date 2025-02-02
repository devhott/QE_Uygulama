# Gauge Test Otomasyon Projesi

Bu proje, **Gauge** test otomasyon framework'ü kullanılarak geliştirilmiştir. Senaryolar **Gauge + Selenium + Allure** kullanılarak oluşturulmuştur.

## 📌 Proje Hakkında
Bu proje, **CatchyLabs Web Client** uygulamasının test otomasyonu için geliştirilmiştir. Uygulama, kullanıcıların giriş yaparak para transferi gerçekleştirmesine, hesap bakiyelerini görüntülemesine ve çeşitli finansal işlemleri takip etmesine olanak tanır.

Gauge framework'ü ile test senaryoları oluşturulmuş, Selenium WebDriver ile UI testleri gerçekleştirilmiş ve Allure entegrasyonu sayesinde detaylı test raporlaması sağlanmıştır.

## 📌 Proje Yapısı

```
Uygulama_QE/
│-- env/                 # Test ortamları konfigürasyonları
│-- reports/             # Test raporları
│-- specs/               # Gauge test senaryoları (SPEC dosyaları)
│-- src/
│   ├── main/java/utils  # Yardımcı sınıflar (Driver, ElementReader vb.)
│   ├── test/java/       # Step Implementation (Gauge steplerinin tanımlandığı yer)
│-- gauge.properties     # Gauge ayarları
│-- pom.xml              # Maven bağımlılıkları
│-- README.md            # Bu dosya :)
```

## 🚀 Kurulum
### **1️⃣ Projeyi Klonla**
```sh
git clone https://github.com/kullaniciadi/gauge-otomasyon.git
cd gauge-otomasyon
```

### **2️⃣ Gereksinimleri Yükle**
#### **Gauge ve Java Plugin'lerini Kur**
```sh
choco install gauge # Windows için
brew install gauge  # Mac için
```
```sh
gauge install java
```

#### **Maven Bağımlılıklarını Kur**
```sh
mvn clean install
```

## ▶️ Testleri Çalıştırma
### **Tüm Testleri Çalıştır**
```sh
gauge run specs
```

### **Belli Bir Senaryoyu Çalıştır**
```sh
gauge run specs/scenario.spec
```

### **Belirli Ortamda Testleri Çalıştır**
```sh
gauge run specs --env default
```

## 📊 Allure Raporlama Entegrasyonu
### **1️⃣ Allure'u Yükleyin**
```sh
choco install allure # Windows için
brew install allure  # Mac için
```

### **2️⃣ Allure'u Projeye Dahil Edin**
`env/default/plugins.json` dosyasını oluşturun:

```json
{
  "allure": {
    "enabled": true
  }
}
```

### **3️⃣ Raporları Üretin ve Görüntüleyin**
Testleri çalıştırdıktan sonra Allure raporlarını oluşturmak için şu komutları çalıştırın:

```sh
allure generate --clean && allure serve
```

## 🛠️ Hata Ayıklama
Eğer `SLF4J: No SLF4J providers were found.` hatası alıyorsanız, `pom.xml` içinde şu bağımlılığı ekleyin:
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.17.1</version>
</dependency>
```

Bağımlılığı ekledikten sonra tekrar şu komutu çalıştırın:
```sh
mvn clean install
```

## 📌 Ekstra Bilgiler
- **Gauge Dokümantasyonu**: [https://docs.gauge.org/](https://docs.gauge.org/)
- **Allure Dokümantasyonu**: [https://docs.qameta.io/allure/](https://docs.qameta.io/allure/)
- **Selenium WebDriver**: [https://www.selenium.dev/](https://www.selenium.dev/)

📌 **Herhangi bir sorun yaşarsanız `Issues` bölümünden bildirebilirsiniz!** 🚀
