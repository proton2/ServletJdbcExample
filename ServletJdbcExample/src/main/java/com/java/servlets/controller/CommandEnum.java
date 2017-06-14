package com.java.servlets.controller;

import com.java.servlets.controller.Commands.DeleteAttachCommand;
import com.java.servlets.controller.Commands.DeleteCommentCommand;
import com.java.servlets.controller.Commands.DeleteUserCommand;
import com.java.servlets.controller.Commands.DeleteWorkTaskCommand;
import com.java.servlets.controller.Commands.DownloadAttachCommand;
import com.java.servlets.controller.Commands.EditAttachCommand;
import com.java.servlets.controller.Commands.EditUserCommand;
import com.java.servlets.controller.Commands.EditWorkTaskCommand;
import com.java.servlets.controller.Commands.ImportCommand;
import com.java.servlets.controller.Commands.InsertAttachCommand;
import com.java.servlets.controller.Commands.InsertUserCommand;
import com.java.servlets.controller.Commands.InsertWorkTaskCommand;
import com.java.servlets.controller.Commands.ListUsersCommand;
import com.java.servlets.controller.Commands.ListWorktasksCommand;
import com.java.servlets.controller.Commands.LogOutCommand;
import com.java.servlets.controller.Commands.LoginCommand;
import com.java.servlets.controller.Commands.OpenCommentCommand;
import com.java.servlets.controller.Commands.SaveAttachFileCommand;
import com.java.servlets.controller.Commands.SaveCommentCommand;
import com.java.servlets.controller.Commands.SaveUserCommand;
import com.java.servlets.controller.Commands.SaveWorkTaskCommand;
import com.java.servlets.controller.Commands.SelectUserCommand;

public enum CommandEnum {
    LOGIN {{
        this.command = new LoginCommand();
    }},
    LOGOUT {{
        this.command = new LogOutCommand();
    }},
    EDIT_WORKTASK {{
        this.command = new EditWorkTaskCommand();
    }},
    LIST_WORKTASKS {{
        this.command = new ListWorktasksCommand();
    }},
    LIST_USERS {{
        this.command = new ListUsersCommand();
    }},
    DELETE_WORKTASK {{
        this.command = new DeleteWorkTaskCommand();
    }},
    EDIT_ATTACH {{
        this.command = new EditAttachCommand();
    }},
    DELETE_ATTACH {{
        this.command = new DeleteAttachCommand();
    }},
    OPEN_COMMENT {{
        this.command = new OpenCommentCommand();
    }},
    SAVE_COMMENT {{
        this.command = new SaveCommentCommand();
    }},
    DELETE_COMMENT {{
        this.command = new DeleteCommentCommand();
    }},
    SELECT_USER {{
        this.command = new SelectUserCommand();
    }},
    INSERT_ATTACH {{
        this.command = new InsertAttachCommand();
    }},
    SAVE_WORKTASK {{
        this.command = new SaveWorkTaskCommand();
    }},
    INSERT_WORKTASK {{
        this.command = new InsertWorkTaskCommand();
    }},
    IMPORT {{
        this.command = new ImportCommand();
    }},
    SAVE_ATTACH_FILE {{
        this.command = new SaveAttachFileCommand();
    }},
    DOWNLOAD_ATTACH {{
        this.command = new DownloadAttachCommand();
    }},
    EDIT_USER {{
        this.command = new EditUserCommand();
    }},
    DELETE_USER {{
        this.command = new DeleteUserCommand();
    }},
    INSERT_USER {{
        this.command = new InsertUserCommand();
    }},
    SAVE_USER {{
        this.command = new SaveUserCommand();
    }};

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }

}
