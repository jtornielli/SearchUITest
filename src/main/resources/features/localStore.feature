Feature: Web Search and price check

  Scenario: Search for camisetas in website and list prices
    Given : I have access to the url requested
    And : I use the search bar and input the require search
    And : I click on the products and get the required information
    And : I repeat the process of the previous step in the second and third result page
    Then : I should have a list of all the products in a text file
