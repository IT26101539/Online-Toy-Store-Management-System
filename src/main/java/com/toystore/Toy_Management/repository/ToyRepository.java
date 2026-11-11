// Declares the package containing toy data access classes.
package com.toystore.Toy_Management.repository;

// Imports the helper used to read and write the local data file.
import com.toystore.common.FileHandler;
// Imports the concrete toy subtypes used when restoring records.
import com.toystore.Toy_Management.model.EducationalToy;
import com.toystore.Toy_Management.model.ElectronicToy;
import com.toystore.Toy_Management.model.NonElectronicToy;
import com.toystore.Toy_Management.model.PlushToy;
// Imports the shared base type for all toys.
import com.toystore.Toy_Management.model.Toy;
// Imports Spring's repository annotation.
import org.springframework.stereotype.Repository;

// Imports collection types used to store toy records.
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Imports stream operations used to filter and transform toy records.
import java.util.stream.Collectors;

/** Reads and writes toy records in the local pipe-delimited text file. */
// Marks this class as a Spring repository component.
@Repository
/** Loads and saves toy records in the local text file, rebuilding the proper toy subtype. */
public class ToyRepository {
    // Handles file operations for the toy data file.
    private final FileHandler fileHandler;

    // Creates a file handler for the file that stores toy records.
    public ToyRepository() {
        this.fileHandler = new FileHandler("toys.txt");
    }

    /** Appends one serialized toy record to the data file. */
    // Synchronizes access so simultaneous writes do not overlap.
    public synchronized void save(Toy toy) {
        // Converts the toy to a text record and appends it to the file.
        fileHandler.appendLine(toy.toFileString());
    }

    /** Loads records and recreates the matching concrete toy subtype for each recognized type. */
    // Synchronizes access while reading the stored toy records.
    public synchronized List<Toy> findAll() {
        // Reads every line from the toy data file.
        List<String> lines = fileHandler.readAllLines();
        // Creates a list to hold the toys reconstructed from those lines.
        List<Toy> toys = new ArrayList<>();
        // Processes each stored record.
        for (String line : lines) {
            // Splits the record into fields using the pipe delimiter.
            String[] parts = line.split("\\|");
            // Continues only when the required fields are present.
            if (parts.length >= 8) {
                // Reads the toy's identifier from the first field.
                String id = parts[0];
                // Reads the toy's name from the second field.
                String name = parts[1];
                // Reads the brand from the third field.
                String brand = parts[2];
                // parts[3] is category, which is implicit in the type
                // Reads the intended age group from the fifth field.
                String ageGroup = parts[4];
                // Parses the price field as a decimal number.
                double price = Double.parseDouble(parts[5]);
                // Parses the stock quantity field as an integer.
                int stockQuantity = Integer.parseInt(parts[6]);
                // Reads the toy subtype token from the eighth field.
                String type = parts[7];
                // Reads the image filename when present, otherwise uses an empty string.
                String imageFilename = (parts.length >= 9) ? parts[8] : "";

                // Creates the matching subtype based on the stored type token.
                if ("EDUCATIONAL".equalsIgnoreCase(type)) {
                    toys.add(new EducationalToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename));
                } else if ("ELECTRONIC".equalsIgnoreCase(type)) {
                    toys.add(new ElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename));
                } else if ("PLUSH".equalsIgnoreCase(type)) {
                    toys.add(new PlushToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename));
                } else if ("NON_ELECTRONIC".equalsIgnoreCase(type) || "NON-ELECTRONIC".equalsIgnoreCase(type)) {
                    toys.add(new NonElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename));
                }
            }
        }
        // Returns the toys reconstructed from the file's recognized records.
        return toys;
    }

    /** Finds a toy by its stable identifier, if a matching record exists. */
    // Searches the loaded toy collection and returns an optional match.
    public Optional<Toy> findById(String id) {
        // Filters for the matching ID and returns the first matching toy, if any.
        return findAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    /** Replaces the matching toy record and writes the complete collection back to disk. */
    // Synchronizes the update to prevent overlapping file changes.
    public synchronized void update(Toy updatedToy) {
        // Loads the current collection of toys.
        List<Toy> toys = findAll();
        // Checks each toy for the identifier being updated.
        for (int i = 0; i < toys.size(); i++) {
            // Replaces the matching toy in the collection.
            if (toys.get(i).getId().equals(updatedToy.getId())) {
                toys.set(i, updatedToy);
                // Stops searching after the matching record is replaced.
                break;
            }
        }
        // Writes the updated collection back to the data file.
        saveAll(toys);
    }

    /** Removes the requested toy and rewrites the remaining records. */
    // Synchronizes the deletion to prevent overlapping file changes.
    public synchronized void delete(String id) {
        // Loads all toys, removes the one with the requested ID, and collects the rest.
        List<Toy> toys = findAll().stream()
                .filter(t -> !t.getId().equals(id))
                .collect(Collectors.toList());
        // Rewrites the file with the remaining toys.
        saveAll(toys);
    }

    /** Rewrites the data file using the serialized form of each remaining toy. */
    // Converts each toy to its stored text format and replaces the file contents.
    private void saveAll(List<Toy> toys) {
        // Builds a list of serialized toy records.
        List<String> lines = toys.stream().map(Toy::toFileString).collect(Collectors.toList());
        // Writes all records to the data file.
        fileHandler.writeAllLines(lines);
    }
}
