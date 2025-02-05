package com.alexandre.readinglist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alexandre.readinglist.controllers.ReadingListController;
import com.alexandre.readinglist.entities.Reader;
import com.alexandre.readinglist.repositories.ReadingListRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;

@WebMvcTest(ReadingListController.class)
public class MockMvcWebTestsWithSecurity {
    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ReadingListRepository readingListRepository;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity()).build();
    }

    @Test
    public void homePage_unauthenticatedUser() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "alexandre", password = "password", roles = "READER")
    public void homePage_authenticatedUser() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

}
