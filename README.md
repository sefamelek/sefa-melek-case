# Sefa Melek Case

Insider web sitesi için Selenium WebDriver kullanılarak geliştirilmiş otomatik test projesi. Bu proje, Insider'ın kariyer sayfalarındaki iş ilanlarını test eder.

## 📋 İçindekiler

- [Özellikler](#özellikler)
- [Teknolojiler](#teknolojiler)
- [Gereksinimler](#gereksinimler)
- [Kurulum](#kurulum)
- [Test Çalıştırma](#test-çalıştırma)
- [Allure Raporları](#allure-raporları)
- [Proje Yapısı](#proje-yapısı)
- [Test Senaryoları](#test-senaryoları)
- [Yapılandırma](#yapılandırma)

## ✨ Özellikler

- ✅ Page Object Model (POM) mimarisi
- ✅ Paralel test çalıştırma desteği
- ✅ Allure raporlama entegrasyonu
- ✅ Dinamik wait stratejileri
- ✅ Screenshot otomatik yakalama
- ✅ Çoklu browser desteği (Chrome, Firefox)
- ✅ Headless mode desteği
- ✅ Detaylı loglama (Log4j2)

## 🛠️ Teknolojiler

- **Java 21** - Programlama dili
- **Selenium WebDriver 4.26.0** - Web otomasyon framework
- **TestNG 7.10.2** - Test framework
- **Maven** - Build ve dependency yönetimi
- **Allure 2.27.0** - Test raporlama
- **WebDriverManager 5.9.2** - Driver yönetimi
- **Log4j2 2.23.1** - Logging framework

## 📦 Gereksinimler

- Java 21 veya üzeri
- Maven 3.6+ 
- Chrome veya Firefox browser

## 🚀 Kurulum

### 1. Projeyi Klonlayın

```bash
git clone <repository-url>
cd sefa-melek-case
```

### 2. Bağımlılıkları Yükleyin

```bash
mvn clean install
```

### 3. WebDriver'ları Kontrol Edin

WebDriverManager otomatik olarak gerekli driver'ları indirir. Manuel kurulum gerekmez.

## 🧪 Test Çalıştırma

### Tüm Testleri Çalıştırma

```bash
mvn clean test
```

### Belirli Bir Test Sınıfını Çalıştırma

```bash
mvn test -Dtest=InsiderCareerTest
```

### Belirli Bir Test Metodunu Çalıştırma

```bash
mvn test -Dtest=InsiderCareerTest#testHomePageOpensAndDisplaysCorrectTitle
```

### Headless Mode ile Çalıştırma

`testng.xml` dosyasında `headless` parametresi `true` olarak ayarlanmıştır. Değiştirmek için:

```xml
<parameter name="headless" value="false"/>
```

### Paralel Test Çalıştırma

`testng.xml` dosyasında paralel çalıştırma yapılandırılmıştır:

```xml
<suite name="Insider Test Suite" parallel="methods" thread-count="2">
```

Thread sayısını değiştirmek için `thread-count` değerini güncelleyin.

## 📊 Allure Raporları

### Rapor Oluşturma ve Görüntüleme

```bash
# Testleri çalıştır ve raporu otomatik aç
mvn clean test allure:serve
```

### Sadece Raporu Görüntüleme (Testler zaten çalıştırıldıysa)

```bash
mvn allure:serve
```

### Statik HTML Rapor Oluşturma

```bash
# Raporu oluştur
mvn allure:report

# Tarayıcıda aç
open target/site/allure-maven-plugin/index.html
```

### Allure CLI ile (Opsiyonel)

```bash
# Homebrew ile Allure CLI kurulumu
brew install allure

# Raporu serve et
allure serve target/allure-results

# Statik rapor oluştur
allure generate target/allure-results -o target/allure-report
```

## 📁 Proje Yapısı

```
insider/
├── src/
│   ├── main/
│   │   └── resources/
│   │       ├── banner.txt
│   │       ├── compact-banner.txt
│   │       └── log4j2.xml
│   └── test/
│       └── java/
│           └── com/
│               └── insider/
│                   ├── listeners/          # TestNG listener'ları
│                   │   ├── AllureExecutorListener.java
│                   │   └── ScreenshotListener.java
│                   ├── locators/          # Page locator'ları
│                   │   ├── HomePageLocators.java
│                   │   ├── CareersPageLocators.java
│                   │   ├── JobsPageLocators.java
│                   │   └── QAPageLocators.java
│                   ├── pages/            # Page Object sınıfları
│                   │   ├── BasePage.java
│                   │   ├── HomePage.java
│                   │   ├── CareersPage.java
│                   │   ├── JobsPage.java
│                   │   └── QAPage.java
│                   ├── tests/            # Test sınıfları
│                   │   ├── BaseTest.java
│                   │   └── InsiderCareerTest.java
│                   └── utils/            # Utility sınıfları
│                       ├── WebDriverUtils.java
│                       └── methods/
│                           ├── WaitUtils.java
│                           ├── ClickUtils.java
│                           ├── DropdownUtils.java
│                           ├── AssertionUtils.java
│                           └── ...
├── target/
│   ├── allure-results/    # Allure test sonuçları
│   └── surefire-reports/  # TestNG raporları
├── logs/                   # Log dosyaları
├── pom.xml                 # Maven yapılandırması
├── testng.xml             # TestNG yapılandırması
└── README.md              # Bu dosya
```

## 📝 Test Senaryoları

### 1. Ana Sayfa Başlık Doğrulama
- Insider ana sayfasının açıldığını doğrular
- Sayfa başlığının "Insider" içerdiğini kontrol eder

### 2. Careers Sayfası Navigasyonu
- Company → Careers menüsüne gider
- Sayfa içeriklerinin (Locations, Teams, Life at Insider) görünür olduğunu doğrular

### 3. QA İşlerini Filtreleme
- QA kariyer sayfasına gider
- "See all QA jobs" butonuna tıklar
- Filtreleri uygular:
  - Location → "Istanbul, Turkey"
  - Department → "Quality Assurance"
- İş listesinin görünür olduğunu doğrular

### 4. Filtrelere Uygunluk Doğrulama
- Listelenen tüm iş ilanlarının filtre kriterlerine uygun olduğunu kontrol eder:
  - Position → "Quality Assurance" içeriyor mu?
  - Department → "Quality Assurance" içeriyor mu?
  - Location → "Istanbul, Turkey" içeriyor mu?

### 5. View Role Butonu Testi
- "View Role" butonuna tıklar
- lever.co'ya yönlendirdiğini doğrular

## ⚙️ Yapılandırma

### TestNG Yapılandırması (`testng.xml`)

```xml
<suite name="Insider Test Suite" parallel="methods" thread-count="2">
    <test name="Chrome Test">
        <parameter name="browser" value="chrome"/>
        <parameter name="headless" value="true"/>
    </test>
</suite>
```

### Timeout Ayarları

- **Varsayılan timeout**: 10 saniye
- **Filter timeout**: 20 saniye (JobsPage için özel)

### Log Yapılandırması

Log ayarları `src/main/resources/log4j2.xml` dosyasında yapılandırılmıştır.

## 🔧 Utility Sınıfları

Proje, tekrar kullanılabilir utility metodları içerir:

- **WaitUtils**: Dinamik ve statik wait metodları
- **ClickUtils**: Click işlemleri için utility metodlar
- **DropdownUtils**: Dropdown işlemleri
- **AssertionUtils**: Assertion metodları
- **CookieUtils**: Cookie yönetimi
- **WebDriverUtils**: WebDriver işlemleri

## 📸 Screenshot

Testler başarısız olduğunda veya belirli adımlarda otomatik olarak screenshot alınır. Screenshot'lar Allure raporlarına eklenir.

## 🐛 Sorun Giderme

### Chrome Driver Sorunları

WebDriverManager otomatik olarak driver'ları yönetir. Sorun yaşarsanız:

```bash
mvn clean test
```

### Test Timeout Sorunları

Timeout sürelerini artırmak için ilgili utility sınıflarındaki `DEFAULT_TIMEOUT_SECONDS` değerini güncelleyin.

### Allure Rapor Sorunları

Allure sonuçları `target/allure-results` klasöründe oluşur. Eğer rapor görünmüyorsa:

```bash
# Sonuçları temizle ve tekrar çalıştır
rm -rf target/allure-results
mvn clean test allure:serve
```


## 👤 Geliştirici

**Sefa Melek**

---

## 📚 Ek Kaynaklar

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/documentation-main.html)
- [Allure Documentation](https://docs.qameta.io/allure/)
- [Maven Documentation](https://maven.apache.org/guides/)

