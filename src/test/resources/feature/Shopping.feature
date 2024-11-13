@HappyPath
Feature: Happy Path of Shopping and Checkout Process

  # Background steps are optional but useful if there are steps common to all scenarios.
  Background: 
    Given I launch the browser and open the homepage "Home - SHOP"

  # Scenario Outline for adding items to the cart
  Scenario Outline: Add items to the cart
    When I click on "<item>"
    And I add "<item>" of size "<size>" and quantity "<quantity>" to the cart
    And I view cart to check item is added successfully
    And I click on Shop to navigate to home page

    Examples: 
      | item             | size | quantity |
      | Men’s Outerwear  | XL   |        2 |
      | Ladies Outerwear | XS   |        3 |

  Scenario: View cart and verify items
    When I view the cart
    Then I should see all items added to the cart with correct details and correct price
      | item             | size | quantity |
      | Men’s Outerwear  | XL   |        2 |
      | Ladies Outerwear | XS   |        3 |
    And the total price is calculated correctly

  # Scenario to change quantity of an item in the cart
  Scenario: Update quantity for Ladies Outerwear
    When I change the quantity of "Ladies Outerwear" to "1"
    Then the cart should update the total price correctly
    
    
# Scenario for the checkout process using data from Excel
  Scenario: Complete checkout with data from Excel
  When I proceed to the checkout page
  And I complete the checkout with the following account information from excel:
    | Email  |
    | Phone  |
  And I complete the checkout with the following shipping address from excel:
    | Address |
    | City    |
    | State   |
    | Zip     |
    | Country |
  And I complete the checkout with the following payment method from excel:
    | Cardholder Name  |
    | Card Number      | 
    | Expiry           | 
    | CVV              |

 # Final confirmation of order placement
  Scenario: Place order and confirm
    When I place the order
    Then I should see a confirmation message saying "Thank you"
    And I click on Finish to complete the process
      
