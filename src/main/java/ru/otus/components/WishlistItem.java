package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class WishlistItem extends AbsComponent<WishlistItem> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/title"));
    private final SelenideElement subtitle =
            root.$(id("ru.otus.wishlist:id/subtitle"));
    private final SelenideElement editButton =
            root.$(id("ru.otus.wishlist:id/edit_button"));

    public WishlistItem(SelenideElement root) {
        super(root);
    }

    public void assertTitleEqualsTo(String value) {
        title.shouldHave(text(value));
    }

    public void assertSubtitleEqualsTo(String value) {
        subtitle.shouldHave(text(value));
    }

    public void tapEdit() {
        editButton
                .shouldBe(visible)
                .click();
    }

    public void click() {
        root
                .shouldBe(visible)
                .click();
    }
}
