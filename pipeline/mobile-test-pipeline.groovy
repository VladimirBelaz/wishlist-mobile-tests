pipeline {
    agent any

    parameters {
        string(name: 'BRANCH', defaultValue: 'main')
        string(name: 'APK_URL', defaultValue: 'https://raw.githubusercontent.com/VladimirBelaz/wishlist-mobile-tests/main/wiremock/__files/wishlist.apk')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Download APK') {
            steps {
                sh "curl -L -o app.apk ${params.APK_URL}"
            }
        }

        stage('Run Appium tests') {
            steps {
                sh """
            # Запускаем Docker-инфраструктуру (эмуляторы, WireMock, Appium)
            docker compose up -d

            # Ждём загрузки эмулятора
            sleep 30

            # Запускаем тесты через Maven с параметрами из README
            mvn clean test -DdatabaseUserName=student -DdatabasePassword=student
        """
            }
        }

        stage('Publish Allure report') {
            steps {
                allure([
                        includeProperties: false,
                        results: [[path: 'build/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}