package Response;

import java.io.Serializable;

public enum STATUS implements Serializable {
    ERROR,
    OK,
    NEED_OBJECT,
    OBJECT,
    COMMAND,
    SAVE;
    private static final long serialVersionUID = 228L;
}
