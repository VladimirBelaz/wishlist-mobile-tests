package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.database.TestDataManager;
import ru.otus.extension.AndroidExtension;
import ru.otus.pages.GiftListPage;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;
import ru.otus.pages.UsersPage;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AndroidExtension.class)
public class ReserveGiftTest {

    @Inject
    private LoginPage loginPage;
    @Inject
    private MyWishlistsPage myWishlistsPage;
    @Inject
    private GiftListPage giftListPage;
    @Inject
    private UsersPage usersPage;
    @Inject
    private TestDataManager testDataManager;

    @Test
    void reserveGift() {
        String myLogin = "belozerovvd";
        String otherLogin = "omelchenkoav";
        String wishlistTitle = "День рождения";

        testDataManager.resetAllReservationsForUser(otherLogin);

        UUID giftId = testDataManager.getFirstGiftIdForUser(otherLogin);

        loginPage.login(myLogin, "psbDemo2026");

        usersPage.open();

        usersPage.searchAndSelectUser(otherLogin);

        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .clickWishlist(1);

        giftListPage
                .assertNumberOfGifts(1)
                .toggleReservation(1);

        boolean reserved = testDataManager.isGiftReserved(giftId);
        assertTrue(reserved, "Подарок должен быть зарезервирован");
    }
}