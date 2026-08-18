package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class GiftItem extends AbsComponent<GiftItem> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/title"));   // вместо gift_title
    private final SelenideElement price =
            root.$(id("ru.otus.wishlist:id/price"));   // вместо gift_price
    private final SelenideElement editButton =
            root.$(id("ru.otus.wishlist:id/edit_button")); // вместо edit_gift_button
    private final SelenideElement reserveToggle =
            root.$(id("ru.otus.wishlist:id/reserve_toggle")); // предположительно

    public GiftItem(SelenideElement root) {
        super(root);
    }

    public void assertTitleEqualsTo(String value) {
        title.shouldHave(text(value));
    }

    public void assertPriceEqualsTo(String value) {
        price.shouldHave(text(value));
    }

    public void tapEdit() {
        editButton.shouldBe(visible).click();
    }

    public void toggleReservation() {
        reserveToggle.shouldBe(visible).click();
    }
}