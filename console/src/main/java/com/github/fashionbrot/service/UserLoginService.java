package com.github.fashionbrot.service;



import com.github.fashionbrot.consts.GlobalConst;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.enums.SameSiteEnum;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.util.CookieUtil;
import com.github.fashionbrot.util.JwtTokenUtil;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.fashionbrot.util.ResponseCookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * @author fashi
 */
@Slf4j
@Service
public class UserLoginService {

    HttpServletRequest request;
    HttpServletResponse response;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


//    public LoginModel getLogin2() {
//        String token = CookieUtil.getCookieValue(request, GlobalConst.AUTH_KEY,false);
//        if (StringUtils.isEmpty(token)) {
//            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
//        }
//        Map<String, Claim> claimMap = JwtUtil.verifyToken(token, JwtUtil.getAlgorithmRsa(AlgorithmEnum.RSA256,RSA_PUBLIC_KEY,RSA_PRIVATE_KEY));
//        LoginModel model = null;
//        if (ObjectUtil.isNotEmpty(claimMap)){
//            model = LoginModel.builder().build();
//            Claim userIdClaim = claimMap.get(SYSTEM_USER_ID);
//            if (userIdClaim==null){
//                throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
//            }
//            model.setUserId(userIdClaim.asLong());
//            Claim claimRole = claimMap.get(SYSTEM_ROLE_ID);
//            if (claimRole!=null){
//                model.setRoleId(claimRole.asLong());
//            }
//            model.setSuperAdmin(claimMap.get("superAdmin").asBoolean());
//        }
//        if (model!=null){
//            request.setAttribute(GlobalConst.AUTH_KEY,model);
//        }
//        return model;
//    }

    //    public LoginModel getLogin() {
//        Object attribute = request.getAttribute(GlobalConst.AUTH_KEY);
//        if (attribute!=null){
//            return (LoginModel) attribute;
//        }
////        Cookie[] cookies = request.getCookies();
//
//        String token = CookieUtil.getCookieValue(request, GlobalConst.AUTH_KEY,false);
//        if (StringUtils.isEmpty(token)) {
//            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
//        }
//
//        Map<String, String> stringStringMap = CustomVerifyUtil.verifyTokenMap(token,RSA_PRIVATE_KEY);
//        if (ObjectUtil.isEmpty(stringStringMap)){
//            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
//        }
//
//        LoginModel model = LoginModel.builder()
//                .userId(ObjectUtil.parseLong(stringStringMap.get("u")))
//                .userName(stringStringMap.get("un"))
//                .roleId(ObjectUtil.parseLong(stringStringMap.get("r")))
//                .superAdmin(ObjectUtil.parseBoolean(stringStringMap.get("s")))
//                .build();
//
//        if (model!=null){
//            request.setAttribute(GlobalConst.AUTH_KEY,model);
//        }
//        return model;
//    }
    public LoginModel getLogin() {
        Object attribute = request.getAttribute(GlobalConst.AUTH_KEY);
        if (attribute != null) {
            return (LoginModel) attribute;
        }
        String token = CookieUtil.getCookieValue(request, GlobalConst.AUTH_KEY, false);
        if (ObjectUtil.isEmpty(token)) {
            throw new DynamicConfigException(RespEnum.SIGNATURE_MISMATCH);
        }
        LoginModel login = JwtTokenUtil.getLogin(token);
        if (login != null) {
            request.setAttribute(GlobalConst.AUTH_KEY, login);
        }
        return login;
    }


    public LoginModel getSafeLogin() {
        try {
            Object attribute = request.getAttribute(GlobalConst.AUTH_KEY);
            if (attribute == null) {
                return getLogin();
            } else {
                return (LoginModel) attribute;
            }
        } catch (Exception e) {
//            if (e instanceof MarsException) {
//                log.error("getSafeLogin error msg:{}", ((MarsException) e).getMsg());
//            }
        }
        return null;
    }


//    public void setCookie2(Long userId,Long roleId,boolean superAdmin){
//        String token = JwtUtil.createToken(
//                MapUtil.put(SYSTEM_USER_ID, userId,SYSTEM_ROLE_ID,roleId,"superAdmin",superAdmin),
//                JwtUtil.getAlgorithmRsa(AlgorithmEnum.RSA256,RSA_PUBLIC_KEY,RSA_PRIVATE_KEY),
//                45*60);
//        setCookie(token);
//    }

    public void resetCookie() {
        LoginModel safeLogin = getSafeLogin();
        if (safeLogin != null) {
            setCookie(safeLogin);
        }
    }

//    public void setCookie(Long userId, String username, Long roleId, boolean superAdmin) {
//        String token = CustomVerifyUtil.createToken(
//                MapUtil.put("u", "un", username, userId, "r", roleId, "s", superAdmin),
//                45L,
//                RSA_PUBLIC_KEY);
//
//        setCookie(token);
//    }
    public void setCookie(LoginModel model) {
        String token = JwtTokenUtil.createToken(model.getUserId(), model.getUserName(), model.getRoleId(), model.getRoleName(), model.isSuperAdmin(), model.getCompanyId());

        setCookie(token);
    }

    public void setCookie(String token) {
        if (ObjectUtil.isNotEmpty(token)) {
            CookieUtil.addCookie(response, ResponseCookie.builder()
                    .domain(request.getServerName())
                    .httpOnly(true)
                    .maxAge(Duration.ofSeconds(45 * 60))
                    .path("/")
                    .sameSite(SameSiteEnum.STRICT)
                    .secure(true)
                    .name(GlobalConst.AUTH_KEY)
                    .value(token)
                    .build());
        }
    }




}
