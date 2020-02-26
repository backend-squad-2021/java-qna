package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        return sessionUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser((session))) {
            return null;
        }

        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
