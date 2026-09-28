stage('Run Appium tests') {
    steps {
        sh '''
            docker compose up -d
            docker cp wiremock/. wiremock:/home/wiremock/

            echo "Waiting for device to appear in adb..."
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
                docker logs android-1 --tail 80 || true
                exit 1
            fi

            echo "Waiting for Android system to fully boot (sys.boot_completed=1)..."
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
                docker logs android-1 --tail 80 || true
                exit 1
            fi

            echo "Emulator fully ready. Waiting extra 60s for Android to settle..."
            sleep 60
            docker exec android-1 adb devices

            mvn clean test -DdatabaseUserName=student -DdatabasePassword=student -DappiumHost=host.docker.internal
        '''
    }
}