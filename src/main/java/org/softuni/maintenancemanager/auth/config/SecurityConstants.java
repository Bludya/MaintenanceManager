package org.softuni.maintenancemanager.auth.config;

public class SecurityConstants {
    public static final String SECRET = "MaintMan";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String APPEND_STRING =
            "{\"%s\":\"%s %s\", " +
                    "\"name\": \"%s\"," +
                    "\"roles\": \"%s\"}";

    public static final String SIGN_UP_URL = "/users/register";
    public static final String SIGN_IN_URL = "/users/login";

    public static final String[] ADMIN_URLS = {
            "/users/all",
            "/users/find/{searchWord}",
            "/users/activate",
            "/users/deactivate",
            "/users/delete",
            "/users/change-role",
            "/logs/all",
            "/"
    };

    public static final String[] MANAGER_URLS = {
            "/tasks/add",
            "/projects/action",
            "/projects/delete",
            "/project-systems/add",
    };
}