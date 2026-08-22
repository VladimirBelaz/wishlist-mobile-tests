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
        // Используем другого пользователя (omelchenkoav)
        String login = "omelchenkoav";
        String wishlistTitle = "День рождения";   // существующий список у omelchenkoav

        // Подготавливаем подарок "Бентли" (он уже есть, но обновим на всякий случай)
        Gift originalGift = new Gift("Бентли", BigDecimal.valueOf(400), "Скромно но со вкусом");
        testDataManager.prepareGift(login, originalGift);

        String newName = "Шляпа";
        BigDecimal newPrice = BigDecimal.valueOf(100);
        String newDescription = "Чтобы не обгореть";

        loginPage.login(login, "P@SSw0rd");   // пароль для omelchenkoav

        myWishlistsPage
                .assertNumberOfWishlists(1)
                .assertWishlistTitle(1, wishlistTitle)
                .clickWishlist(1);

        giftListPage
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, "Бентли")
                .assertGiftPrice(1, BigDecimal.valueOf(400))
                .tapEditGift(1)
                .editGift(newName, newPrice.toString(), newDescription)
                .assertNumberOfGifts(1)
                .assertGiftTitle(1, newName)
                .assertGiftPrice(1, newPrice);
    }
}