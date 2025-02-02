# Application Functional Tests


SC1 Login Olunup Kullanıcı Adı Değiştirilmesi
------------
Tags:@Login @Happypath @EditAccount @Regression
* Uygulama açılır.
* Login sayfasında "devrim.hot@testinium.com" kullanıcı adı ve "6bQfRSXy" şifresiyle oturum açılır.
* Uygulama hakkında bilgiler görülür ve open money transfer tıklanır.
* Edit Account butonuna tıklanır ve pop geldiği görülür.
* Güncelle butonuna tıklanır ve kullanıcı adının değişti görülür.

SC2 Login Olunup Para Gönderilmesi
-----------
Tags:@Login @SendMoney @Regression
 * Uygulama açılır.
 * Login sayfasında "devrim.hot@testinium.com" kullanıcı adı ve "6bQfRSXy" şifresiyle oturum açılır.
 * Uygulama hakkında bilgiler görülür ve open money transfer tıklanır.
 * Transfer money butonuna tıklanır.
 * Gönderilecek miktar "10000" yazılır ve gönder butonuna tıklanır.
 * Yeni bakiye alınır.
* Beklenen bakiye hesaplanır ve karşılaştırılır.

SC3 Login Olunup Hesaba Para Eklenmesi
---------
Tags:@Login @GetMoney @Regression
* Uygulama açılır.
 * Login sayfasında "devrim.hot@testinium.com" kullanıcı adı ve "6bQfRSXy" şifresiyle oturum açılır.
 * Uygulama hakkında bilgiler görülür ve open money transfer tıklanır.
 * Add money butonuna tıklanır.
 * Kart bilgileri doldurulur, "10000" Tl yazılır ve add butonuna basılır.
 * Yeni bakiye alınır.
 * Eklenen bakiye hesaplanır ve karşılaştırılır.



