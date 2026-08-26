package ru.otus.pages;

import com.google.inject.Singleton;
import ru.otus.components.EditWishlistComponent;
import ru.otus.components.WishlistContent;
import ru.otus.components.WishlistItem;

import static com.codeborne.selenide.Condition.visible;

@Singleton
public class MyWishlistsPage extends AbsBasePage {

    private final WishlistContent wishlistContent =
            new WishlistContent(
                    elementById(
                            "ru.otus.wishlist:id/wishlists_content",
                            "Содержимое списков желаний"
                    )
            );

    private final EditWishlistComponent editWishlistComponent =
            new EditWishlistComponent(
                    elementById(
                            "ru.otus.wishlist:id/wishlist_edit_bottom_sheet",
                            "Окно редактирования списка желаний"
                    )
            );

    public MyWishlistsPage open() {
        bottomNavigation.goToMine();
        return this;
    }

    public MyWishlistsPage assertNumberOfWishlists(int expected) {
        wishlistContent.shouldBe(visible).assertSizeEqualTo(expected);
        return this;
    }

    public MyWishlistsPage assertWishlistTitle(int index, String expected) {
        getWishlistItem(index).assertTitleEqualsTo(expected);
        return this;
    }

    public MyWishlistsPage assertWishlistSubtitle(int index, String expected) {
        getWishlistItem(index).assertSubtitleEqualsTo(expected);
        return this;
    }

    public MyWishlistsPage tapEditWishlist(int index) {
        getWishlistItem(index).tapEdit();
        return this;
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
        getWishlistItem(index).click();
        return this;
    }

    private WishlistItem getWishlistItem(int index) {
        return wishlistContent.get(index).shouldBe(visible);
    }
}