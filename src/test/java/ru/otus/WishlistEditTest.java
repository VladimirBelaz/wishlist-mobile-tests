package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.database.TestDataManager;
import ru.otus.extension.AndroidExtension;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;

@ExtendWith(AndroidExtension.class)
public class WishlistEditTest {

    @Inject
    private LoginPage loginPage;
    @Inject
    private MyWishlistsPage myWishlistPage;
    @Inject
    private TestDataManager testDataManager;

    @Test
    void editWishlist() {
        String login = "belozerovvd";
        String wishlistTitle = "Отпуск";
        String wishlistDescription = "А есть ли жизнь после работы?";
        String newWishlistDescription = "Не все долото, что блестит!";

        testDataManager.prepareWishListDescription(login, wishlistDescription);

        loginPage.login(login, "psbDemo2026");
        myWishlistPage.open()
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, wishlistDescription)
                .tapEditWishlist(1)
                .editWishlistDescription(newWishlistDescription)
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .assertWishlistSubtitle(1, newWishlistDescription);
    }
}