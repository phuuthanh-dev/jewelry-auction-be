package vn.webapp.backend.auction.security;

public final class Endpoints {

    private Endpoints() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**",
            "/api/v1/auction-history/get-by-auction/**", "/api/v1/auction-history/get-by-username/**",
            "/api/v1/auction-history/get-by-date/**", "/api/v1/image/**", "/api/v1/user/by-email/**",
            "/api/v1/user/by-username/**", "/api/v1/bank/**", "/api/v1/payment/**",
            "/api/v1/auction-registration/**", "/api/v1/transaction/**",  "api/v1/bank",
            "/api/v1/auction-history/get-when-auction-finished/**",
            "/api/v1/user/get-winner-auction/**", "/api/v1/request-approval/**",
            "/api/v1/transaction/get-by-type-state", "/api/v1/auction-history/get-by-auction-and-user/**"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/v1/auction-history", "/api/v1/jewelry/jewelry-request","/api/v1/image/add-image",
            "/api/v1/request-approval/send-from-user",  "/api/v1/request-approval/send-from-staff", "/api/v1/request-approval/send-from-manager",
            "/api/v1/transaction/create-transaction-for-winner/**",  "/api/v1/transaction/create-transaction-for-winner-if-not-exist/**",
    };

    public static final String[] PUBLIC_PUT_ENDPOINTS = {
            "/api/v1/user/change-password",
            "/api/v1/auction/set-state/**", "/api/v1/user","/api/v1/request-approval/set-state/**",
            "/api/v1/request-approval/confirm/**", "/api/v1/request-approval/cancel-request",
            "/api/v1/transaction/set-method/**", "/api/v1/jewelry/set-holding/**"
    };

    public static final String[] MANAGER_GET_ENDPOINTS = {
            "/api/v1/transaction/get-handover"
    };

    public static final String[] MANAGER_PUT_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**",
            "/api/v1/transaction/set-state/**"
    };

    public static final String[] MANAGER_POST_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**"
    };

    public static final String[] MANAGER_DELETE_ENDPOINTS = {
            "/api/v1/jewelry/**", "/api/v1/jewelry-category/**", "/api/v1/auction/**",
            "/api/v1/image/jewelry/**"
    };


    public static final String[] MANAGER_ADMIN_GET_ENDPOINTS = {
            "/api/v1/user/**"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
//            "/api/v1/user/**"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/v1/user/**"
    };

    public static final String[] ADMIN_PUT_ENDPOINTS = {
            "/api/v1/user/**"
    };
}
