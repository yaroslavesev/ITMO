package org.example.response;

import java.io.Serializable;

public enum STATUS implements Serializable {
    ERROR,
    OK,
    NEED_OBJECT,
    OBJECT,
    COMMAND,
    USERCHECK,
    SAVE;
    private static final long serialVersionUID = 228L;
}
