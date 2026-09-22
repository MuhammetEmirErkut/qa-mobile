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
            description: 'Target mobile platform execution profile'
        )
        string(
            name: 'CUSTOM_TEST',
            defaultValue: '',
            description: 'Single test class or method name (e.g. DualWebviewTest or LoginTest#testValidLoginAndLogout). Runs entire suite if empty.'
        )
        string(
            name: 'APPIUM_URL',
            defaultValue: 'http://host.docker.internal:4723',
            description: 'Appium Server URL (Accessible from inside Docker container to host machine)'
        )
        booleanParam(
            name: 'CLEAN_BUILD',
            defaultValue: true,
            description: 'Clean target directory before test execution (mvn clean)'
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
                    echo "🚀 QA Mobile Automation CI Pipeline Started"
                    echo "📱 Platform: ${params.PLATFORM}"
                    echo "🧪 Custom Test: ${params.CUSTOM_TEST.isEmpty() ? 'Full Suite' : params.CUSTOM_TEST}"
                    echo "🌐 Appium URL: ${params.APPIUM_URL}"
                    echo "=========================================================="
                    sh 'java -version'
                    sh 'mvn -version'
                    sh 'allure --version || echo "Allure CLI not found globally, Maven plugin will be used."'
                }
            }
        }

        stage('Appium Connectivity Check') {
            steps {
                script {
                    echo "🔍 Testing Appium server connectivity: ${params.APPIUM_URL}/status"
                    sh """
                        curl -s -f ${params.APPIUM_URL}/status || echo "WARNING: Unable to reach Appium server. Please ensure the server is running."
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

                    echo "⚡ Executing Maven Command: ${testCommand}"
                    sh "${testCommand}"
                }
            }
        }
    }

    post {
        always {
            script {
                echo "📊 Collecting test results and Allure reports..."

                // Compile Allure results if plugin is present
                try {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                } catch (Exception e) {
                    echo "Allure Jenkins plugin not installed or failed to generate report: ${e.message}"
                }

                // Archive TestNG XML reports
                try {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                } catch (Exception e) {
                    echo "JUnit plugin not installed: ${e.message}"
                }

                // Archive screenshots and logs
                archiveArtifacts artifacts: 'target/surefire-reports/**', allowEmptyArchive: true
            }
        }
        success {
            echo "✅ ALL MOBILE TESTS COMPLETED SUCCESSFULLY!"
        }
        failure {
            echo "❌ SOME TESTS FAILED OR ENCOUNTERED AN ERROR! Please inspect Allure report and build logs."
        }
    }
}
