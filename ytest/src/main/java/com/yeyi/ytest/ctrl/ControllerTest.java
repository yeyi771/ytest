/*
 * 
 * author: yeyi
 * date: 2017年11月19日
 */
package com.yeyi.ytest.ctrl;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author: yeyi
 * @date: 2017年11月19日
 */
@RestController
@RequestMapping("/ctrlTest")
public class ControllerTest {
    
    @RequestMapping(value="/test")
    public String test(HttpServletRequest request,
        HttpServletResponse response,
        HttpSession session,
        WebRequest webRequest,
        Locale locale,
        InputStream input,
        OutputStream output,
        Principal principal,
        HttpEntity<?> entity,
        Model model,
        ModelMap modelMap,
        RedirectAttributes redirectA,
        Errors errors,
        BindingResult bindingResult,
        SessionStatus sessionStatus,
        UriComponentsBuilder uriBuilder){
        return "";
    }
}
