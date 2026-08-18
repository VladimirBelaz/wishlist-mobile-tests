package ru.otus.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static io.appium.java_client.AppiumBy.id;

public class EditGiftComponent extends AbsComponent<EditGiftComponent> {

    private final SelenideElement title =
            root.$(id("ru.otus.wishlist:id/gift_edit_title"));
    private final SelenideElement nameInput =
            root.$(id("ru.otus.wishlist:id/name_input"));   // было gift_name_input
    private final SelenideElement priceInput =
            root.$(id("ru.otus.wishlist:id/price_input"));  // было gift_price_input
    private final SelenideElement descriptionInput =
            root.$(id("ru.otus.wishlist:id/description_input")); // было gift_description_input
    private final SelenideElement saveButton =
            root.$(id("ru.otus.wishlist:id/save_button"));

    public EditGiftComponent(SelenideElement root) {
        super(root);
    }

    public void assertComponentTitle(String expected) {
        title.shouldBe(visible).shouldHave(text(expected));
    }

    public void editGift(String name, String price, String description) {
        nameInput.shouldBe(visible).clear();
        nameInput.sendKeys(name);
        priceInput.shouldBe(visible).clear();
        priceInput.sendKeys(price);
        descriptionInput.shouldBe(visible).clear();
        descriptionInput.sendKeys(description);
        saveButton.shouldBe(visible).click();
    }
}