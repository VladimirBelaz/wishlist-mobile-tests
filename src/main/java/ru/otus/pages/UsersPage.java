package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.FilterUserComponent;
import ru.otus.components.UsersContent;

import static com.codeborne.selenide.Condition.visible;

@Singleton
public class UsersPage extends AbsBasePage {

    private final UsersContent usersContent =
            new UsersContent(
                    elementById(
                            "ru.otus.wishlist:id/users_content",
                            "Список пользователей"
                    )
            );

    public UsersPage open() {
        bottomNavigation.goToUsers();
        usersContent.shouldBe(visible);
        return this;
    }

    public UsersContent users() {
        return usersContent;
    }

    public FilterUserComponent clickFilter() {
        topAppBar.clickFilter();

        return new FilterUserComponent(
                elementById(
                        "ru.otus.wishlist:id/users_filter_bottom_sheet",
                        "Окно фильтрации пользователей"
                )
        ).shouldBeVisible();
    }

    public UsersPage searchUser(String username) {
        FilterUserComponent filter = clickFilter();
        filter.setUsername(username);
        filter.apply();

        return this;
    }

    public UsersPage assertNumberOfUsers(int expected) {
        usersContent.assertSizeEqualTo(expected);
        return this;
    }

    public UsersPage assertUserName(int index, String expected) {
        usersContent.get(index).shouldBe(visible).assertNameEqualsTo(expected);
        return this;
    }

    public UsersPage clickUser(int index) {
        usersContent.get(index).shouldBe(visible).click();
        return this;
    }
}