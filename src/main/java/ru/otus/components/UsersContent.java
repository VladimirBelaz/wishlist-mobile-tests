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

    public UserItem findByName(String username) {
        for (SelenideElement item : items) {
            UserItem user = new UserItem(item);
            if (username.equals(user.getName())) {
                return user;
            }
        }
        throw new AssertionError("User not found: " + username);
    }

    public void assertSizeEqualTo(int expected) {
        items.shouldHave(size(expected));
    }
}