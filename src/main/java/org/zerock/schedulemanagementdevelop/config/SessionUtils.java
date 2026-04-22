package org.zerock.schedulemanagementdevelop.config;

import jakarta.servlet.http.HttpSession;
import org.zerock.schedulemanagementdevelop.exception.UnauthorizedException;
import org.zerock.schedulemanagementdevelop.user.dto.SessionUser;

public abstract class SessionUtils {

    public static SessionUser getLoginUser(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return sessionUser;
    }
}
