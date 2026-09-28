pipeline {
    agent any

    parameters {
        string(name: 'BRANCH', defaultValue: 'main')
        string(name: 'APK_URL', defaultValue: 'https://raw.githubusercontent.com/VladimirBelaz/wishlist-mobile-tests/main/wiremock/__files/wishlist.apk')
    }

    triggers {
        pollSCM('H/5 * * * *')
        cron('H 0 * * *')
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
                sh '''
                    docker compose up -d
                    docker cp wiremock/. wiremock:/home/wiremock/

                    echo "Waiting for emulator to boot (up to 15 minutes)..."
                    READY=0
                    for i in $(seq 1 60); do
                        if docker exec android-1 adb devices 2>/dev/null | grep -q "device$"; then
                            echo "Emulator is ready after $i attempts"
                            READY=1
                            break
                        fi
                        echo "Attempt $i: emulator not ready yet"
                        sleep 15
                    done

                    if [ "$READY" != "1" ]; then
                        echo "ERROR: Emulator did not boot in 15 minutes"
                        docker exec android-1 adb devices || true
                        docker logs android-1 --tail 80 || true
                        exit 1
                    fi

                    docker exec android-1 adb devices

                    mvn clean test -DdatabaseUserName=student -DdatabasePassword=student -DappiumHost=host.docker.internal
                '''
            }
        }

        stage('Publish Allure report') {
            steps {
                allure([
                        includeProperties: false,
                        results: [[path: 'target/allure-results']]
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