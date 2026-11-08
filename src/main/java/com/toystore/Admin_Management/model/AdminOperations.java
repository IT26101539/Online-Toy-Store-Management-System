// This file belongs to the Admin_Management.model package (folder)
package com.toystore.Admin_Management.model;

// COMPONENT 04: Admin Management | Owner: IT26101539
// Abstraction: the interface says WHAT must be done, not how.
// The "how" is hidden inside AdminService.

public interface AdminOperations {

    // Registers a new admin. No method body here, only the name and parameters.
    // The implementing class (AdminService) must write the actual code.
    // "throws Exception" warns that an error can occur,
    // for example when the username already exists.

    void registerAdmin(String username,String password,String email,String permissions) throws Exception;

    // Records an action performed by an admin in the activity log.
    // adminId = who did it, action = what was done.

    void logAction(String adminId,String action);
}
