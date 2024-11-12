package stepdefs;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import drivers.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrderConfirmationPage;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.SeleniumUtils;

public class ShoppingSteps {

	private HomePage homePage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private OrderConfirmationPage orderConfirmationPage;
	private Map<String, Map<String, String>> cartItems = new HashMap<>(); // Store item details for verification
	private final String COUNTRY = "Country";
	private final String EXPIRY = "Expiry";

	// This method will be run before the test scenarios to initialize the driver
	// and navigate to the homepage
	@Given("I launch the browser and open the homepage")
	public void i_launch_the_browser_and_open_the_homepage() {
		homePage = homePage == null ? new HomePage() : homePage;
		cartPage = cartPage == null ? new CartPage() : cartPage;
		checkoutPage = checkoutPage == null ? new CheckoutPage() : checkoutPage;
		orderConfirmationPage = orderConfirmationPage == null ? new OrderConfirmationPage() : orderConfirmationPage;

		// SeleniumUtils.getTheUrl(utils.ConfigReader.getProperty("BASEURL"));
	}

	// Use this method to interact with the "Mens Outerwear" item (defined in the
	// page object)
	@When("I click on {string}")
	public void i_click_on_item(String item) {

		if (item.equalsIgnoreCase("Men’s Outerwear")) {
			homePage.clickMensOuterwear(); // Call the method for Men's Outerwear
		} else if (item.equalsIgnoreCase("Ladies Outerwear")) {
			homePage.clickWomensOuterwear(); // Call the method for Women's Outerwear
		} else {
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	@And("I add {string} of size {string} and quantity {string} to the cart")
	public void i_add_item_to_cart(String item, String size, String quantity) {
		Map<String, String> itemDetails = new HashMap<>();
		itemDetails.put("size", size);
		itemDetails.put("quantity", quantity);
		cartItems.put(item, itemDetails);
		if (item.equalsIgnoreCase("Men’s Outerwear")) {
			homePage.clickProductofMensOuterwearToCart();
			homePage.addMenSizetemToCart(size); // Ensure that the correct size and quantity are passed here
			homePage.addMenQuantityItemToCart(quantity); // Ensure that the correct size and quantity are passed here
			homePage.clickOnAddToCartButton();
		} else if (item.equalsIgnoreCase("Ladies Outerwear")) {
			homePage.clickProductofWomenssOuterwearToCart();
			homePage.addWomenSizetemToCart(size);
			homePage.addWomenQuantityItemToCart(quantity);
			homePage.clickOnAddToCartButton();
		} else {
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	@When("I view the cart")
	public void i_view_the_cart() {
		cartPage.clickOnViewCartbutton();
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

		// Now you can print the values or use them for assertions
		System.out.println("Men's Item Name: " + actualmenItemName);
		System.out.println("Women's Item Name: " + actualmenItemName);
		System.out.println("Men's Item Size: " + actualmenItemSize);
		System.out.println("Women's Item Size: " + actualwomenItemSize);
		System.out.println("Men's Item Quantity: " + actualmenItemQuantity);
		System.out.println("Women's Item Quantity: " + actualwomenItemQuantity);

		// Loop through each item in the expectedData and compare with actual values
		for (Map<String, String> expectedItem : expectedItems) {
			String expectedItemName = expectedItem.get("item");
			String expectedItemSize = expectedItem.get("size");
			String expectedItemQuantity = expectedItem.get("quantity");
			System.out.println(expectedItemName + ":" + expectedItemSize + ":" + expectedItemQuantity);

			// Check if it's the men's item
			if (expectedItemName.equalsIgnoreCase("Men’s Outerwear")) {
				// Assert the item name, size, and quantity for Men's Outerwear
				Assert.assertTrue(actualmenItemName.toLowerCase().contains("men"),
						"Men's item name doesn't match the expected name!");
				Assert.assertEquals(actualmenItemSize, expectedItemSize, "Men's item size doesn't match!");
				Assert.assertEquals(actualmenItemQuantity, expectedItemQuantity, "Men's item quantity doesn't match!");
				// Optionally, you can also check the price
				Assert.assertNotNull(actualmenItemPrice, "Men's item price should not be null!");
			}
			// Check if it's the women's item
			else if (expectedItemName.equalsIgnoreCase("Ladies Outerwear")) {
				// Assert the item name, size, and quantity for Women's Outerwear
				Assert.assertTrue(actualwomenItemName.toLowerCase().contains("ladies"),
						"Men's item name doesn't match the expected name!");
				Assert.assertEquals(actualwomenItemSize, expectedItemSize, "Women's item size doesn't match!");
				Assert.assertEquals(actualwomenItemQuantity, expectedItemQuantity,
						"Women's item quantity doesn't match!");
				// Optionally, you can also check the price
				Assert.assertNotNull(actualwomenItemPrice, "Women's item price should not be null!");
			}
		}

	}

	@And("the total price is calculated correctly")
	public void the_total_price_is_calculated_correctly() {

		String expectedtotalPrice = cartPage.CalculateThePrice();
		String actualcartSubtotal = cartPage.getTheSubtotalOfTheCartFromUI();
		System.out.println("Total Price: " + expectedtotalPrice);
		System.out.println("Cart Subtotal: " + actualcartSubtotal);
		Assert.assertEquals(expectedtotalPrice, actualcartSubtotal,
				"After calulating the prices for products, they dont match");

	}

	@When("I change the quantity of {string} to {string}")
	public void i_change_the_quantity_of_to(String item, String quantity) {
		// cartPage.clickOnViewCartbutton();

		// Assuming you are only dealing with "Ladies Outerwear" for now
		if (item.equalsIgnoreCase("Ladies Outerwear")) {
			// Convert quantity to integer
			int quantityInt = Integer.parseInt(quantity);

			// Set the quantity using the CartPage method
			cartPage.setWomenItemQuantityTo(quantityInt);

			System.out.println("Quantity of " + item + " changed to " + quantity);
			// Fetch the updated quantity from the cart
			String updatedQuantity = cartPage.getWomenItemQuantityFromCart();

			// Assert that the updated quantity matches the expected quantity
			Assert.assertEquals(String.valueOf(quantityInt), updatedQuantity,
					"The quantity of " + item + " was not updated correctly!");
			;
		} else {
			throw new IllegalArgumentException("Unknown item: " + item);
		}
	}

	@Then("the cart should update the total price correctly")
	public void the_cart_should_update_the_total_price_correctly() {
		// Fetch the updated item prices and quantities

		String expectedtotalPrice = cartPage.CalculateThePrice();
		String actualcartSubtotal = cartPage.getTheSubtotalOfTheCartFromUI();
		System.out.println("Total Price: " + expectedtotalPrice);
		System.out.println("Cart Subtotal: " + actualcartSubtotal);
		Assert.assertEquals(expectedtotalPrice, actualcartSubtotal,
				"After calulating the prices for products, they dont match");

	}

	@When("I proceed to the checkout page")
	public void I_proceed_to_the_checkout_page() {
		cartPage.clickOnViewCartbutton();

		checkoutPage.getCheckOutbutton();

	}

	@And("I complete the checkout with the following account information from excel:")
	public void I_complete_the_checkout_with_the_following_account_information_from_excel(DataTable accountInfo) {
		// Get the Excel file path from a configuration or system property

		String filePath = ConfigReader.getCheckoutDataFilePath(); // This method will return the file path dynamically

		// Read all data from the Excel file
		Map<String, String> checkoutData = ExcelReader.readCheckoutData(filePath);
		System.out.println("Checkout Data: " + checkoutData); // Print loaded checkout data for debugging

		// Convert DataTable to a Map<String, String> and process each entry

		Map<String, String> accountInfoMap = accountInfo.asMap(String.class, String.class);
		System.out.println("Account Info Map from DataTable: " + accountInfoMap);

		for (Map.Entry<String, String> entry : accountInfoMap.entrySet()) {
			String field = entry.getKey();
			// Confirm the key exists in checkoutData to avoid NullPointerException
			if (checkoutData.containsKey(field)) {
				String value = checkoutData.get(field);
				System.out.println("Filling field: " + field + " with value from Excel: " + value);
				checkoutPage.completeCheckoutField(field, value);
			} else {
				System.out.println("Warning: No matching key '" + field + "' found in checkoutData.");
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
			// Confirm the key exists in checkoutData to avoid NullPointerException
			if (checkoutData.containsKey(field)) {
				if (field.equalsIgnoreCase(COUNTRY)) {
					checkoutPage.setFieldFromSelectField(field, checkoutData.get(field));

				} else {

					String value = checkoutData.get(field);
					System.out.println("Filling field: " + field + " with value from Excel: " + value);
					checkoutPage.completeCheckoutField(field, value);
				}
			} else {
				System.out.println("Warning: No matching key '" + field + "' found in checkoutData.");
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
			// Confirm the key exists in checkoutData to avoid NullPointerException
			if (checkoutData.containsKey(field)) {

				if (field.equalsIgnoreCase(EXPIRY)) {

					String[] expiryData = checkoutData.get(field).split(" ");

					checkoutPage.setFieldFromSelectField("ExpiryMonth", expiryData[0]);

					checkoutPage.setFieldFromSelectField("ExpiryYear", expiryData[1]);
				}

				else {
					String value = checkoutData.get(field);
					System.out.println("Filling field: " + field + " with value from Excel: " + value);
					checkoutPage.completeCheckoutField(field, value);
				}
			} else {
				System.out.println("Warning: No matching key '" + field + "' found in checkoutData.");
			}
		}
	}

	@When("I place the order")
	public void i_place_the_order() {
		// Proceed to the checkout page if not already there
		orderConfirmationPage.getorderConfirmationPlaceOrderbutton(); // Ensure this method clicks the correct button on
																		// the checkout page

	}

	// Step 2: Verify the confirmation message
	@Then("I should see a confirmation message saying {string}")
	public void i_should_see_confirmation_message(String expectedMessage) {
		// Fetch the actual confirmation message from the UI
		String actualMessage = orderConfirmationPage.getOrderConfirmationThankYou(); // Ensure this method returns the
																						// confirmation message
		String actualMessageDesc = orderConfirmationPage.getOrderConfirmationThankYouDescription();
		System.out.println(actualMessage + "description " + actualMessageDesc);
		// Assert that the confirmation message is as expected
		Assert.assertEquals(actualMessage, expectedMessage, "Thank you");
	}

	// Step 3: Click on the "Finish" button to complete the process
	@And("I click on Finish to complete the process")
	public void i_click_on_finish_to_complete_the_process() {
		// Click the Finish button to finalize the order
		orderConfirmationPage.getOrderConfirmationFinishbutton(); // Ensure this method clicks the "Finish" button to
																	// complete the process
	}

};