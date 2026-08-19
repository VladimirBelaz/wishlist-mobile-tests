package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import ru.otus.pageobject.AbsPageObject;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

public abstract class AbsBasePage extends AbsPageObject {

    protected SelenideElement elementById(String resourceId, String description) {
        return $(id(resourceId)).as(description);
    }

    protected void shouldBeVisible(SelenideElement element, String description) {
        element.shouldBe(
                visible.because(description)
        );
    }
}