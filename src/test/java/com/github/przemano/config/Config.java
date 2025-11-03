package com.github.przemano.config;

public class Config {


        /** Timeout in seconds - page loading time and other actions for the wait object of the WebDriverWait class*/
        public static final long Timeout = 3;

        public static class Timeout {
            public static final int PageToLoad = 30; //sec
        }

        public static class URL {
                /** Default home page of the tested website. Can be overwritten with variables from CI/CD tools */
                private static final String BaseUrlDefault = "https://practicesoftwaretesting.com/";
                /** Default home page of the tested website - from CI/CD tools or from BaseUrlDefault*/
                public static final String BaseURL = System.getenv("BASE_URL") == null ? BaseUrlDefault : System.getenv("BASE_URL");

                /** Selenium Grid URL using Docker container - default value*/
                private static final String SeleniumGridDefault = "http://host.docker.internal:4444/wd/hub";
                /** Selenium Grid URL using Docker container*/
                public static final String SeleniumGrid = System.getenv("SELENIUM_GRID_URL") == null ? SeleniumGridDefault : System.getenv("SELENIUM_GRID_URL");

        }
}
