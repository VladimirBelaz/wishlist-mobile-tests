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
                    echo "=== Cleaning up previous containers ==="
                    docker compose down --remove-orphans || true
                    docker rm -f android-1 android-2 wiremock 2>/dev/null || true

                    echo "=== Starting fresh containers ==="
                    docker compose up -d
                    docker cp wiremock/. wiremock:/home/wiremock/

                    echo "=== Waiting for device to appear in adb (up to 10 min) ==="
                    READY=0
                    for i in $(seq 1 60); do
                        if docker exec android-1 adb devices 2>/dev/null | grep -q "device$"; then
                            echo "Device appeared in adb after $i attempts"
                            READY=1
                            break
                        fi
                        echo "Attempt $i: device not in adb yet"
                        sleep 10
                    done

                    if [ "$READY" != "1" ]; then
                        echo "ERROR: Device did not appear in adb in 10 minutes"
                        echo "=== Container logs ==="
                        docker logs android-1 --tail 100 || true
                        echo "=== KVM check ==="
                        ls -la /dev/kvm || true
                        exit 1
                    fi

                    echo "=== Waiting for Android system to fully boot (sys.boot_completed=1, up to 10 min) ==="
                    BOOTED=0
                    for i in $(seq 1 60); do
                        BOOT=$(docker exec android-1 adb shell getprop sys.boot_completed 2>/dev/null | tr -d "\\r")
                        if [ "$BOOT" = "1" ]; then
                            echo "Android fully booted after $i attempts"
                            BOOTED=1
                            break
                        fi
                        echo "Attempt $i: sys.boot_completed=$BOOT"
                        sleep 10
                    done

                    if [ "$BOOTED" != "1" ]; then
                        echo "ERROR: Android did not fully boot in 10 minutes"
                        docker logs android-1 --tail 100 || true
                        exit 1
                    fi

                    echo "=== Emulator fully ready. Waiting extra 60s for Android services to settle ==="
                    sleep 60
                    docker exec android-1 adb devices

                    echo "=== Running Maven tests ==="
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