package slipp.controller;

import nextstep.mvc.tobe.RequestMappingHandlerMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import slipp.ManualHandlerMapping;
import slipp.domain.User;
import slipp.support.db.DataBase;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private RequestMappingHandlerMapping mappings;

    @BeforeEach
    void setUp() {
        mappings = new RequestMappingHandlerMapping(new ManualHandlerMapping());
        mappings.initialize();
    }

    @Test
    @DisplayName("레거시 컨트롤러_RequestMapping 적용 : createUser")
    void createUser() throws Exception {
        // 회원가입
        User expected = new User("sloth", "password", "redman", "marx@communism.rus");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/create");
        request.setParameter("userId", expected.getUserId());
        request.setParameter("password", expected.getPassword());
        request.setParameter("name", expected.getName());
        request.setParameter("email", expected.getEmail());

        MockHttpServletResponse response = new MockHttpServletResponse();

        mappings.handle(request, response);

        assertThat(DataBase.findUserById("sloth")).isEqualTo(expected);
    }

}