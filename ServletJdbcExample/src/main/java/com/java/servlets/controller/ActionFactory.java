package com.java.servlets.controller;

import com.java.servlets.controller.Commands.EmptyCommand;
import com.java.servlets.controller.Commands.ErrorCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest req){
        ActionCommand current = new ErrorCommand();
        String action = req.getParameter("action");
        if (action==null  || action.isEmpty()){
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e){
            req.setAttribute("wrongAction", action);
        }
        return current;
    }
}
