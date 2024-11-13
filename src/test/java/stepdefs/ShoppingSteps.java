package stepdefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import drivers.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrderConfirmationPage;
import utils.ConfigReader;
import utils.ExcelReader;

public class ShoppingSteps {

	private HomePage homePage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private OrderConfirmationPage orderConfirmationPage;
	private Map<String, Map<String, String>> cartItems = new HashMap<>(); // Store item details for verification
	private final String COUNTRY = "Country";
	private final String EXPIRY = "Expiry";
	private Scenario scenario;

	@BeforeStep
	public void setUp(Scenario scenario) {
		this.scenario = scenario;
	}

	
	@Given("I launch the browser and open the homepage {string}")
	public void i_launch_the_browser_and_open_the_homepage_title(String title) {
		homePage = homePage == null ? new HomePage() : homePage;
		cartPage = cartPage == null ? new CartPage() : cartPage;
		checkoutPage = checkoutPage == null ? new CheckoutPage() : checkoutPage;
		orderConfirmationPage = orderConfirmationPage == null ? new OrderConfirmationPage() : orderConfirmationPage;


			String actualTitle = DriverFactory.getDriver().getTitle();
			scenario.log("Actual Title: " + actualTitle);


	}
	// SeleniumUtils.getTheUrl(utils.ConfigReader.getProperty("BASEURL"));

	@When("I click on {string}")
	public void i_click_on_item(String item) {
		scenario.log("Attempting to click on item: " + item);

		if (item.equalsIgnoreCase("Men’s Outerwear")) {
			homePage.clickMensOuterwear(); // Call the method for Men's Outerwear
			scenario.log("Clicked on Men's Outerwear");
		} else if (item.equalsIgnoreCase("Ladies Outerwear")) {
			homePage.clickWomensOuterwear(); // Call the method for Women's Outerwear
			scenario.log("Clicked on Women's Outerwear");
		} else {
			scenario.log("Error: Unknown item: " + item);
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	@And("I add {string} of size {string} and quantity {string} to the cart")
	public void i_add_item_to_cart(String item, String size, String quantity) {
		scenario.log("Adding item to cart: " + item + ", Size: " + size + ", Quantity: " + quantity);

		Map<String, String> itemDetails = new HashMap<>();
		itemDetails.put("size", size);
		itemDetails.put("quantity", quantity);
		cartItems.put(item, itemDetails);

		if (item.equalsIgnoreCase("Men’s Outerwear")) {
			homePage.clickProductofMensOuterwearToCart();
			homePage.addMenSizetemToCart(size); // Ensure that the correct size and quantity are passed here
			homePage.addMenQuantityItemToCart(quantity); // Ensure that the correct size and quantity are passed here
			homePage.clickOnAddToCartButton();
			scenario.log("Added Men's Outerwear to the cart with size: " + size + " and quantity: " + quantity);
		} else if (item.equalsIgnoreCase("Ladies Outerwear")) {
			homePage.clickProductofWomenssOuterwearToCart();
			homePage.addWomenSizetemToCart(size);
			homePage.addWomenQuantityItemToCart(quantity);
			homePage.clickOnAddToCartButton();
			scenario.log("Added Women's Outerwear to the cart with size: " + size + " and quantity: " + quantity);
		} else {
			// Log error if an unknown item is provided
			scenario.log("Error: Unknown item: " + item);
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}
	
	 @And ("I view cart to check item is added successfully")
	public void  I_view_cart_to_check_item_is_added_successfully () {
			scenario.log("Viewing cart to check the items");
			cartPage.clickOnViewCartButton();

		 
	 }
	
	 @And ("I click on Shop to navigate to home page")
	 public void  I_click_on_Shop_to_navigate_to_home_page() {
			scenario.log("Clicking SHOP to navigate to the Home page");

			homePage.navigateToHomePageOnShopClick();
		 
	 }

	@When("I view the cart")
	public void i_view_the_cart() {
		scenario.log("Clicking on Basket cart button");

		cartPage.clickOnBasketButton();
	}

	@Then("I should see all items added to the cart with correct details and correct price")
	public void I_should_see_all_items_added_to_the_cart_with_correct_details_and_correct_price_(
			io.cucumber.datatable.DataTable expectedData) {
		List<Map<String, String>> expectedItems = expectedData.asMaps(String.class, String.class);

		// Declare variables to store the values
		String actualmenItemName = cartPage.getMenItemNameFromCart();
		String actualwomenItemName = cartPage.getWomenItemNameFromCart();
		String actualmenItemSize = cartPage.getMenItemSizeFromCart();
		String actualwomenItemSize = cartPage.getWomenItemSizeFromCart();
		String actualmenItemQuantity = cartPage.getMenItemQuantityFromCart();
		String actualwomenItemQuantity = cartPage.getWomenItemQuantityFromCart();
		String actualmenItemPrice = cartPage.getMenItemPriceFromCart();
		String actualwomenItemPrice = cartPage.getWomenItemPriceFromCart();

		// Log the expected vs actual values for the Cucumber report
		for (Map<String, String> expectedItem : expectedItems) {
			String expectedItemName = expectedItem.get("item");
			String expectedItemSize = expectedItem.get("size");
			String expectedItemQuantity = expectedItem.get("quantity");

			// Log expected values
			scenario.log("Expected values: Item: " + expectedItemName + ", Size: " + expectedItemSize + ", Quantity: "
					+ expectedItemQuantity);

			if (expectedItemName.equalsIgnoreCase("Men’s Outerwear")) {
				// Log actual values
				scenario.log("Actual values: Item: " + actualmenItemName + ", Size: " + actualmenItemSize
						+ ", Quantity: " + actualmenItemQuantity);

				Assert.assertTrue(actualmenItemName.toLowerCase().contains("men"),
						"Men's item name doesn't match the expected name!");
				Assert.assertEquals(actualmenItemSize, expectedItemSize, "Men's item size doesn't match!");
				Assert.assertEquals(actualmenItemQuantity, expectedItemQuantity, "Men's item quantity doesn't match!");
				Assert.assertNotNull(actualmenItemPrice, "Men's item price should not be null!");
			} else if (expectedItemName.equalsIgnoreCase("Ladies Outerwear")) {
				// Log actual values
				scenario.log("Actual values: Item: " + actualwomenItemName + ", Size: " + actualwomenItemSize
						+ ", Quantity: " + actualwomenItemQuantity);

				Assert.assertTrue(actualwomenItemName.toLowerCase().contains("ladies"),
						"Women's item name doesn't match the expected name!");
				Assert.assertEquals(actualwomenItemSize, expectedItemSize, "Women's item size doesn't match!");
				Assert.assertEquals(actualwomenItemQuantity, expectedItemQuantity,
						"Women's item quantity doesn't match!");
				Assert.assertNotNull(actualwomenItemPrice, "Women's item price should not be null!");
			}
		}
	}

	@And("the total price is calculated correctly")
	public void the_total_price_is_calculated_correctly() {
		String expectedTotalPrice = cartPage.calculateTotalPrice();
		String actualCartSubtotal = cartPage.getSubtotalFromUI();

		scenario.log("Expected Total Price: " + expectedTotalPrice);
		scenario.log("Actual Cart Subtotal: " + actualCartSubtotal);

		System.out.println("Total Price: " + expectedTotalPrice);
		System.out.println("Cart Subtotal: " + actualCartSubtotal);

		Assert.assertEquals(expectedTotalPrice, actualCartSubtotal,
				"After calculating the prices for products, they don't match");
	}

	@When("I change the quantity of {string} to {string}")
	public void i_change_the_quantity_of_to(String item, String quantity) {
		if (item.equalsIgnoreCase("Ladies Outerwear")) {
			int quantityInt = Integer.parseInt(quantity);

			// Set the quantity using the CartPage method
			cartPage.setWomenItemQuantityTo(quantityInt);

			// Log the quantity change
			scenario.log("Changed quantity of " + item + " to " + quantityInt);

			// Fetch the updated quantity from the cart
			String updatedQuantity = cartPage.getWomenItemQuantityFromCart();

			// Log expected vs actual updated quantity
			scenario.log("Updated Quantity: Expected: " + quantityInt + ", Actual: " + updatedQuantity);

			// Assert that the updated quantity matches the expected quantity
			Assert.assertEquals(String.valueOf(quantityInt), updatedQuantity,
					"The quantity of " + item + " was not updated correctly!");
		} else {
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	@Then("the cart should update the total price correctly")
	public void the_cart_should_update_the_total_price_correctly() {
		String expectedTotalPrice = cartPage.calculateTotalPrice();
		String actualCartSubtotal = cartPage.getSubtotalFromUI();

		scenario.log("Expected Total Price after update: " + expectedTotalPrice);
		scenario.log("Actual Cart Subtotal after update: " + actualCartSubtotal);

		System.out.println("Total Price: " + expectedTotalPrice);
		System.out.println("Cart Subtotal: " + actualCartSubtotal);

		Assert.assertEquals(expectedTotalPrice, actualCartSubtotal,
				"After calculating the prices for products, they don't match");
	}

	@When("I proceed to the checkout page")
	public void I_proceed_to_the_checkout_page() {
		checkoutPage.getCheckOutbutton();

	}

	@And("I complete the checkout with the following account information from excel:")
	public void I_complete_the_checkout_with_the_following_account_information_from_excel(DataTable accountInfo) {
		String filePath = ConfigReader.getCheckoutDataFilePath();

		Map<String, String> checkoutData = ExcelReader.readCheckoutData(filePath);

		// Convert DataTable to a Map<String, String> and process each entry
		Map<String, String> accountInfoMap = accountInfo.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : accountInfoMap.entrySet()) {
			String field = entry.getKey();

			if (checkoutData.containsKey(field)) {
				String value = checkoutData.get(field);
				scenario.log("Filling field: " + field + " with value from Excel: " + value);
				checkoutPage.completeCheckoutField(field, value);
			} else {
				scenario.log("Warning: No matching key '" + field + "' found in checkoutData.");
			}
		}
	}

	@And("I complete the checkout with the following shipping address from excel:")
	public void I_complete_the_checkout_with_the_following_shipping_address_from_excel(DataTable shippingAddress) {
		String filePath = ConfigReader.getCheckoutDataFilePath();
		Map<String, String> checkoutData = ExcelReader.readCheckoutData(filePath);

		// Convert DataTable to a Map<String, String> and process each entry
		Map<String, String> shippingAddressMap = shippingAddress.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : shippingAddressMap.entrySet()) {
			String field = entry.getKey();

			if (checkoutData.containsKey(field)) {
				String value = checkoutData.get(field);

				// Log expected field and value from Excel
				scenario.log("Filling shipping address field: " + field + " with value from Excel: " + value);

				if (field.equalsIgnoreCase(COUNTRY)) {
					checkoutPage.setFieldFromSelectField(field, value);
				} else {
					checkoutPage.completeCheckoutField(field, value);
				}
			} else {
				scenario.log("Warning: No matching key '" + field + "' found in checkoutData.");
			}
		}
	}

	@And("I complete the checkout with the following payment method from excel:")
	public void I_complete_the_checkout_with_the_following_payment_method_from_excel(DataTable paymentMethod) {
		String filePath = ConfigReader.getCheckoutDataFilePath();
		Map<String, String> checkoutData = ExcelReader.readCheckoutData(filePath);

		// Convert DataTable to a Map<String, String> and process each entry
		Map<String, String> paymentDataMap = paymentMethod.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : paymentDataMap.entrySet()) {
			String field = entry.getKey();

			if (checkoutData.containsKey(field)) {
				String value = checkoutData.get(field);

				// Log expected field and value from Excel
				scenario.log("Filling payment method field: " + field + " with value from Excel: " + value);

				if (field.equalsIgnoreCase(EXPIRY)) {
					String[] expiryData = value.split(" ");
					checkoutPage.setFieldFromSelectField("ExpiryMonth", expiryData[0]);
					checkoutPage.setFieldFromSelectField("ExpiryYear", expiryData[1]);
				} else {
					checkoutPage.completeCheckoutField(field, value);
				}
			} else {
				scenario.log("Warning: No matching key '" + field + "' found in checkoutData.");
			}
		}
	}

	@When("I place the order")
	public void i_place_the_order() {
		// Log the action of placing the order
		scenario.log("Placing the order...");
		orderConfirmationPage.getorderConfirmationPlaceOrderbutton();
	}

	@Then("I should see a confirmation message saying {string}")
	public void i_should_see_confirmation_message(String expectedMessage) {
		String actualMessage = orderConfirmationPage.getOrderConfirmationThankYou();
		String actualMessageDesc = orderConfirmationPage.getOrderConfirmationThankYouDescription();

		scenario.log("Expected confirmation message: " + expectedMessage);
		scenario.log("Actual confirmation message: " + actualMessage);

		scenario.log("Order confirmation description: " + actualMessageDesc);

		System.out.println(actualMessage + " description " + actualMessageDesc);

		Assert.assertEquals(actualMessage, expectedMessage, "Thank you");
	}

	@And("I click on Finish to complete the process")
	public void i_click_on_finish_to_complete_the_process() {
		scenario.log("Clicking on Finish to complete the order process...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		orderConfirmationPage.getOrderConfirmationFinishbutton();
	}

};