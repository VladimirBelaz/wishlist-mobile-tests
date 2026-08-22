package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import ru.otus.components.BottomNavigationComponent;

@Singleton
public class UsersPage extends AbsBasePage {

    private final BottomNavigationComponent bottomNavigation =
            new BottomNavigationComponent(
                    elementById(
                            "ru.otus.wishlist:id/bottom_navigation",
                            "Нижняя навигация"
                    )
            );

    private final SelenideElement usersContent =
            elementById(
                    "ru.otus.wishlist:id/users_content",
                    "Список пользователей"
            );

    private final SelenideElement filterButton =
            elementById(
                    "ru.otus.wishlist:id/filter",
                    "Кнопка фильтрации пользователей"
            );

    private final SelenideElement usernameInput =
            elementById(
                    "ru.otus.wishlist:id/username_input",
                    "Поле фильтрации по имени пользователя"
            );

    private final SelenideElement applyButton =
            elementById(
                    "ru.otus.wishlist:id/apply_button",
                    "Кнопка применения фильтра"
            );

    private final SelenideElement userItem =
            elementById(
                    "ru.otus.wishlist:id/user_item",
                    "Пользователь в списке"
            );

    public UsersPage open() {
        bottomNavigation.goToUsers();
        shouldBeVisible(usersContent, "Список пользователей не загрузился");
        return this;
    }

    public void searchAndSelectUser(String username) {
        shouldBeVisible(filterButton, "Кнопка фильтрации пользователей не отображается");
        filterButton.click();

        shouldBeVisible(usernameInput, "Поле фильтрации пользователей не отображается");
        usernameInput.sendKeys(username);

        shouldBeVisible(applyButton, "Кнопка применения фильтра не отображается");
        applyButton.click();

        shouldBeVisible(userItem, "Пользователь " + username + " не найден после фильтрации");
        userItem.click();
    }
}