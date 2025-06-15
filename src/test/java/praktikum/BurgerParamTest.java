package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static praktikum.TestConstants.*;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private Burger burger;
    private Bun bun;
    private Ingredient ingredient;

    private final IngredientType type;
    private final String name;
    private final float price;
    private final String expectedTypeString;

    public BurgerParamTest(IngredientType type, String name, float price, String expectedTypeString) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.expectedTypeString = expectedTypeString;
    }

    @Parameterized.Parameters(name = "Тип: {0}, Ингредиент: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {IngredientType.SAUCE, HOT_SAUCE_NAME, SAUCE_PRICE, "sauce"},
                {IngredientType.FILLING, CUTLET_NAME, FILLING_PRICE, "filling"},
                {IngredientType.SAUCE, CHILI_SAUCE_NAME, CHILI_SAUCE_PRICE, "sauce"}
        });
    }

    @Before
    public void setUp() {
        burger = new Burger();
        bun = new Bun(BLACK_BUN_NAME, BUN_PRICE);
        ingredient = new Ingredient(type, name, price);
        burger.setBuns(bun);
    }

    //Проверка корректности чека
    @Test
    public void testGetReceiptWithDifferentIngredients() {
        burger.addIngredient(ingredient);

        String expected = String.format(RECEIPT_STRUCTURE,
                BLACK_BUN_NAME, expectedTypeString, name, BLACK_BUN_NAME, BUN_PRICE * 2 + price);
        assertEquals(String.format("Чек для %s должен содержать правильный тип (%s)",
                name, expectedTypeString), expected, burger.getReceipt());
    }

    //Проверка расчета цены
    @Test
    public void testGetPriceWithDifferentIngredients() {
        burger.addIngredient(ingredient);
        float expectedPrice = BUN_PRICE * 2 + price;
        assertEquals(String.format("Цена для %s должна быть %.2f",
                name, expectedPrice), expectedPrice, burger.getPrice(), 0.0);
    }
}