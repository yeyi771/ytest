/*
 * 
 * author: yeyi
 * date: 2017年10月31日
 */
package com.yeyi.ytest.DataBase.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * @author: yeyi
 * @date: 2017年10月31日
 */
@WebListener
public class RequestListener implements ServletRequestListener
{

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
        System.out.println("----------------requestDestroyed: "+arg0.toString());
    }

    @Override
    public void requestInitialized(ServletRequestEvent arg0) {
        System.out.println("----------------requestInitialized:"+arg0.toString());
    }  
}