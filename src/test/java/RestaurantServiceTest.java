import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.List;

class RestaurantServiceTest {

    List<Item> itemsInMenu = new ArrayList<Item>();

    RestaurantService service = new RestaurantService();
    Restaurant restaurant ;

    public void adding_test_details_of_restaurants(){
        restaurant = service.addRestaurant("Amelie's cafe","chennai",LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"));
        service.addRestaurant("paris cafe","chennai",LocalTime.parse("09:30:00"),LocalTime.parse("22:00:00"));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        adding_test_details_of_restaurants();
        assertEquals("Amelie's cafe",service.findRestaurantByName("Amelie's cafe").getName());

    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        adding_test_details_of_restaurants();
        assertThrows(restaurantNotFoundException.class,()->{
            service.findRestaurantByName("Datta");
        });

    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        adding_test_details_of_restaurants();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        adding_test_details_of_restaurants();

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        adding_test_details_of_restaurants();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ORDER VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void order_value_should_get_cumulative_total_when_collection_of_items_selected(){
        adding_test_details_of_restaurants();
        itemsInMenu = restaurant.getMenu();
        assertEquals(388,service.displayOrderTotal(itemsInMenu));
    }

    @Test
    public void order_value_should_reduce_cumulative_total_when_an_item_removed(){
        adding_test_details_of_restaurants();
        itemsInMenu = restaurant.getMenu();
        int total = service.displayOrderTotal(itemsInMenu);
        int afterTotal = itemsInMenu.get(1).getPrice();
        itemsInMenu.remove(1);
        assertEquals(total-afterTotal,service.displayOrderTotal(itemsInMenu));
    }

    @Test
    public void failing_case_order_value_should_get_cumulative_total_when_collection_of_items_selected(){
        adding_test_details_of_restaurants();
        itemsInMenu = restaurant.getMenu();
        assertEquals(504,service.displayOrderTotal(itemsInMenu));
    }

    //<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>
}