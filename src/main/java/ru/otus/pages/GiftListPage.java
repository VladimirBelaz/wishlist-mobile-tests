package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import ru.otus.components.EditGiftComponent;
import ru.otus.components.GiftContent;
import ru.otus.components.GiftItem;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class GiftListPage extends AbsBasePage {

    private final GiftContent giftContent =
            new GiftContent($(id("ru.otus.wishlist:id/gifts_content"))); // предположительный ID
    private final EditGiftComponent editGiftComponent =
            new EditGiftComponent($(id("ru.otus.wishlist:id/gift_edit_bottom_sheet")));

    private final SelenideElement addGiftButton =
            $(id("ru.otus.wishlist:id/add_button"))
                    .as("Кнопка добавления подарка");

    public GiftListPage assertNumberOfGifts(int expected) {
        giftContent.shouldBe(visible).assertSizeEqualTo(expected);
        return this;
    }

    public GiftListPage assertGiftTitle(int index, String value) {
        getGiftItem(index).assertTitleEqualsTo(value);
        return this;
    }

    public GiftListPage assertGiftPrice(int index, String value) {
        getGiftItem(index).assertPriceEqualsTo(value);
        return this;
    }

    public GiftListPage tapEditGift(int index) {
        getGiftItem(index).tapEdit();
        return this;
    }

    public GiftListPage tapAddGift() {
        addGiftButton.shouldBe(visible).click();
        return this;
    }

    public GiftListPage editGift(String name, String price, String description) {
        editGiftComponent.shouldBe(visible).editGift(name, price, description);
        $(id("ru.otus.wishlist:id/gifts_content")).shouldBe(visible);
        return this;
    }

    public GiftListPage assertEditGiftTitle(String expected) {
        editGiftComponent.assertComponentTitle(expected);
        return this;
    }

    public GiftListPage toggleReservation(int index) {
        getGiftItem(index).toggleReservation();
        return this;
    }

    private GiftItem getGiftItem(int index) {
        return giftContent.get(index).shouldBe(visible);
    }
}