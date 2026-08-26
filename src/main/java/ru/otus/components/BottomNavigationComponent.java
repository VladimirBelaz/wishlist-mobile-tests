package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class BottomNavigationComponent extends AbsComponent<BottomNavigationComponent> {

    private final SelenideElement mineMenu =
            root.$(id("ru.otus.wishlist:id/mine_menu")).as("Вкладка Мои желания");
    private final SelenideElement usersMenu =
            root.$(id("ru.otus.wishlist:id/users_menu")).as("Вкладка Пользователи");
    private final SelenideElement profileMenu =
            root.$(id("ru.otus.wishlist:id/profile_menu")).as("Вкладка Профиль");

    public BottomNavigationComponent(SelenideElement root) {
        super(root);
    }

    public BottomNavigationComponent shouldBeVisible() {
        root.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public void goToMine() {
        mineMenu.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public void goToUsers() {
        usersMenu.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public void goToProfile() {
        profileMenu.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public boolean isMineActive() {
        return "true".equals(mineMenu.getAttribute("selected"));
    }
}