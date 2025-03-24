package com.hanshul.blog.utility;

public class Constant {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String POSTS_DEFAULT_SORT_FIELD = "postId";
    public static final String DEFAULT_SORT_ORDER = "dec";
    public static final Integer NORMAL_USER_ID = 102;
    public static final Integer ADMIN_USER_ID = 101;
    public static final String[] PUBLIC_URL = {"/api/v1/auth/**"
            ,"/v3/api-docs"
            ,"/v2/api-docs"
            ,"/swagger-resources/**"
            ,"/swagger-ui/**"
            ,"/swagger-ui/**"
            ,"/webjars"
    };
}
