package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class UsersPage extends AbsBasePage {

    public UsersPage open() {
        $(id("ru.otus.wishlist:id/users_menu")).shouldBe(visible).click();
        // Ждём загрузки списка пользователей
        $(id("ru.otus.wishlist:id/users_content")).shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public void selectUser(String username) {
        // Ищем элемент с текстом username в списке
        $(By.xpath("//android.widget.TextView[@resource-id='ru.otus.wishlist:id/username' and @text='" + username + "']"))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
    }
}