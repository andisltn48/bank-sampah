package com.babylo.banksampah.repositories.spesifications;

import org.springframework.data.jpa.domain.Specification;
import com.babylo.banksampah.entities.DataSampah;

public class DataSampahSpecification {

    public static Specification<DataSampah> searchByTerm(String searchTerm) {
        return (root, query, builder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return builder.conjunction(); // No filter if searchTerm is empty
            }

            String likePattern = "%" + searchTerm + "%";
            return builder.or(
                    builder.like(root.get("namaSampah"), likePattern),
                    builder.like(root.get("unit"), likePattern)
            );
        };
    }
}
