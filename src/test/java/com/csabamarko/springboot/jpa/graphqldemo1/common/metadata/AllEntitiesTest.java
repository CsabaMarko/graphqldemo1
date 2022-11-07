package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.ChangeRequest;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.Product;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

class AllEntitiesTest {

    @Test
    void testCons() {
        var allEntities = new AllEntities<>();

        FieldMeta[] changeRequestFields = {
                new FieldMeta(String.class, "id", true),
                new FieldMeta(String.class, "changeNumber"),
                new FieldMeta(Boolean.class, "active"),
                new FieldMeta(Integer.class, "timeWorked")
        };

        EntityMeta changeRequestMeta = new EntityMeta(ChangeRequest.class, changeRequestFields);
        allEntities.add(changeRequestMeta);

        FieldMeta[] userFields = {
                new FieldMeta(String.class, "id", true),
                new FieldMeta(String.class, "firstName"),
                new FieldMeta(String.class, "lastName")
        };

        EntityMeta userMeta = new EntityMeta(User.class, userFields);
        allEntities.add(userMeta);

        Assertions.assertTrue(allEntities.getEntityMeta(ChangeRequest.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(User.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(Product.class).isEmpty());

        EntityMeta changeRequestMeta2 = allEntities.getEntityMeta(ChangeRequest.class).get();
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(), changeRequestMeta2.getType().getCanonicalName());

        FieldMeta idFieldMeta = changeRequestMeta2.getFieldMeta("id");
        Assertions.assertEquals(String.class.getCanonicalName(), idFieldMeta.getType().getCanonicalName());
        Assertions.assertEquals(true, idFieldMeta.isId());

        FieldMeta activeFieldMeta = changeRequestMeta2.getFieldMeta("active");
        Assertions.assertEquals(Boolean.class.getCanonicalName(), activeFieldMeta.getType().getCanonicalName());

        FieldMeta timeWorkedFieldMeta = changeRequestMeta2.getFieldMeta("timeWorked");
        Assertions.assertEquals(Integer.class.getCanonicalName(), timeWorkedFieldMeta.getType().getCanonicalName());

    }

    @Test
    void testConsT() {
        var allEntities = new AllEntitiesT<>();

        var idField = new FieldMetaT<>(String.class, "id", true);
        var changeNumberField = new FieldMetaT<>(String.class, "changeNumber");
        var activeField = new FieldMetaT<>(Boolean.class, "active");
        var timeWorkedField = new FieldMetaT<Integer>(Integer.class, "timeWorked");

        var fieldList = new ArrayList<FieldMetaT>();
        fieldList.add(idField);
        fieldList.add(changeNumberField);
        fieldList.add(activeField);
        fieldList.add(timeWorkedField);

        EntityMetaT changeRequestMeta = new EntityMetaT(ChangeRequest.class, fieldList);
        allEntities.add(changeRequestMeta);

        FieldMetaT[] userFields = {
                new FieldMetaT(String.class, "id", true),
                new FieldMetaT(String.class, "firstName"),
                new FieldMetaT(String.class, "lastName")
        };

        EntityMetaT userMeta = new EntityMetaT(User.class, Arrays.asList(userFields));
        allEntities.add(userMeta);

        Assertions.assertTrue(allEntities.getEntityMeta(ChangeRequest.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(User.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(Product.class).isEmpty());

        EntityMetaT changeRequestMeta2 = allEntities.<ChangeRequest>getEntityMeta(ChangeRequest.class).get();
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(), changeRequestMeta2.getType().getCanonicalName());

        FieldMetaT idFieldMeta = changeRequestMeta2.getFieldMeta("id");
        Assertions.assertEquals(String.class.getCanonicalName(), idFieldMeta.getType().getCanonicalName());
        Assertions.assertEquals(true, idFieldMeta.isId());

        FieldMetaT activeFieldMeta = changeRequestMeta2.getFieldMeta("active");
        Assertions.assertEquals(Boolean.class.getCanonicalName(), activeFieldMeta.getType().getCanonicalName());

        FieldMetaT timeWorkedFieldMeta = changeRequestMeta2.getFieldMeta("timeWorked");
        Assertions.assertEquals(Integer.class.getCanonicalName(), timeWorkedFieldMeta.getType().getCanonicalName());

    }

}
