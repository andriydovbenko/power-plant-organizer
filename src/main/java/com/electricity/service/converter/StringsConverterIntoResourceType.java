package com.electricity.service.converter;

import com.electricity.enumeration.resource.PurchasableResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringsConverterIntoResourceType {
    private static final List<PurchasableResourceType> purchasableResourceTypes = new ArrayList<>() {{
        this.add(PurchasableResourceType.COAL);
        this.add(PurchasableResourceType.URANIUM);
    }};

    private StringsConverterIntoResourceType() {
    }

    public static synchronized PurchasableResourceType convert(String type) {
        PurchasableResourceType resourceType = null;

        Optional<PurchasableResourceType> typeOptional = purchasableResourceTypes.stream()
                .filter(pType -> (pType.toString()).equals(type))
                .findFirst();

        if (typeOptional.isPresent()) {
            resourceType = typeOptional.get();
        }

        return resourceType;
    }
}