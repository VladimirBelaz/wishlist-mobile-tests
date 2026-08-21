package ru.otus;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.database.TestDataManager;
import ru.otus.entity.Gift;
import ru.otus.extension.AndroidExtension;
import ru.otus.pages.GiftListPage;
import ru.otus.pages.LoginPage;
import ru.otus.pages.MyWishlistsPage;

import java.math.BigDecimal;

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

        Gift originalGift = new Gift("Зонтик", new BigDecimal("50.00"), "На пляже буду лежать и кайфовать");
        testDataManager.prepareGift(login, originalGift);

        String newName = "Шляпа";
        BigDecimal newPrice = new BigDecimal("100.00");
        String newDescription = "Чтобы не обгореть";

        loginPage.login(login, "psbDemo2026");
        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .clickWishlist(1);

        giftListPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, "Зонтик")
                .assertGiftPrice(1, new BigDecimal("50.00"))
                .tapEditGift(1)
                .editGift(newName, newPrice.toString(), newDescription)
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, newName)
                .assertGiftPrice(1, newPrice);
    }
}