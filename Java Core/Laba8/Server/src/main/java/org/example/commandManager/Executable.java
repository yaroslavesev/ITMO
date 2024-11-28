package org.example.commandManager;

import org.example.managers.UserStatusManager;
import org.example.response.Response;

/**
 * Интерфейс реализуемый каждой командой, отвечает за непосредственное выполнение команды
 */
public interface Executable {
    Response execution(String args, Object object, UserStatusManager userStatusManager);
}
