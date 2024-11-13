
Test Framework for Assessment
Project Overview
This project demonstrates my ability to build a fully functional test automation framework using Selenium, Cucumber, and Java, following best practices such as Page Object Model (POM) and PageFactory. The test framework automates the "happy path" scenario for purchasing items from an online store.
This framework automates the shopping and checkout process of an e-commerce website using Cucumber and Selenium WebDriver. It follows the Page Object Model (POM) design pattern, supports data-driven testing, and integrates with TestNG for reporting.

Key Features

Shopping Cart & Checkout: Automates actions like adding items to the cart, updating quantities, and completing the checkout process.
Data-Driven: Uses Cucumber DataTables and Excel files for flexible, dynamic test data input.
Test Scenarios: Covers various scenarios like adding products, verifying cart details, and placing orders.


1. Code Optimization & Reusability
Refactor Redundant Methods: Combine similar actions (e.g., adding items) into reusable methods and reduce repetitive code by creating utility methods for common actions like selecting product sizes and quantities.

2. Utility Functions & logging/session check/retry analyser
Create more utility functions for common tasks (e.g., scrolling, waiting, pop-up handling), logging can also be added by adding log4j or any other advanced logging mechnanism

3. Advanced Reporting
Custom Reporting: Integrate tools like ExtentReports or Allure for advanced reporting, including screenshots and logs.

4. Parameterization
Multi-Environment Testing and Parallel Execution: Parameterize browser configurations & Enable parallel execution to speed up test runs using TestNG's parallel execution feature like xml files

5. Encryption & Decryption
Sensitive Data Encryption: Encrypt sensitive data like payment details when storing it externally (e.g., in Excel files)Secure Test DataDe then decrypt sensitive data only when needed during test execution.

6. Documentation
Comments/Block comments can be used more in future.

How to Run:

1.Clone the repository. 

2.Install the necessary dependencies:

3.Java 21

4.Maven 

5.Cucumber dependencies (cucumber-java, cucumber-testng)

6.Selenium WebDriver

7.Apache POI (for Excel file handling)

8.Run the tests using TestNG:

9.You can run all tests by executing the TestNG XML configuration or running individual features via Maven-  mvn clean test 

