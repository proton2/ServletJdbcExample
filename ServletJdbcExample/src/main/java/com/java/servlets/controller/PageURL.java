package com.java.servlets.controller;

/**
 * Created by b.yacenko on 14.06.2017.
 */
public interface PageURL {
    String LOGIN_PAGE = "/login.jsp";
    String LOGIN_ACTION = "/WorkTaskController?action=login";
    String ERROR_PAGE = "/error.jsp";
    String DOWNLOAD = "/download?downloadfile=";

    String EDIT_WORKTASK = "/WorkTaskController?action=edit_worktask&id=";
    String LIST_WORKTASK_FORWARD = "/WorkTaskController?action=list_worktasks";
    String LIST_WORKTASKS = "/listWorkTask.jsp";
    String INSERT_OR_EDIT_WORKTASK = "/workTask.jsp";
    String INSERT_OR_EDIT_ATTACH = "/attach.jsp";

    String SELECT_USER = "/selectUser.jsp";
    String EDIT_USER = "/user.jsp";
    String LIST_USERS = "/WorkTaskController?action=list_users";
    String LIST_USER = "/listUser.jsp";
}