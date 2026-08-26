package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class EditGiftComponent extends AbsComponent<EditGiftComponent> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/gift_edit_title"));
    private final SelenideElement nameInput =
            root.$(id("ru.otus.wishlist:id/name_input"));
    private final SelenideElement priceInput =
            root.$(id("ru.otus.wishlist:id/price_input"));
    private final SelenideElement descriptionInput =
            root.$(id("ru.otus.wishlist:id/description_input"));
    private final SelenideElement saveButton =
            root.$(id("ru.otus.wishlist:id/save_button"));

    public EditGiftComponent(SelenideElement root) {
        super(root);
    }

    public void assertComponentTitle(String expected) {
        title.shouldBe(visible).shouldHave(text(expected));
    }

    public void editGift(String name, BigDecimal price, String description) {
        nameInput.shouldBe(visible).sendKeys(name);
        priceInput.shouldBe(visible).sendKeys(price.toPlainString());
        descriptionInput.shouldBe(visible).sendKeys(description);
        saveButton.shouldBe(visible).click();
    }
}