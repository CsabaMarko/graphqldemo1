package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.GraphQlConfig;
import com.csabamarko.springboot.jpa.graphqldemo1.common.TestUtils;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.test.tester.GraphQlTester;

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

    @Test
    void emptyResult() {
        var queryName = "users";
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryUtil.GraphQlQueryModel.builder()
                .queryName(queryName).page(1).size(2).build());
        var response = TestUtils.executeQuery(graphQlTester, queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(User.class).hasSize(0));
    }

    @Test
    void unfilteredResult() {
        var queryName = "users";
        var testCollection = TestUtils.pageOf(TestData.u1, TestData.u2, TestData.u3, TestData.u4);
        var page = 1;
        var size = 4;

        when(repository.findAll(any(Pageable.class))).thenReturn(testCollection);
        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryUtil.GraphQlQueryModel.builder()
                .queryName(queryName).page(page).size(size).build());
        var response = TestUtils.executeQuery(graphQlTester, queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(User.class)
                .hasSize(testCollection.getSize()));
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
