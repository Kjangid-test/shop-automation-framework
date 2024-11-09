#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

 Feature: Shopping and Checkout Process

  Scenario: Add two items to the cart and complete checkout

    # Step 1: Add Men's Outerwear to cart
    Given I am on the homepage
    When I add "Men’s Outerwear" of size "XL" and quantity "2" to the cart
    Then I should see "Men’s Outerwear" added to the cart with size "XL" and quantity "2"

    # Step 2: Confirm item in cart
    When I view the cart
    Then I should see "Men’s Outerwear" in the cart with the correct details

    # Step 3: Navigate back to shop
    When I click on Shop to return to the homepage

    # Step 4: Add Ladies' Outerwear to cart
    And I add "Ladies' Outerwear" of size "XS" and quantity "3" to the cart
    Then I should see "Ladies' Outerwear" added to the cart with size "XS" and quantity "3"

    # Step 5: Confirm item in cart
    When I view the cart
    Then I should see "Ladies' Outerwear" in the cart with the correct details

    # Step 6: Verify items in cart before checkout
    Given I view the basket
    Then I confirm the items in the cart have the correct sizes and quantities
    And the total price is calculated correctly

    # Step 7: Update quantity for Ladies' Outerwear
    When I change the quantity of "Ladies' Outerwear" to "1"
    Then the cart should update the total price correctly

    # Step 8: Proceed to checkout
    Given I proceed to the checkout page

    # Step 9: Enter account, shipping, and payment details from Excel
    When I enter account information from the Excel sheet
    And I enter shipping address details from the Excel sheet
    And I enter payment method details from the Excel sheet

    # Step 10: Place the order
    And I place the order

    # Step 11: Confirm order and finish
    Then I should see a confirmation message saying "Thank you for your order"
    And I click on Finish to complete the process
