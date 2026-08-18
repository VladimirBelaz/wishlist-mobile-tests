package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static io.appium.java_client.AppiumBy.id;

public class GiftContent extends AbsComponent<GiftContent> {

    private final ElementsCollection items =
            root.$$(id("ru.otus.wishlist:id/gift_item")); // предположительный ID

    public GiftContent(SelenideElement root) {
        super(root);
    }

    public GiftItem get(int index) {
        return new GiftItem(items.get(index - 1));
    }

    public void assertSizeEqualTo(int expected) {
        items.shouldHave(size(expected));
    }
}