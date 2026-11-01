package com.toystore.Admin_Management.model;

public interface AdminOperations {
    void registerAdmin(String username, String password, String email, String permissions) throws Exception;
    void logAction(String adminId, String action);
}
