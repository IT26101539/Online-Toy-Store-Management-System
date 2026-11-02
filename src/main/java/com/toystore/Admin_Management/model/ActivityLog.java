// This file belongs to the Admin_Management.model package (folder)
package com.toystore.Admin_Management.model;

// COMPONENT 04: Admin Management
// Owner: IT26101539
// Model class - represents one activity log entry (who did what, and when)

// Encapsulation - fields are private,so they can only be accessed through getters.

public class ActivityLog {
    private String adminId; //ID of the admin who performed the action
    private String action;  // Description of what was done
    private String timestamp; // Date and time when the action happened

 /* Constructor - runs when a new ActivityLog object is created and sets the values of all three fields */
// "this" refers to the current object's field


    public ActivityLog(String adminId, String action, String timestamp) {
        this.adminId = adminId;
        this.action = action;
        this.timestamp = timestamp;
    }
    // Getters: allow other classes to read the private fields

    public String getAdminId() { return adminId; }
    public String getAction() { return action; }
    public String getTimestamp() { return timestamp; }


    // Converts this log entry into one line of text for saving in admin_logs.txt
    // Parts are separated by "|", e.g. A123|Registered new admin: sam|2026-11-01T10:30

    public String toFileString() {
        return adminId + "|" + action + "|" + timestamp;
    }
}
