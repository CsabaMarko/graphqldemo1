package com.csabamarko.springboot.jpa.graphqldemo1.snow.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.AllEntitiesMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.EntityMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.FieldMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.ChangeRequest;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.Customer;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.Product;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.User;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * Generated code!!!
 *  See: com.csabamarko.springboot.jpa.graphqldemo1.util.ToGraphQlReflectionUtil#metaModelGen()
 */
public class EntityFilterMetaData {

    public static final EntityMeta<RootEntity<Serializable>, Serializable> changeRequest = EntityMeta.from(ChangeRequest.class, Arrays.asList(
            FieldMeta.from(String.class, "id", true),
            FieldMeta.from(String.class, "changeNumber"),
            FieldMeta.from(String.class, "shortDescription"),
            FieldMeta.from(String.class, "description"),
            FieldMeta.from(Boolean.class, "active"),
            FieldMeta.from(String.class, "type"),
            FieldMeta.from(Integer.class, "timeWorkedHours")
    ));

    public static final EntityMeta<Customer, Long> customer = EntityMeta.from(Customer.class, Arrays.asList(
            FieldMeta.from(Long.class, "id", true),
            FieldMeta.from(String.class, "name")
    ));

    public static final EntityMeta<Product, Long> product = EntityMeta.from(Product.class, Arrays.asList(
            FieldMeta.from(Long.class, "id", true),
            FieldMeta.from(String.class, "sku"),
            FieldMeta.from(String.class, "name")
    ));

    public static final EntityMeta<User, String> user = EntityMeta.from(User.class, Arrays.asList(
            FieldMeta.from(String.class, "id", true),
            FieldMeta.from(String.class, "snId"),
            FieldMeta.from(String.class, "firstName"),
            FieldMeta.from(String.class, "lastName"),
            FieldMeta.from(String.class, "middleName"),
            FieldMeta.from(String.class, "phone"),
            FieldMeta.from(String.class, "email")
    ));


    private static final AllEntitiesMeta<? extends RootEntity<?>, ? extends Serializable> allEntitiesMeta =
            new AllEntitiesMeta<>();


    static {
        allEntitiesMeta.addT(changeRequest);
        allEntitiesMeta.addT(customer);
        allEntitiesMeta.addT(product);
        allEntitiesMeta.addT(user);
    }

    public static Optional<? extends EntityMeta<? extends RootEntity<?>, ? extends Serializable>>
    getForEntity(@NonNull Class<? extends RootEntity<?>> entityClass) {
        return allEntitiesMeta.getEntityMeta(entityClass);
    }

}
