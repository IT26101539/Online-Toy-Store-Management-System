// This file belongs to the Admin_Management.repository package (folder)
        package com.toystore.Admin_Management.repository;

//FileHandler: helper class (from the common package) that reads/writes text files

import com.toystore.common.FileHandler;
import com.toystore.Admin_Management.model.ActivityLog;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

// COMPONENT 04 - Admin Management
// Owner - IT26101539
// @Repositor - tells Spring this class handles data storage,
// so Spring creates and manages its object automatically

@Repository
public class ActivityLogRepository{

    // Helper object used to read/write the log text file

    private final FileHandler fileHandler;

    // Constructor: links this repository to the file "admin_logs.txt"

    public ActivityLogRepository(){
        this.fileHandler=new FileHandler("admin_logs.txt");
    }

    // Saves one log entry by adding it as a new line at the end of the file.
    // "synchronized" = only one thread can run this at a time (prevents file corruption)

    public synchronized void save(ActivityLog log){
        fileHandler.appendLine(log.toFileString());
    }

    // Reads all lines from the file and converts each line back into an ActivityLog object

    public synchronized List<ActivityLog> findAll(){

        // Read every line of the file as text

        List<String>lines=fileHandler.readAllLines();

        // Empty list to collect the ActivityLog objects
        List<ActivityLog>logs=new ArrayList<>();
        for (String line : lines) {

            // Split the line at "|" (escaped as \\| because | is special in regex)
            String[] parts = line.split("\\|");

            // A valid log line must have 3 parts: adminId, action, timestamp
            if (parts.length>=3) {
                logs.add(new ActivityLog(parts[0],parts[1],parts[2]));
            }
        }
        return logs;
    }
}