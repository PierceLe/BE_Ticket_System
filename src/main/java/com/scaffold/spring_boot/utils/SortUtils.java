package com.scaffold.spring_boot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Component;

@Component
public class SortUtils {
    public List<Order> generateOrder(String sortParams) {
        List<Order> orders = new ArrayList<>();
        String[] sort = sortParams.split(";");
        for (String s : sort) {
            String[] split = s.split(",");
            if (split.length != 2) {
                throw new RuntimeException("Request parameter sort error");
            }
            String property = split[0];
            Sort.Direction direction = Objects.equals(split[1], "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Order(direction, property));
        }
        return orders;
    }
}
