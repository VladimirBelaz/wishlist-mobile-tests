package ru.otus.pages;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import ru.otus.components.EditWishlistComponent;
import ru.otus.components.WishlistContent;
import ru.otus.components.WishlistItem;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;

@Singleton
public class MyWishlistsPage extends AbsBasePage {

    private final WishlistContent wishlistContent =
            new WishlistContent($(id("ru.otus.wishlist:id/wishlists_content")));
    private final EditWishlistComponent editWishlistComponent =
            new EditWishlistComponent($(id("ru.otus.wishlist:id/wishlist_edit_bottom_sheet")));

    public MyWishlistsPage assertNumberOfWishlists(int expected) {
        wishlistContent.shouldBe(visible).assertSizeEqualTo(expected);
        return this;
    }

    public MyWishlistsPage assertWishlistTitle(int index, String value) {
        getWishlistItem(index).assertTitleEqualsTo(value);
        return this;
    }

    public MyWishlistsPage assertWishlistSubtitle(int index, String value) {
        getWishlistItem(index).assertSubtitleEqualsTo(value);
        return this;
    }

    public MyWishlistsPage tapEditWishlist(int index) {
        getWishlistItem(index).tapEdit();
        return this;
    }

    private WishlistItem getWishlistItem(int index) {
        return wishlistContent.get(index).shouldBe(visible);
    }

    public MyWishlistsPage assertEditWishlistTitle() {
        editWishlistComponent.assertComponentTitle("Изменить список желаний");
        return this;
    }

    public MyWishlistsPage editWishlistDescription(String description) {
        editWishlistComponent.editDescription(description);
        return this;
    }

    public MyWishlistsPage clickWishlist(int index) {
        getWishlistItem(index).shouldBe(visible).click();
        return this;
    }

    public MyWishlistsPage open() {
        SelenideElement mineMenu = $(id("ru.otus.wishlist:id/mine_menu"));
        if (mineMenu.isDisplayed() && !"true".equals(mineMenu.getAttribute("selected"))) {
            mineMenu.click();
        }
        wishlistContent.shouldBe(visible);
        return this;
    }
}