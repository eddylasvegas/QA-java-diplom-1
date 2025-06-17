package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static praktikum.TestConstants.*;

@RunWith(Parameterized.class)
public class BurgerParamTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
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
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(bun.getPrice()).thenReturn(BUN_PRICE);

        when(ingredient.getType()).thenReturn(type);
        when(ingredient.getName()).thenReturn(name);
        when(ingredient.getPrice()).thenReturn(price);

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