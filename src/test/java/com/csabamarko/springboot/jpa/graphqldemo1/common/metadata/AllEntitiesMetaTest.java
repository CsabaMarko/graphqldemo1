package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.ChangeRequest;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.User;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class AllEntitiesMetaTest {

    @Test
    <E extends RootEntity<ID>, ID extends Serializable>
    void testConsWithTypeParams() {
        AllEntitiesMeta<E, ID> allEntities = new AllEntitiesMeta<>();

        var idField = new FieldMeta<>(String.class, "id", true);
        var changeNumberField = new FieldMeta<>(String.class, "changeNumber");
        var activeField = new FieldMeta<>(Boolean.class, "active");
        var timeWorkedField = new FieldMeta<>(Integer.class, "timeWorked");

        var changeRequestMeta = EntityMeta.<E, ID>from(ChangeRequest.class, Arrays.asList(
                idField, changeNumberField, activeField, timeWorkedField
        ));
        allEntities.add(changeRequestMeta);

        FieldMeta<?>[] userFields = {
                new FieldMeta<>(String.class, "id", true),
                new FieldMeta<>(String.class, "firstName"),
                new FieldMeta<>(String.class, "lastName")
        };

        var userMeta = EntityMeta.<E, ID>from(User.class, Arrays.asList(userFields));
        allEntities.add(userMeta);

        Assertions.assertTrue(allEntities.getEntityMeta(ChangeRequest.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(User.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(DummyEntity.class).isEmpty());

        Optional<EntityMeta<E, ID>> changeRequestMetaOption = allEntities.getEntityMeta(ChangeRequest.class);
        Assertions.assertTrue(changeRequestMetaOption.isPresent());
        EntityMeta<E, ID> changeRequestMetaResult = changeRequestMetaOption.get();
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(),
                changeRequestMetaOption.get().getType().getCanonicalName());

        FieldMeta<?> idFieldMeta = changeRequestMetaResult.getFieldMeta("id");
        Assertions.assertNotNull(idFieldMeta);
        Assertions.assertEquals(String.class.getCanonicalName(), idFieldMeta.getType().getCanonicalName());
        Assertions.assertTrue(idFieldMeta.isId());

        FieldMeta<?> activeFieldMeta = changeRequestMetaResult.getFieldMeta("active");
        Assertions.assertEquals(Boolean.class.getCanonicalName(), activeFieldMeta.getType().getCanonicalName());

        FieldMeta<?> timeWorkedFieldMeta = changeRequestMetaResult.getFieldMeta("timeWorked");
        Assertions.assertEquals(Integer.class.getCanonicalName(), timeWorkedFieldMeta.getType().getCanonicalName());
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void testConsWithoutTypeParams() {
        AllEntitiesMeta allEntities = new AllEntitiesMeta<>();

        FieldMeta idField = new FieldMeta<>(String.class, "id", true);
        FieldMeta changeNumberField = new FieldMeta<>(String.class, "changeNumber");
        FieldMeta activeField = new FieldMeta<>(Boolean.class, "active");
        FieldMeta timeWorkedField = new FieldMeta<>(Integer.class, "timeWorked");

        List<FieldMeta<?>> fieldListB = new ArrayList<>();
        fieldListB.add(idField);
        fieldListB.add(changeNumberField);
        fieldListB.add(activeField);
        fieldListB.add(timeWorkedField);

        EntityMeta changeRequestMeta = EntityMeta.from(ChangeRequest.class, fieldListB);
        allEntities.add(changeRequestMeta);

        FieldMeta[] userFields = {
                new FieldMeta(String.class, "id", true),
                new FieldMeta(String.class, "firstName"),
                new FieldMeta(String.class, "lastName")
        };

        EntityMeta userMeta = new EntityMeta(User.class, Arrays.asList(userFields));
        allEntities.add(userMeta);

        Assertions.assertTrue(allEntities.getEntityMeta(ChangeRequest.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(User.class).isPresent());
        Assertions.assertTrue(allEntities.getEntityMeta(DummyEntity.class).isEmpty());

        Optional<EntityMeta> changeRequestMetaOption = allEntities.getEntityMeta(ChangeRequest.class);
        Assertions.assertTrue(changeRequestMetaOption.isPresent());
        EntityMeta changeRequestMetaResult = changeRequestMetaOption.get();
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(),
                changeRequestMetaOption.get().getType().getCanonicalName());

        FieldMeta idFieldMeta = changeRequestMetaResult.getFieldMeta("id");
        Assertions.assertNotNull(idFieldMeta);
        Assertions.assertEquals(String.class.getCanonicalName(), idFieldMeta.getType().getCanonicalName());
        Assertions.assertTrue(idFieldMeta.isId());

        FieldMeta activeFieldMeta = changeRequestMetaResult.getFieldMeta("active");
        Assertions.assertEquals(Boolean.class.getCanonicalName(), activeFieldMeta.getType().getCanonicalName());

        FieldMeta timeWorkedFieldMeta = changeRequestMetaResult.getFieldMeta("timeWorked");
        Assertions.assertEquals(Integer.class.getCanonicalName(), timeWorkedFieldMeta.getType().getCanonicalName());

    }

    @Getter
    @Setter
    private static class DummyEntity<String> extends RootEntity<String> {
        private String id;

        @Override
        public String getId() {
            return id;
        }
    }
}
