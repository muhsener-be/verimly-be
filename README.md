Verimly API
Verimly, kullanıcıların görevlerini ve bu görevlere harcadıkları zamanı verimli bir şekilde yönetmelerini sağlayan bir backend servisidir. Modern, modüler ve test edilebilir bir mimari ile geliştirilmiştir.

✨ Öne Çıkan Özellikler
Kullanıcı Yönetimi: Kayıt, giriş ve çıkış işlemleri.

Görev Yönetimi: Görev oluşturma, listeleme, güncelleme ve silme.

Klasör Yönetimi: Görevleri klasörler altında organize etme.

Zaman Takibi: Görevler için çalışma oturumları başlatma, duraklatma ve bitirme.

Güvenlik: JWT (JSON Web Token) tabanlı, HttpOnly cookie'ler ile güvenli kimlik doğrulama.

API Dokümantasyonu: SpringDoc OpenAPI ile otomatik oluşturulan interaktif API dokümantasyonu.

Çoklu Dil Desteği: Hata mesajları için İngilizce ve Türkçe dil desteği.

🛠️ Kullanılan Teknolojiler
Backend: Java 21, Spring Boot 3

Veritabanı: PostgreSQL, H2 (test için)

Veri Erişimi: Spring Data JPA, Hibernate

Güvenlik: Spring Security, JWT

Build & Bağımlılık Yönetimi: Maven

Test: JUnit 5, Mockito, Testcontainers

API Dokümantasyonu: SpringDoc OpenAPI

Diğer: Lombok, MapStruct

🏗️ Mimari
Proje, Hexagonal (Ports & Adapters) mimari prensiplerine uygun olarak tasarlanmış çok modüllü bir monolitik yapıdır. Bu yapı, iş mantığını (domain ve application katmanları) dış dünyadan (web, veritabanı vb.) izole ederek yüksek test edilebilirlik ve esneklik sağlar.

commons-core: Tüm modüller tarafından paylaşılan temel sınıflar, istisnalar ve güvenlik altyapısı.

user: Kullanıcı yönetimi ile ilgili iş mantığını içerir.

task: Görev, klasör ve oturum yönetimi ile ilgili iş mantığını içerir.

api-composition: Farklı modüllerin servislerini birleştirerek yeni API'lar sunar ve genel güvenlik yapılandırmasını yönetir.

app: Tüm modülleri bir araya getiren ve Spring Boot uygulamasını başlatan ana modüldür.

🚀 Projeyi Başlatma Rehberi
Ön Gereksinimler
Git

Java 21 (Temurin veya eşdeğeri)

Apache Maven 3.9+

Docker (isteğe bağlı, Docker ile çalıştırma için)

Kurulum ve Çalıştırma (Lokal)
Projeyi klonlayın:

git clone [https://github.com/kullaniciadi/verimly-be.git](https://github.com/kullaniciadi/verimly-be.git)
cd verimly-be

Yapılandırma Dosyasını Oluşturun:
Projenin ana dizininde .env adında bir dosya oluşturun ve içeriğini .env.example dosyasını referans alarak doldurun.

Projeyi derleyin ve bağımlılıkları yükleyin:

mvn clean install

Uygulamayı çalıştırın:

java -jar app/target/app-0.0.1-SNAPSHOT.jar

Uygulama varsayılan olarak 8080 portunda başlayacaktır.

🐳 Docker ile Çalıştırma
Proje, Docker Hub üzerinde muhsener98/verimly-be-api:latest imajı ile mevcuttur.

Docker imajını çekin:

docker pull muhsener98/verimly-be-api:latest

Konteyneri çalıştırın:
Aşağıdaki komutu çalıştırmadan önce, .env.example dosyasındaki tüm değişkenleri -e parametresi ile komutunuza eklediğinizden emin olun.

docker run -p 8080:8080 \
  -e DB_URL="your_database_url_here" \
  -e DB_USERNAME="your_database_username" \
  -e DB_PASSWORD="your_database_password" \
  -e PROFILE="prod" \
  -e FE_ORIGIN="[https://verimly-fe-abc.vercel.app](https://verimly-fe-abc.vercel.app)" \
  -e SPRING_SQL_INIT_MODE="never" \
  -e BOOTSTRAP_USER_FIRSTNAME="YourFirstName" \
  -e BOOTSTRAP_USER_LASTNAME="YourLastName" \
  -e BOOTSTRAP_USER_EMAIL="your_email@example.com" \
  -e BOOTSTRAP_USER_PASSWORD="your_strong_password" \
  -e JWT_SECRET_KEY="your_super_secret_jwt_key" \
  muhsener98/verimly-be-api:latest

⚙️ Yapılandırma (Çevre Değişkenleri)
Uygulamanın çalışması için aşağıdaki çevre değişkenlerinin ayarlanması gerekmektedir. Detaylar için .env.example dosyasına bakabilirsiniz.

Değişken

Açıklama

Örnek Değer

DB_URL

PostgreSQL veritabanı bağlantı adresi.

jdbc:postgresql://host:port/dbname

DB_USERNAME

Veritabanı kullanıcı adı.

postgres

DB_PASSWORD

Veritabanı şifresi.

your_password

PROFILE

Aktif Spring profili (dev veya prod).

prod

FE_ORIGIN

CORS için izin verilen frontend adresi.

http://localhost:3000

SPRING_SQL_INIT_MODE

Uygulama başlangıcında schema.sql'in çalışıp çalışmayacağı.

never

BOOTSTRAP_USER_EMAIL

İlk başlangıçta oluşturulacak varsayılan kullanıcı emaili.

admin@verimly.com

BOOTSTRAP_USER_PASSWORD

Varsayılan kullanıcının şifresi.

strongpassword123

JWT_SECRET_KEY

JWT imzalamak için kullanılacak gizli anahtar.

çok_güçlü_ve_uzun_bir_anahtar

...

Diğer cookie ve JWT ayarları...

...

📖 API Dokümantasyonu
Uygulama çalıştırıldıktan sonra, interaktif API dokümantasyonuna (Swagger UI) aşağıdaki adresten ulaşabilirsiniz:

http://localhost:8080/swagger-ui.html
