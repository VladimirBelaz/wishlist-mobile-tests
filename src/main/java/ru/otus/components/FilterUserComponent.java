package ru.otus.components;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

public class FilterUserComponent extends AbsComponent<FilterUserComponent> {

    private final SelenideElement usernameInput =
            root.$(id("ru.otus.wishlist:id/username_input"));

    private final SelenideElement applyButton =
            root.$(id("ru.otus.wishlist:id/apply_button"));

    public FilterUserComponent(SelenideElement root) {
        super(root);
    }

    public FilterUserComponent shouldBeVisible() {
        root.shouldBe(visible);
        usernameInput.shouldBe(visible);
        applyButton.shouldBe(visible);
        return this;
    }

    public FilterUserComponent setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }

    public void apply() {
        applyButton.click();
    }
}