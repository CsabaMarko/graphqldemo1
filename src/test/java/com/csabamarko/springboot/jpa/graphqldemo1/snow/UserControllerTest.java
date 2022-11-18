package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.GraphQlConfig;
import com.csabamarko.springboot.jpa.graphqldemo1.common.TestUtils;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterConverterComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.JpaSpecificationCreatorComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.SimpleFilterModel;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator.OperatorLiteral;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.FieldMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.test.tester.GraphQlTester;

import javax.persistence.criteria.Path;
import java.util.Optional;

import static com.csabamarko.springboot.jpa.graphqldemo1.test.GraphQlAssertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(GraphQlConfig.class)
@GraphQlTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private UserRepository repository;

    @MockBean
    private FilterConverterComponent<User, String> filterConverter;

    @MockBean
    private JpaSpecificationCreatorComponent<User, String> jpaSpecificationCreator;

    @Test
    void list_emptyResult() {
        var queryName = "users";
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.empty());

        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryUtil.GraphQlQueryModel.builder()
                .queryName(queryName).page(1).size(2).build());

        var response = TestUtils.executeQuery(graphQlTester, queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(User.class).hasSize(0));
    }

    @Test
    void list_unfilteredResult() {
        var queryName = "users";
        var testCollection = TestUtils.pageOf(TestData.u1, TestData.u2, TestData.u3, TestData.u4);
        var page = 1;
        var size = 4;

        when(repository.findAll(any(Pageable.class))).thenReturn(testCollection);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.empty());
        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryUtil.GraphQlQueryModel.builder()
                .queryName(queryName).page(page).size(size).build());

        var response = TestUtils.executeQuery(graphQlTester, queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(User.class)
                .hasSize(testCollection.getSize()));
    }

    @Test
    void list_filteredMultiResults() {
        var queryName = "users";
        var testCollection = TestUtils.pageOf(TestData.u1, TestData.u2);
        var page = 1;
        var size = 5;

        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(testCollection);

        // filter: { lastName: { equals: "Bunny" } }
        var filterValue = "Bunny";
        var fieldName = "lastName";
        var operator = OperatorLiteral.EQUALS;
        var filter = GraphQlQueryUtil.SimpleFilterModel.builder()
                .fieldName(fieldName).operator(operator.getText()).value(filterValue).build();
        var filterString = GraphQlQueryUtil.makeFilterString(filter);
        var queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryUtil.GraphQlQueryModel.builder().queryName(queryName)
                .filter(filterString)
                .page(page).size(size).build());
        var simpleFilterModel = makeFilterModel(operator, fieldName, filterValue);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.of(simpleFilterModel));

        var specification = makeSpecification(fieldName, filterValue);
        when(jpaSpecificationCreator.create(any())).thenReturn(specification);

        var response = TestUtils.executeQuery(graphQlTester, queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(User.class)
                .hasSize(testCollection.getSize()));
    }


    private static <F extends Comparable<F>>
    SimpleFilterModel<User, String, ? extends Comparable<?>> makeFilterModel(OperatorLiteral operatorLiteral,
                                                                             String fieldName, F value) {
        @SuppressWarnings("unchecked")
        Class<F> clazz = (Class<F>) value.getClass();
        var fieldModel = new FieldMeta<>(clazz, fieldName);
        return new SimpleFilterModel<>(User.class, operatorLiteral, fieldModel, value);
    }

    private static Specification<User> makeSpecification(String fieldName, String value) {
        return (root, query, builder) -> {
            final Path<String> path = root.get(fieldName);
            return builder.equal(path, value);
        };
    }

    static class TestData {
        static final User u1 = User.builder().id("U1").snId("bugs_bunny").firstName("Bugs").lastName("Bunny")
                .phone("1-555-123-4567").email("Bugs.Bunny@notwarner.com").build();
        static final User u2 = User.builder().id("U2").snId("lola_bunny").firstName("Lola").lastName("Bunny")
                .build();
        static final User u3 = User.builder().id("U3").snId("duffy_duck").firstName("Duffy").lastName("Duck")
                .email("Duffy.Duck@notwarner.com").build();
        static final User u4 = User.builder().id("U4").snId("porky_pig").firstName("Porky").lastName("Pig")
                .email("Porky.Pig@notwarner.com").build();
    }

}
