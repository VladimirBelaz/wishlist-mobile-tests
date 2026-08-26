package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class GiftItem extends AbsComponent<GiftItem> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/title"));
    private final SelenideElement subtitle =
            root.$(id("ru.otus.wishlist:id/subtitle"));
    private final SelenideElement price =
            root.$(id("ru.otus.wishlist:id/price"));
    private final SelenideElement editButton =
            root.$(id("ru.otus.wishlist:id/edit_button"));
    private final SelenideElement reserveToggle =
            root.$(id("ru.otus.wishlist:id/reserved"));

    public GiftItem(SelenideElement root) {
        super(root);
    }

    public void assertTitleEqualsTo(String value) {
        title.shouldHave(text(value));
    }

    public void assertPriceEqualsTo(BigDecimal value) {
        String expected = value.stripTrailingZeros().toPlainString() + " ₽";
        price.shouldHave(text(expected));
    }

    public void assertSubtitleEqualsTo(String value) {
        subtitle.shouldHave(text(value));
    }

    public void tapEdit() {
        editButton.shouldBe(visible).click();
    }

    public void toggleReservation() {
        reserveToggle.shouldBe(visible).click();
    }

    public void assertReserved(boolean expected) {
        String expectedValue = expected ? "true" : "false";
        reserveToggle.shouldHave(attribute("checked", expectedValue));
    }
}