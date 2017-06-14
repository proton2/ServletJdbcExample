package com.java.servlets.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by b.yacenko on 13.06.2017.
 */
public interface ActionCommand {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
