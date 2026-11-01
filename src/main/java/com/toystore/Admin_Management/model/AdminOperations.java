//This file belongs to the Admin_Management.model package (folder)
package com.toystore.Admin_Management.model;

//Owner: Placeholder
//Abstraction - the interface says WHAT must be done, not HOW.
public interface AdminOperations {
    void registerAdmin(String username, String password, String email, String permissions) throws Exception;
    void logAction(String adminId, String action);
}
