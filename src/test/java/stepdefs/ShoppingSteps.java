package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.CheckoutPage;
import pages.HomePage;
import pages.CartPage;
import utils.ExcelReader;

import java.util.Map;

public class ShoppingSteps {
	 WebDriver driver;
	    HomePage homePage;
	    CartPage cartPage;
	    CheckoutPage checkoutPage;
	    Map<String, String> checkoutData;

	    // Directly using TestBase to get WebDriver and initialize page objects
	    public ShoppingSteps() {
	        driver = new utils.TestBase().getDriver();  // Directly get driver from TestBase
	        homePage = new HomePage(driver);
	        cartPage = new CartPage(driver);
	        checkoutPage = new CheckoutPage(driver);
	        checkoutData = ExcelReader.getCheckoutData("CheckoutDetails");  // Fetch data from Excel
	    }
	    
}
		/*
		 * @Given("I am on the homepage") public void iAmOnTheHomePage() {
		 * homePage.open(); }
		 * 
		 * @When("I add {string} of size {string} and quantity {string} to the cart")
		 * public void iAddItemToCart(String item, String size, String quantity) {
		 * homePage.addItemToCart(item, size, quantity); }
		 * 
		 * @Then("I should see {string} added to the cart with size {string} and quantity {string}"
		 * ) public void iShouldSeeItemInCartWithDetails(String item, String size,
		 * String quantity) { cartPage.verifyItemInCart(item, size, quantity); }
		 * 
		 * @When("I view the cart") public void iViewTheCart() { cartPage.open(); }
		 * 
		 * @Then("I should see {string} in the cart with the correct details") public
		 * void iShouldSeeItemInCartWithCorrectDetails(String item) {
		 * cartPage.verifyItemDetails(item); }
		 * 
		 * @When("I click on Shop to return to the homepage") public void
		 * iClickOnShopToReturnToTheHomepage() { cartPage.navigateBackToShop(); }
		 * 
		 * @Given("I view the basket") public void iViewTheBasket() { cartPage.open(); }
		 * 
		 * @Then("I confirm the items in the cart have the correct sizes and quantities"
		 * ) public void iConfirmItemsInCartHaveCorrectDetails() {
		 * cartPage.verifyAllItemDetails(); }
		 * 
		 * @Then("the total price is calculated correctly") public void
		 * theTotalPriceIsCalculatedCorrectly() { cartPage.verifyTotalPrice(); }
		 * 
		 * @When("I change the quantity of {string} to {string}") public void
		 * iChangeQuantityOfItem(String item, String quantity) {
		 * cartPage.updateQuantity(item, quantity); }
		 * 
		 * @Then("the cart should update the total price correctly") public void
		 * theCartUpdatesTotalPriceCorrectly() { cartPage.verifyUpdatedTotalPrice(); }
		 * 
		 * @Given("I proceed to the checkout page") public void iProceedToCheckoutPage()
		 * { cartPage.proceedToCheckout(); }
		 * 
		 * @When("I enter account information from the Excel sheet") public void
		 * iEnterAccountInformationFromExcel() {
		 * checkoutPage.enterEmail(checkoutData.get("Email"));
		 * checkoutPage.enterPhone(checkoutData.get("Phone")); }
		 * 
		 * @When("I enter shipping address details from the Excel sheet") public void
		 * iEnterShippingAddressFromExcel() {
		 * checkoutPage.enterAddress(checkoutData.get("Address"));
		 * checkoutPage.enterCity(checkoutData.get("City"));
		 * checkoutPage.enterState(checkoutData.get("State"));
		 * checkoutPage.enterZip(checkoutData.get("Zip"));
		 * checkoutPage.selectCountry(checkoutData.get("Country")); }
		 * 
		 * @When("I enter payment method details from the Excel sheet") public void
		 * iEnterPaymentDetailsFromExcel() {
		 * checkoutPage.enterCardholderName(checkoutData.get("Cardholder Name"));
		 * checkoutPage.enterCardNumber(checkoutData.get("Card Number"));
		 * checkoutPage.enterExpiryDate(checkoutData.get("Expiry"));
		 * checkoutPage.enterCVV(checkoutData.get("CVV")); }
		 * 
		 * @When("I place the order") public void iPlaceTheOrder() {
		 * checkoutPage.placeOrder(); }
		 * 
		 * @Then("I should see a confirmation message saying {string}") public void
		 * iShouldSeeConfirmationMessage(String message) {
		 * checkoutPage.verifyOrderConfirmation(message); }
		 * 
		 * @Then("I click on Finish to complete the process") public void
		 * iClickOnFinish() { checkoutPage.clickFinish(); }
		 */

