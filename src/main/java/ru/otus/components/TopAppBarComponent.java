package ru.otus.components;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class TopAppBarComponent extends AbsComponent<TopAppBarComponent> {

    private final SelenideElement title = root.$(AppiumBy.className("android.widget.TextView"));
    private final SelenideElement filterButton = $(AppiumBy.id("ru.otus.wishlist:id/filter"));

    public TopAppBarComponent() {
        super($(AppiumBy.id("ru.otus.wishlist:id/top_app_bar")));
    }

    public TopAppBarComponent shouldBeVisible() {
        root.shouldBe(visible);
        return this;
    }

    public TopAppBarComponent shouldHaveTitle(String expectedTitle) {
        title.shouldBe(visible).shouldHave(text(expectedTitle));
        return this;
    }

    public void clickFilter() {
        filterButton.shouldBe(visible).click();
    }

    public boolean isFilterDisplayed() {
        return filterButton.isDisplayed();
    }
}