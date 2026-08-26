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
        return new FilterUserComponent().shouldBeVisible();
    }

    public UsersPage searchAndSelectUser(String username) {
        FilterUserComponent filter = clickFilter();
        filter.setUsername(username);
        filter.apply();
        usersContent.findByName(username).click();
        return this;
    }
}