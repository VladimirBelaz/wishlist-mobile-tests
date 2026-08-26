package ru.otus.components;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Condition.visible;

public class UserItem extends AbsComponent<UserItem> {

    private final SelenideElement name =
            root.$(AppiumBy.id("ru.otus.wishlist:id/username"));

    public UserItem(SelenideElement root) {
        super(root);
    }

    public String getName() {
        return name.shouldBe(visible).getText();
    }
}