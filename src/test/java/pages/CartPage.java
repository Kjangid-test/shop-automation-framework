package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import drivers.DriverFactory;
import utils.SeleniumUtils;

public class CartPage {
    WebDriver driver;

    // Constructor
    public CartPage() {

		PageFactory.initElements(DriverFactory.getDriver(), this); // Initialize elements only once
	}
	private static final String VIEW_CART = "#header > app-toolbar > div.cart-btn-container > a > paper-icon-button";
    String[] cartProductMenNameSelector = { "iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)","div > div.name > a"};
    String[] cartProductWomenNameSelector = { "iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)","div > div.name > a"};
    String[] cartProductMenQuantitySelector = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)","#quantitySelect"};
    String[] cartProductWomenQuantitySelector = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)","#quantitySelect"};
    String[] cartProductMenSizeSelector = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)","div > div.detail > div.size > span"};
    String[] cartProductWomenSizeSelector = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)","div > div.detail > div.size > span"};
    String[] cartProductMenWearPrice = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(1)","div > div.detail > div.price"};
    String[] cartProductWomenWearPrice = {"iron-pages > shop-cart","div > div:nth-child(2) > div.list > shop-cart-item:nth-child(2)","div > div.detail > div.price"};
    String[] cartProductSubTotal = {"iron-pages > shop-cart","div > div:nth-child(2) > div.checkout-box > span"};
    
    @FindBy(css = "body > shop-app") // Assuming both the elements are under <shop-tab> this is main shawdohost
	private WebElement shadowHostForMensAndWomensWear;


    // Method to view the cart (click on the cart button)
    public void clickOnViewCartbutton() {
    
		SeleniumUtils.clickElementInShadowDOM(shadowHostForMensAndWomensWear, VIEW_CART);
    }
   public String getMenItemNameFromCart() {
	   try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        WebElement productNameElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductMenNameSelector);
        
        System.out.println(productNameElement);        
        return productNameElement.getText().trim();
    }
   public String getWomenItemNameFromCart() {
       WebElement productNameElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenNameSelector);
       return productNameElement.getText().trim();
   }

   // Method to get the product size from the cart
   public String getMenItemSizeFromCart() {
       WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductMenSizeSelector);
       return productSizeElement.getText().trim();
   }

    // Method to get the product size from the cart
    public String getWomenItemSizeFromCart() {
        WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenSizeSelector);
        return productSizeElement.getText().trim();
    }

    // Method to get the product quantity from the cart
    public String getMenItemQuantityFromCart() {
        WebElement productQuantityElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductMenQuantitySelector);
        WebElement selectedOption = productQuantityElement.findElement(By.cssSelector("option:checked"));
        return selectedOption.getText().trim();
    }
    // Method to get the product quantity from the cart
    public String getWomenItemQuantityFromCart() {
        WebElement productQuantityElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenQuantitySelector);
        WebElement selectedOption = productQuantityElement.findElement(By.cssSelector("option:checked"));

        return selectedOption.getText().trim();
    }
    
    
    
    public void setWomenItemQuantityTo(int quantity) {
    	WebElement productQuantityElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenQuantitySelector);
        
        // Find the dropdown options (list of quantity options)
        List<WebElement> quantityOptions = productQuantityElement.findElements(By.cssSelector("option"));
        
        // Log the quantity options available for debugging
        System.out.println("Available quantity options: ");
        for (WebElement option : quantityOptions) {
            System.out.println(option.getText().trim());
        }
        
        // Loop through the options and select the one that matches the given quantity
        for (WebElement option : quantityOptions) {
            String optionText = option.getText().trim();
            if (optionText.equals(String.valueOf(quantity))) {
                option.click();  // Select the option that matches the quantity
                System.out.println("Selected quantity: " + quantity);
                break;  // Exit the loop once the correct option is selected
            }
        }
    }
    public String getMenItemPriceFromCart() {
        WebElement productQuantityElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductMenWearPrice);


        return productQuantityElement.getText().trim();
    }
    public String getWomenItemPriceFromCart() {
        WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductWomenWearPrice);
        return productSizeElement.getText().trim();
    }
    public String CalculateThePrice() {
        String sumPrice = "";

        try {
            // Get the prices from the cart and remove the dollar sign
            String menPriceString = getMenItemPriceFromCart().replace("$", "").trim();
            String womenPriceString = getWomenItemPriceFromCart().replace("$", "").trim();

            // Get the quantities from the cart
            String menQuantityString = getMenItemQuantityFromCart().trim();
            String womenQuantityString = getWomenItemQuantityFromCart().trim();

            // Log the values for debugging
            System.out.println("Men's Price (before parsing): " + menPriceString);
            System.out.println("Women's Price (before parsing): " + womenPriceString);
            System.out.println("Men's Quantity: " + menQuantityString);
            System.out.println("Women's Quantity: " + womenQuantityString);

            // Check if both price strings and quantities are valid
            if (menPriceString.isEmpty() || womenPriceString.isEmpty() || menQuantityString.isEmpty() || womenQuantityString.isEmpty()) {
                throw new NumberFormatException("Price or quantity is empty or invalid");
            }

            // Convert the prices and quantities to double (in case there are decimal values)
            double menPrice = Double.parseDouble(menPriceString);
            double womenPrice = Double.parseDouble(womenPriceString);
            int menQuantity = Integer.parseInt(menQuantityString);
            int womenQuantity = Integer.parseInt(womenQuantityString);

            // Calculate the total price considering the quantities
            double totalPrice = (menPrice * menQuantity) + (womenPrice * womenQuantity);

            // Convert the result to string and format it to two decimal places
            sumPrice = String.format("%.2f", totalPrice);
        } catch (NumberFormatException ex) {
            // Handle the case where the price or quantity values are not numbers
            sumPrice = "Invalid input: " + ex.getMessage();
        }

        System.out.println("Total price of both the products is " + sumPrice);
        return sumPrice;
    }

    public String getTheSubtotalOfTheCartFromUI() {
    	WebElement productSizeElement = SeleniumUtils.getElementInNestedShadowDOM(shadowHostForMensAndWomensWear, cartProductSubTotal);
        return productSizeElement.getText().replace("$", "").trim();
    }

        
    

}
