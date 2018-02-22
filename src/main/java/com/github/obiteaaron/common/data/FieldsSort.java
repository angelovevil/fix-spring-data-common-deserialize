package com.github.obiteaaron.common.data;

import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by 刘一波 on 15/4/6.
 * E-Mail:obiteaaron@gmail.com
 */
public class FieldsSort extends Sort {

    private static final long serialVersionUID = -5734061149612933701L;

    private static final FieldOrder DEFAULT_FIXORDER = new FieldOrder("_id");

    public FieldsSort() {
        super(DEFAULT_FIXORDER);
        setOrders();
    }

    public FieldsSort(FieldOrder... orders) {
        super(DEFAULT_FIXORDER);
        setOrders(orders);
    }

    public FieldsSort(List<FieldOrder> orders) {
        super(DEFAULT_FIXORDER);
        setOrders(orders);
    }

    public FieldsSort(String... properties) {
        this(DEFAULT_DIRECTION, properties);
    }

    public FieldsSort(Direction direction, String... properties) {
        this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
    }

    public FieldsSort(Direction direction, List<String> properties) {
        super(DEFAULT_FIXORDER);
        if (properties == null || properties.isEmpty()) {
            setOrders();
            return;
        }

        List<FieldOrder> orders = new ArrayList<>();
        for (String property : properties) {
            orders.add(new FieldOrder(direction, property));
        }
        setOrders(orders);
    }

    @SuppressWarnings("unchecked")
	public FieldsSort(Sort sort) {
        this();
        if (sort == null) return;
        try {
            Field field = Sort.class.getDeclaredField("orders");
            field.setAccessible(true);
            List<Order> orders = (List<Order>) field.get(sort);
            List<FieldOrder> fieldOrders = new LinkedList<>();
            if (orders != null && orders.size() > 0) {
                for (Order order : orders) {
                    if (order != null)
                        fieldOrders.add(new FieldOrder(order));
                }
            }
            setOrders(fieldOrders);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setOrders(List<FieldOrder> orders) {
        try {
            Field field = Sort.class.getDeclaredField("orders");
            field.setAccessible(true);
            field.set(this, orders);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setOrders(FieldOrder... orders) {
        if (orders == null) {
            orders = new FieldOrder[0];
        }
        setOrders(Arrays.asList(orders));
    }

    public static class FieldOrder extends Order {

        private static final long serialVersionUID = -4307899744334924872L;

        public FieldOrder() {
            this("_id");
        }

        public FieldOrder(String property) {
            super("_id");
            setProperties(property);
        }

        public FieldOrder(Direction direction, String property) {
            super(direction, "_id");
            setProperties(property);
        }

        public FieldOrder(Direction direction, String property, NullHandling nullHandlingHint) {
            super(direction, "_id", nullHandlingHint);
            setProperties(property);
        }

        public FieldOrder(Order order) {
            this();
            if (order == null) return;
            String[] properties = {"direction", "property", "ignoreCase", "nullHandling"};
            try {
                for (String property : properties) {
                    Field field = Order.class.getDeclaredField(property);
                    field.setAccessible(true);
                    field.set(this, field.get(order));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }


        private void setProperties(String property) {
            Field field = null;
            try {
                field = Order.class.getDeclaredField("property");
                field.setAccessible(true);
                field.set(this, property);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

    }
}
