package model.service;

import model.entity.User;

public class Session {
    private static User CURRENT;

    public static void setCurrentUser(User u){ CURRENT = u; }
    public static User getCurrentUser(){ return CURRENT; }
    public static String getCurrentRole(){ return CURRENT==null ? null : CURRENT.getRole().name(); }
    public static boolean isLogged(){ return CURRENT != null; }
    public static void clear(){ CURRENT = null; }
}
