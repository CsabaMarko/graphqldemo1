package com.csabamarko.springboot.jpa.graphqldemo1.snow.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.EntityMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.FieldMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.*;

import java.util.Arrays;

/**
 * Generated code!!!
 *  See: com.csabamarko.springboot.jpa.graphqldemo1.util.ToGraphQlReflectionUtil#metaModelGen()
 */
public class EntityFilterMetaData {

    public static final EntityMeta<ChangeRequest, String, ?> changeRequest = EntityMeta.from(ChangeRequest.class, Arrays.asList(
            FieldMeta.from(String.class, "id", true),
            FieldMeta.from(String.class, "changeNumber"),
            FieldMeta.from(String.class, "shortDescription"),
            FieldMeta.from(String.class, "description"),
            FieldMeta.from(Boolean.class, "active"),
            FieldMeta.from(String.class, "type"),
            FieldMeta.from(Integer.class, "timeWorkedHours")
    ));

    public static final EntityMeta<Customer, Long, ?> customer = EntityMeta.from(Customer.class, Arrays.asList(
            FieldMeta.from(Long.class, "id", true),
            FieldMeta.from(String.class, "name")
    ));

    public static final EntityMeta<Product, Long, ?> product = EntityMeta.from(Product.class, Arrays.asList(
            FieldMeta.from(Long.class, "id", true),
            FieldMeta.from(String.class, "sku"),
            FieldMeta.from(String.class, "name")
    ));

    public static final EntityMeta<User, String, ?> user = EntityMeta.from(User.class, Arrays.asList(
            FieldMeta.from(String.class, "id", true),
            FieldMeta.from(String.class, "snId"),
            FieldMeta.from(String.class, "firstName"),
            FieldMeta.from(String.class, "lastName"),
            FieldMeta.from(String.class, "middleName"),
            FieldMeta.from(String.class, "phone"),
            FieldMeta.from(String.class, "email")
    ));

}
