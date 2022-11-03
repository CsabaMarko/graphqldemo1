package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.GraphQlConfig;
import com.csabamarko.springboot.jpa.graphqldemo1.common.TestUtils;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil.GraphQlQueryModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.time.OffsetDateTime;

import static com.csabamarko.springboot.jpa.graphqldemo1.test.GraphQlAssertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(GraphQlConfig.class)
@GraphQlTest(ChangeRequestController.class)
public class ChangeRequestControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private ChangeRequestRepository repository;

    @Test
    void emptyResult() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        var queryName = "changeRequests";
        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName).page(1)
                .size(2).build());
        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class).hasSize(0));
    }

    @Test
    void unfilteredResult() {
        var queryName = "changeRequests";
        var testCollection = TestUtils.pageOf(TestData.cr1, TestData.cr2, TestData.cr3);
        var page = 1;
        var size = 3;

        when(repository.findAll(any(Pageable.class))).thenReturn(testCollection);
        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName).page(page)
                .size(size).build());
        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class)
                .hasSize(testCollection.getSize()));
    }

    private GraphQlTester.Response executeQuery(String query) {
        return TestUtils.executeQuery(graphQlTester, query);
    }

    private static class TestData {
        static final ChangeRequest cr1 = ChangeRequest.builder().id("CR1").changeNumber("CHG903001")
                .shortDescription("Short Description 1").description("Description 1").active(true).type("Normal")
                .assignedTo(UserControllerTest.TestData.u1).createdBy(UserControllerTest.TestData.u2)
                .requestedBy(UserControllerTest.TestData.u3)
                .workStart(OffsetDateTime.parse("2021-04-12T00:00:00Z"))
                .workEnd(OffsetDateTime.parse("2021-04-13T00:00:00Z"))
                .timeWorkedHours(5)
                .build();
        static final ChangeRequest cr2 = ChangeRequest.builder().id("CR2").changeNumber("CHG903002")
                .shortDescription("Short Description 2").description("Description 2").active(false).type("Standard")
                .createdBy(UserControllerTest.TestData.u2)
                .requestedBy(UserControllerTest.TestData.u3)
                .build();
        static final ChangeRequest cr3 = ChangeRequest.builder().id("CR3").changeNumber("CHG903003")
                .shortDescription("Short Description 3").description("Description 3").active(true).type("Emergency")
                .createdBy(UserControllerTest.TestData.u4)
                .requestedBy(UserControllerTest.TestData.u2)
                .build();
    }
}
