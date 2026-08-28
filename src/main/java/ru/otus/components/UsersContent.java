package ru.otus.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.CollectionCondition.size;

public class UsersContent extends AbsComponent<UsersContent> {

    private final ElementsCollection items =
            root.$$(AppiumBy.id("ru.otus.wishlist:id/user_item"));

    public UsersContent(SelenideElement root) {
        super(root);
    }

    public UserItem get(int index) {
        return new UserItem(items.get(index - 1));
    }

    public void assertSizeEqualTo(int expected) {
        items.shouldHave(size(expected));
    }
}