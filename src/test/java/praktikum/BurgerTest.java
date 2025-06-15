package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static praktikum.TestConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = 1;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient mockIngredient;

    @Mock
    private Ingredient extraMockIngredient;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    //Проверка добавления булочки в бургер
    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertEquals("Булочка должна быть установлена в бургер", bun, burger.bun);
    }

    // Проверяем увеличение количества ингредиентов
    @Test
    public void testAddIngredientIncreasesSize() {
        int initialSize = burger.ingredients.size();
        burger.addIngredient(mockIngredient);
        assertEquals("Количество ингредиентов должно увеличиться на 1",
                initialSize + 1, burger.ingredients.size());
    }

    // Проверяем, что добавленный ингредиент присутствует
    @Test
    public void testAddedIngredientIsPresent() {
        burger.addIngredient(mockIngredient);
        assertTrue("Добавленный ингредиент должен присутствовать в бургере",
                burger.ingredients.contains(mockIngredient));
    }

    //Добавление нескольких ингредиентов
    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        assertEquals("Должно быть 2 ингредиента",
                2, burger.ingredients.size());
    }

    //Проверяем удаление ингредиента
    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredient);
        burger.removeIngredient(FIRST_POSITION);
        assertTrue("Список ингредиентов должен быть пуст после удаления",
                burger.ingredients.isEmpty());
    }

    // Проверяем перемещение первого ингредиента
    @Test
    public void testMoveIngredientChangesFirstPosition() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        burger.moveIngredient(FIRST_POSITION, SECOND_POSITION);
        assertEquals("Ингредиент должен переместиться на вторую позицию",
                mockIngredient, burger.ingredients.get(SECOND_POSITION));
    }

    // Проверяем позицию второго ингредиента после перемещения
    @Test
    public void testMoveIngredientChangesSecondPosition() {
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);
        burger.moveIngredient(FIRST_POSITION, SECOND_POSITION);
        assertEquals("Второй ингредиент должен переместиться на первую позицию",
                extraMockIngredient, burger.ingredients.get(FIRST_POSITION));
    }

    //Проверка расчета общей стоимости бургера
    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(mockIngredient.getPrice()).thenReturn(SAUCE_PRICE);
        when(extraMockIngredient.getPrice()).thenReturn(FILLING_PRICE);

        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);
        burger.addIngredient(extraMockIngredient);

        float expectedPrice = BUN_PRICE * 2 + SAUCE_PRICE + FILLING_PRICE;
        assertEquals("Общая цена должна учитывать булочку (x2) и все ингредиенты",
                expectedPrice, burger.getPrice(), 0.0);
    }

    //Проверяем чек с 1 ингредиентом
    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(mockIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient.getName()).thenReturn(HOT_SAUCE_NAME);
        when(mockIngredient.getPrice()).thenReturn(SAUCE_PRICE);

        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);

        String expectedReceipt = String.format(RECEIPT_STRUCTURE,
                BLACK_BUN_NAME, "sauce", HOT_SAUCE_NAME, BLACK_BUN_NAME, BUN_PRICE * 2 + SAUCE_PRICE);
        assertEquals("Чек должен содержать корректную информацию о составе",
                expectedReceipt, burger.getReceipt());
    }
}