# Wishlist Mobile Tests

Автотесты для мобильного приложения Wishlist с использованием Selenide, Appium и Docker.

## 🚀 Тесты

В проекте реализованы следующие сценарии:

- **Редактирование списка желаний** – пользователь изменяет описание списка.
- **Редактирование подарка** – пользователь изменяет название, цену и описание подарка.
- **Резервирование подарка** (в разработке) – переключение статуса резервирования у подарка другого пользователя.

На данный момент два теста успешно проходят, третий тест временно отключён и будет доделан в ближайшее время.

## 🛠️ Требования

- Windows 11 с WSL2 (Ubuntu 24.04) или Linux
- Docker (установленный внутри WSL Ubuntu)
- Java 17+
- Maven
- Android SDK (устанавливается внутри Docker-образа)

## 🐳 Инфраструктура

Инфраструктура разворачивается через Docker Compose:
- два Android-эмулятора (Android 12 и Android 14)
- WireMock для раздачи APK-файла приложения
- Appium внутри контейнера

Для ускорения эмулятора используется аппаратная виртуализация (KVM) через проброс `/dev/kvm`.

## 🧪 Запуск тестов

### 1. Запустите Docker-контейнеры

```bash
cd ~/project
docker compose up -d
```

### 2. Дождитесь загрузки эмулятора

```bash
docker exec android-1 adb devices
```

Должен появиться `emulator-5554   device`.

### 3. Выполните тесты

```bash
mvn clean test -DdatabaseUserName=ваш_логин -DdatabasePassword=ваш_пароль
```

### 4. Запуск отдельных тестов

```bash
mvn clean test -Dtest=WishListEditTest -DdatabaseUserName=... -DdatabasePassword=...
mvn clean test -Dtest=GiftEditTest -DdatabaseUserName=... -DdatabasePassword=...
```

## 📊 Результаты

- ✅ WishListEditTest – пройден
- ✅ GiftEditTest – пройден
- ⏳ ReserveGiftTest – в разработке (требуется уточнить локатор переключателя резервирования)

## 📁 Структура проекта

- `src/main/java/ru/otus/pages` – Page Object'ы
- `src/main/java/ru/otus/components` – компоненты UI
- `src/main/java/ru/otus/database` – работа с БД через JDBC
- `src/main/java/ru/otus/factory` – фабрики для Appium и Guice
- `src/test/java/ru/otus` – тесты

## 📜 Лицензия

Учебный проект, создан в рамках курса OTUS.