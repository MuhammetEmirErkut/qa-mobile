pipeline {
    agent any

    options {
        timeout(time: 60, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '10'))
    }

    parameters {
        choice(
            name: 'PLATFORM',
            choices: ['android', 'ios', 'parallel'],
            description: 'Koşturulacak hedef mobil platform profili'
        )
        string(
            name: 'CUSTOM_TEST',
            defaultValue: '',
            description: 'Tekil test çalıştırmak için sınıf veya metod adı (Örn: DualWebviewTest veya LoginTest#testValidLoginAndLogout). Boş bırakılırsa tüm suite koşar.'
        )
        string(
            name: 'APPIUM_URL',
            defaultValue: 'http://host.docker.internal:4723',
            description: 'Appium Sunucu URL adresi (Docker konteyneri içerisinden host makineye erişim)'
        )
        booleanParam(
            name: 'CLEAN_BUILD',
            defaultValue: true,
            description: 'Test öncesinde target dizinini temizle (mvn clean)'
        )
    }

    environment {
        MAVEN_OPTS = "-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn"
        APPIUM_SERVER_URL = "${params.APPIUM_URL}"
    }

    stages {
        stage('Environment Diagnostic') {
            steps {
                script {
                    echo "=========================================================="
                    echo "🚀 QA Mobile Automation CI Pipeline Başlatıldı"
                    echo "📱 Platform: ${params.PLATFORM}"
                    echo "🧪 Özel Test: ${params.CUSTOM_TEST.isEmpty() ? 'Tüm Suite' : params.CUSTOM_TEST}"
                    echo "🌐 Appium URL: ${params.APPIUM_URL}"
                    echo "=========================================================="
                    sh 'java -version'
                    sh 'mvn -version'
                    sh 'allure --version || echo "Allure CLI globalde bulunamadı, Maven plugin kullanılacak."'
                }
            }
        }

        stage('Appium Connectivity Check') {
            steps {
                script {
                    echo "🔍 Appium sunucusuna erişim test ediliyor: ${params.APPIUM_URL}/status"
                    sh """
                        curl -s -f ${params.APPIUM_URL}/status || echo "UYARI: Appium sunucusuna ulaşılamadı. Lütfen sunucunun açık olduğundan emin olun."
                    """
                }
            }
        }

        stage('Execute Mobile Tests') {
            steps {
                script {
                    def cleanGoal = params.CLEAN_BUILD ? 'clean' : ''
                    def testCommand = "mvn ${cleanGoal} test -Dappium.server.url=${params.APPIUM_URL}"

                    if (params.CUSTOM_TEST && !params.CUSTOM_TEST.trim().isEmpty()) {
                        testCommand += " -Dtest=${params.CUSTOM_TEST.trim()} -Dplatform=${params.PLATFORM.toUpperCase()}"
                    } else {
                        testCommand += " -P${params.PLATFORM}"
                    }

                    echo "⚡ Çalıştırılan Maven Komutu: ${testCommand}"
                    sh "${testCommand}"
                }
            }
        }
    }

    post {
        always {
            script {
                echo "📊 Test sonuçları ve Allure raporları toplanıyor..."

                // Allure Sonuçlarını derle (Eğer eklenti kuruluysa çalışır)
                try {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                } catch (Exception e) {
                    echo "Allure Jenkins eklentisi bulunamadı veya rapor oluşturulamadı: ${e.message}"
                }

                // TestNG XML raporlarını arşivle
                try {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                } catch (Exception e) {
                    echo "JUnit eklentisi bulunamadı: ${e.message}"
                }

                // Ekran görüntüleri ve logları arşivle
                archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
            }
        }
        success {
            echo "✅ TÜM MOBİL TESTLER BAŞARIYLA TAMAMLANDI!"
        }
        failure {
            echo "❌ BAZI TESTLER BAŞARISIZ OLDU VEYA HATA ALINDI! Lütfen Allure raporunu ve logları inceleyin."
        }
    }
}
