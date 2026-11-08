package com.toystore.Toy_Management.repository;

import com.toystore.common.FileHandler;
import com.toystore.Toy_Management.model.EducationalToy;
import com.toystore.Toy_Management.model.ElectronicToy;
import com.toystore.Toy_Management.model.NonElectronicToy;
import com.toystore.Toy_Management.model.PlushToy;
import com.toystore.Toy_Management.model.Toy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Reads and writes toy records in the local pipe-delimited text file. */
@Repository
/** Loads and saves toy records in the local text file, rebuilding the proper toy subtype. */
public class ToyRepository {
    private final FileHandler fileHandler;

    public ToyRepository() {
        this.fileHandler = new FileHandler("toys.txt");
    }

    /** Appends one serialized toy record to the data file. */
    public synchronized void save(Toy toy) {
        fileHandler.appendLine(toy.toFileString());
    }

    /** Loads records and recreates the matching concrete toy subtype for each recognized type. */
    public synchronized List<Toy> findAll() {
        List<String> lines = fileHandler.readAllLines();
        List<Toy> toys = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 8) {
                String id = parts[0];
                String name = parts[1];
                String brand = parts[2];
                // parts[3] is category, which is implicit in the type
                String ageGroup = parts[4];
                double price = Double.parseDouble(parts[5]);
                int stockQuantity = Integer.parseInt(parts[6]);
                String type = parts[7];
                String imageFilename = (parts.length >= 9) ? parts[8] : "";

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
        return toys;
    }

    /** Finds a toy by its stable identifier, if a matching record exists. */
    public Optional<Toy> findById(String id) {
        return findAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    /** Replaces the matching toy record and writes the complete collection back to disk. */
    public synchronized void update(Toy updatedToy) {
        List<Toy> toys = findAll();
        for (int i = 0; i < toys.size(); i++) {
            if (toys.get(i).getId().equals(updatedToy.getId())) {
                toys.set(i, updatedToy);
                break;
            }
        }
        saveAll(toys);
    }

    /** Removes the requested toy and rewrites the remaining records. */
    public synchronized void delete(String id) {
        List<Toy> toys = findAll().stream()
                .filter(t -> !t.getId().equals(id))
                .collect(Collectors.toList());
        saveAll(toys);
    }

    /** Rewrites the data file using the serialized form of each remaining toy. */
    private void saveAll(List<Toy> toys) {
        List<String> lines = toys.stream().map(Toy::toFileString).collect(Collectors.toList());
        fileHandler.writeAllLines(lines);
    }
}
