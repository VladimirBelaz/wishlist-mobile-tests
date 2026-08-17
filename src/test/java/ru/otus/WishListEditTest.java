package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.database.TestDataManager;
import ru.otus.extension.AndroidExtension;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;

@ExtendWith(AndroidExtension.class)
public class WishListEditTest {

    @Inject
    private LoginPage loginPage;
    @Inject
    private MyWishlistsPage myWishListPage;
    @Inject
    private TestDataManager testDataManager;

    @Test
    void editWishlist() {
        String login = "belozerovvd";
        String wishlistTitle = "Новый год";
        String wishlistDescription = "К нам мчится, скоро все случится";
        String newWishlistDescription = "К нам уже не мчится";

        testDataManager.prepareWishListDescription(login, wishlistDescription);

        loginPage.login(login, "psbDemo2026");
        myWishListPage
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
