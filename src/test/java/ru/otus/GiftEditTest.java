package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.database.TestDataManager;
import ru.otus.extension.AndroidExtension;
import ru.otus.pages.GiftListPage;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(AndroidExtension.class)
public class GiftEditTest {

    @Inject
    private LoginPage loginPage;
    @Inject
    private MyWishlistsPage myWishlistsPage;
    @Inject
    private GiftListPage giftListPage;
    @Inject
    private TestDataManager testDataManager;

    @Test
    void editGift() {
        String login = "belozerovvd";
        String wishlistTitle = "Отпуск";

        UUID giftId = testDataManager.getFirstGiftIdForUser(login);

        testDataManager.prepareGift(
                giftId,
                "Зонтик",
                "На пляже буду лежать и кайфовать",
                new BigDecimal("50.00"),
                false
        );

        String newName = "Шляпа";
        String newPrice = "100";
        String newDescription = "Чтобы не обгореть";

        loginPage.login(login, "psbDemo2026");
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .clickWishlist(1);

        giftListPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, "Зонтик")
                .assertGiftPrice(1, "50 ₽")
                .tapEditGift(1)
                .editGift(newName, newPrice, newDescription)
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, newName)
                .assertGiftPrice(1, newPrice + " ₽");
    }
}