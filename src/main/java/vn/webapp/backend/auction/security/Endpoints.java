package vn.webapp.backend.auction.security;

public class Endpoints {
    public static final String front_end_host = "http://localhost:3000";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**",
            "/api/v1/auction-history/get-by-auction/**", "/api/v1/auction-history/get-by-username/**",
            "/api/v1/auction-history/get-by-date/**", "/api/v1/image/**", "/api/v1/user/by-email/**",
            "/api/v1/user/by-username/**", "/api/v1/bank/**", "/api/v1/payment/vn-pay" ,
            "/api/v1/payment/vn-pay-callback/**", "/api/v1/auction-registration/**",
            "/api/v1/transaction/**", "/api/v1/auth/logout", "api/v1/bank","/api/v1/aution-history/get-when-auction-finished/**",
            "/api/v1/user/get-winner-auction/**"

    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/v1/auth/**", "/api/v1/auction-history", "/api/v1/auth/logout"
    };

    public static final String[] PUBLIC_PUT_ENDPOINTS = {
            "/api/v1/auction/set-state/**", "/api/v1/user"
    };

    public static final String[] MANAGER_GET_ENDPOINTS = {
    };

    public static final String[] MANAGER_PUT_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**"
    };

    public static final String[] MANAGER_POST_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**"
    };

    public static final String[] MANAGER_DELETE_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/v1/user/**", "/api/v1/role/**"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/v1/user/**"
    };

    public static final String[] ADMIN_PUT_ENDPOINTS = {
            "/api/v1/user/**"
    };
}
