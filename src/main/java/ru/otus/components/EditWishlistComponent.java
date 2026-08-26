package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class EditWishlistComponent extends AbsComponent<EditWishlistComponent> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/wishlist_edit_title"));
    private final SelenideElement wishListDescriptionInputField =
            root.$(id("ru.otus.wishlist:id/description_input"));
    private final SelenideElement saveButton =
            root.$(id("ru.otus.wishlist:id/save_button"));

    public EditWishlistComponent(SelenideElement root) {
        super(root);
    }

    public void assertComponentTitle(String expected) {
        title.shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text(expected));
    }

    public void editDescription(String description) {
        wishListDescriptionInputField.shouldBe(visible, Duration.ofSeconds(10)).sendKeys(description);
        saveButton.shouldBe(visible, Duration.ofSeconds(10)).click();
    }
}