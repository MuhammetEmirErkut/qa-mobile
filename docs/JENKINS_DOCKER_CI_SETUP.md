# Jenkins & Docker ile Mobil Test CI/CD Entegrasyonu 🚀

Bu doküman, Docker üzerinde Jenkins sunucusunun ayağa kaldırılması, konfigürasyonu ve mobil otomasyon testlerinin CI/CD hattında (Pipeline) koşturulmasını adım adım açıklar.

---

## 🏗️ 1. Mimari Genel Bakış

```
+-------------------------------------------------------------------------+
|                              Host (macOS)                               |
|                                                                         |
|  +---------------------------+       +-------------------------------+  |
|  |     Appium Server         | <---> |  Android Emulator /           |  |
|  |   http://127.0.0.1:4723   |       |  iOS Simulator (Xcode)        |  |
|  +---------------------------+       +-------------------------------+  |
|               ^                                                         |
|               |  (host.docker.internal:4723)                            |
|  +------------v------------------------------------------------------+  |
|  |                    Docker Konteyneri (Jenkins)                    |  |
|  |                                                                   |  |
|  |  • Jenkins Controller (LTS JDK 17)                                |  |
|  |  • Maven 3.9+                                                     |  |
|  |  • Allure CLI 2.27.0                                              |  |
|  |  • Declarative Jenkinsfile Pipeline                               |  |
|  +-------------------------------------------------------------------+  |
+-------------------------------------------------------------------------+
```

---

## ⚡ 2. Hızlı Başlangıç (Tek Komutla Ayağa Kaldırma)

Proje kök dizinindeyken aşağıdaki komutu çalıştırarak Jenkins konteynerini başlatın:

```bash
docker compose up -d --build
```

Jenkins başladığında konteyner loglarından ilk yönetici şifresini (Initial Admin Password) alın:

```bash
docker logs qa-mobile-jenkins 2>&1 | grep -A 2 "Please use the following password to proceed"
```
veya:
```bash
docker exec -it qa-mobile-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Tarayıcınızdan **`http://localhost:8080`** adresine gidin ve şifreyi yapıştırarak kurulum sihirbazını tamamlayın.

---

## 🔌 3. Gerekli Jenkins Eklentileri (Plugins)

Jenkins arayüzünde **Manage Jenkins > Plugins > Available Plugins** sekmesinden aşağıdaki eklentileri yükleyin:
1. **Allure Plugin** (Test raporlarını görselleştirmek için)
2. **Pipeline** (Declarative Jenkinsfile pipeline'ı çalıştırmak için)
3. **AnsiColor** (Konsol loglarını renkli görmek için)
4. **HTML Publisher**

### Allure Commandline Yapılandırması:
- **Manage Jenkins > Tools > Allure Commandline Installations** alanına gidin.
- Name: `Allure`
- "Install automatically" seçeneğini işaretleyin veya custom path olarak `/opt/allure-2.27.0` verin.

---

## 🛠️ 4. Pipeline Job Oluşturma

1. Jenkins ana sayfasında **New Item** butonuna tıklayın.
2. Proje adını girin (Örn: `QA-Mobile-Automation-Pipeline`) ve **Pipeline** türünü seçip **OK** deyin.
3. Pipeline sekmesinde:
   - **Definition:** `Pipeline script from SCM`
   - **SCM:** `Git` (Repo URL ve kimlik bilgilerinizi girin)
   - **Script Path:** `Jenkinsfile`
4. **Save** diyerek kaydedin.

---

## 🚀 5. Parametreli Test Çalıştırma (Build with Parameters)

Proje kaydedildikten sonra **Build with Parameters** seçeneği aktifleşecektir. Buradan:
- **PLATFORM:** `android`, `ios` veya `parallel`
- **CUSTOM_TEST:** (İsteğe bağlı tekil test: Örn `DualWebviewTest`, `LoginTest`)
- **APPIUM_URL:** `http://host.docker.internal:4723` (Docker içinden host Appium sunucusuna bağlanır)

seçilerek **Build** butonuna basılır.

---

## 📊 6. Raporlar ve Çıktılar

Build tamamlandığında sol menüde **Allure Report** ikonu belirecektir. Tıklayarak:
- Test başarı oranları (%100 Passed / Failed)
- Adım adım ekran görüntüleri (Fail durumunda otomatik yakalanan)
- Detaylı loglar ve execution süreleri

görüntülenebilir.
