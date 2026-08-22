package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.EditGiftComponent;
import ru.otus.components.GiftContent;
import ru.otus.components.GiftItem;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.visible;

@Singleton
public class GiftListPage extends AbsBasePage {

    private final GiftContent giftContent =
            new GiftContent(
                    elementById(
                            "ru.otus.wishlist:id/gifts_content",
                            "Содержимое списка подарков"
                    )
            );

    private final EditGiftComponent editGiftComponent =
            new EditGiftComponent(
                    elementById(
                            "ru.otus.wishlist:id/gift_edit_bottom_sheet",
                            "Окно редактирования подарка"
                    )
            );

    public GiftListPage assertNumberOfGifts(int expected) {
        giftContent.shouldBe(visible).assertSizeEqualTo(expected);
        return this;
    }

    public GiftListPage assertGiftTitle(int index, String expected) {
        getGiftItem(index).assertTitleEqualsTo(expected);
        return this;
    }

    public GiftListPage assertGiftPrice(int index, BigDecimal expected) {
        // Убираем лишние нули и добавляем символ валюты
        String expectedStr = expected.stripTrailingZeros().toPlainString() + " ₽";
        getGiftItem(index).assertPriceEqualsTo(expectedStr);
        return this;
    }

    public GiftListPage assertGiftDescription(int index, String value) {
        getGiftItem(index).assertSubtitleEqualsTo(value);
        return this;
    }

    public GiftListPage tapEditGift(int index) {
        getGiftItem(index).tapEdit();
        return this;
    }

    public GiftListPage editGift(String name, String price, String description) {
        editGiftComponent.editGift(name, price, description);
        return this;
    }

    public GiftListPage toggleReservation(int index) {
        getGiftItem(index).toggleReservation();
        return this;
    }

    public GiftListPage assertReserved(int index, boolean expected) {
        getGiftItem(index).assertReserved(expected);
        return this;
    }

    private GiftItem getGiftItem(int index) {
        return giftContent.get(index).shouldBe(visible);
    }
}