package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;

@Singleton
public class LoginPage extends AbsBasePage {

    private final SelenideElement usernameInputField =
            elementById(
                    "ru.otus.wishlist:id/username_text_input",
                    "Поле ввода имени пользователя");

    private final SelenideElement passwordInputField =
            elementById(
                    "ru.otus.wishlist:id/password_text_input",
                    "Поле ввода пароля");

    private final SelenideElement logInButton =
            elementById(
                    "ru.otus.wishlist:id/log_in_button",
                    "Кнопка входа");

    public void login(String username, String password) {
        shouldBeVisible(
                usernameInputField,
                "Поле ввода имени пользователя не видно на экране");
        usernameInputField.sendKeys(username);

        shouldBeVisible(
                passwordInputField,
                "Поле ввода пароля не видно на экране");
        passwordInputField.sendKeys(password);

        shouldBeVisible(
                logInButton,
                "Кнопка входа не видна на экране");
        logInButton.click();
    }
}