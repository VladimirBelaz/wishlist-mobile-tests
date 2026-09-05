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
                sh "wget -O app.apk ${params.APK_URL}"
            }
        }

        stage('Run Appium tests') {
            steps {
                sh """
                    # Здесь будет запуск Appium тестов
                    echo "Running Appium tests..."
                    # Пример: gradle clean test -Dapk.path=./app.apk
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