package com.challenge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.controllers.TwitterController;
import com.challenge.domain.People;
import com.challenge.service.TwitterService;
import com.challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.List;
 
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TwitterControllerTest {
 
	private MockMvc mockMvc;

    @Mock
    private TwitterService twitterService;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private TwitterController twitterController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(twitterController)
                .build();
    }

    @Test
    public void test_get_Users() throws Exception {
    	List<People> users = Arrays.asList(
                new People(1, "Daenerys", "Daenerys Targaryen"),
                new People(2, "john", "John Snow"));
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].handle", is("Daenerys")))
                .andExpect(jsonPath("$[0].name", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].handle", is("john")))
                .andExpect(jsonPath("$[1].name", is("John Snow")));
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_create_user_fail_404_not_found() throws Exception {
    	People user = new People("username exists");
        when(userService.checkPersonExists(user.getId())).thenReturn(true);
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isConflict());
        verify(userService, times(1)).checkPersonExists(user.getId());
        verifyNoMoreInteractions(userService);
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}