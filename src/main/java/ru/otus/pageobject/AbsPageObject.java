package ru.otus.pageobject;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

public abstract class AbsPageObject {

    protected SelenideElement shouldBeVisible(SelenideElement element, String description) {
        return element.shouldBe(visible.because(description));
    }
}