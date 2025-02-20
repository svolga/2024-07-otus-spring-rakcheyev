package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.GroupService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер Group должен ")
@WebMvcTest(controllers = GroupController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class GroupControllerTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final long FIRST_GROUP_ID = 1L;
    private static final String FIRST_GROUP_TITLE = "GroupTitle_1";
    private static final String FIRST_GROUP_INFO = "GroupDesc_1";
    private static final int FIRST_GROUP_PRICE = 120_000;

    private static final long FIRST_COURSE_ID = 1L;
    private static final String FIRST_COURSE_TITLE = "CourseTitle_1";
    private static final String FIRST_COURSE_INFO = "CourseDesc_1";
    private static final int FIRST_COURSE_PRICE = 120_000;

    private static final String GROUP_TITLE_CREATED = "GroupTitle_Created";
    private static final String GROUP_TITLE_UPDATED = "GroupTitle_Updated";

    private static final long SECOND_GROUP_ID = 2L;
    private static final String SECOND_GROUP_NAME = "Group_2";

    private static final String UPDATED_GROUP_NAME = "Updated Group";

    @Test
    @DisplayName("возвращать список групп")
    void shouldReturnCorrectGroupList() throws Exception {
        var groupDtos = List.of(
                getFirstGroupInfo()
        );

        when(groupService.findAll()).thenReturn(groupDtos);

        mvc.perform(get("/api/v1/group"))
                .andExpect(content().json(mapper.writeValueAsString(groupDtos)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("возвращать группу по id")
    void shouldReturnCorrectGroupById() throws Exception {
        var groupDto = getFirstGroup();
        given(groupService.findById(FIRST_GROUP_ID)).willReturn(groupDto);

        mvc.perform(get("/api/v1/group/{id}", FIRST_GROUP_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(groupDto)))
                .andDo(print());
    }

    @DisplayName("создавать группу")
    @Test
    void shouldCreateAuthor() throws Exception {
        GroupDto groupDto = getFirstGroup();
        GroupDto expectedGroupDto = GroupDto.builder()
                .id(groupDto.getId())
                .name(groupDto.getName())
                .info(groupDto.getInfo())
                .startAt(groupDto.getStartAt())
                .endAt(groupDto.getEndAt())
                .courseDto(groupDto.getCourseDto())
                .build();

        when(groupService.create(groupDto)).thenReturn(expectedGroupDto);

        ResultActions response = mvc.perform(post("/api/v1/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedGroupDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("обновлять группу")
    @Test
    void shouldUpdateAuthor() throws Exception {
        GroupDto groupDto = getFirstGroup();
        GroupDto groupUpdatedDto = GroupDto.builder()
                .id(groupDto.getId())
                .name(UPDATED_GROUP_NAME)
                .info(groupDto.getInfo())
                .startAt(groupDto.getStartAt())
                .endAt(groupDto.getEndAt())
                .courseDto(groupDto.getCourseDto())
                .build();

        when(groupService.update(groupUpdatedDto)).thenReturn(groupDto);

        ResultActions response = mvc.perform(put("/api/v1/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(groupDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("возвращать статус 404 при несуществующем id группы")
    @Test
    void shouldNotUpdateGroupWIthNotValidGroupId() throws Exception {
        long groupId = 100L;
        GroupDto groupDto = getFirstGroup();
        GroupDto groupUpdatedDto = GroupDto.builder()
                .id(groupId)
                .name(UPDATED_GROUP_NAME)
                .info(groupDto.getInfo())
                .startAt(groupDto.getStartAt())
                .endAt(groupDto.getEndAt())
                .courseDto(groupDto.getCourseDto())
                .build();

        when(groupService.update(groupUpdatedDto)).thenThrow(new EntityNotFoundException("Group with id %d not found".formatted(groupId)));

        ResultActions response = mvc.perform(put("/api/v1/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(groupUpdatedDto))
        );
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять группу по id")
    void shouldCorrectDeleteGroup() throws Exception {

        willDoNothing().given(groupService).delete(FIRST_GROUP_ID);

        ResultActions response = mvc.perform(delete("/api/v1/group/{id}", FIRST_GROUP_ID));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    private static GroupDto getFirstGroup() {
        return GroupDto.builder()
                .id(FIRST_GROUP_ID)
                .name(FIRST_GROUP_TITLE)
                .info(FIRST_GROUP_INFO)
                .startAt(LocalDate.of(2025,1, 1))
                .endAt(LocalDate.of(2025,7, 1))
                .courseDto(getFirstCourse())
                .build();
    }

    private static GroupDto getNewGroupData() {
        return GroupDto.builder()
                .id(0L)
                .name(FIRST_GROUP_TITLE)
                .info(FIRST_GROUP_INFO)
                .startAt(LocalDate.of(2025,1, 1))
                .endAt(LocalDate.of(2025,7, 1))
                .courseDto(getFirstCourse())
                .build();
    }

    private static CourseDto getFirstCourse() {
        return CourseDto.builder()
                .id(FIRST_COURSE_ID)
                .name(FIRST_COURSE_TITLE)
                .info(FIRST_COURSE_INFO)
                .price(FIRST_COURSE_PRICE)
                .build();
    }

    private static GroupInfoDto getFirstGroupInfo() {
        return GroupInfoDto.builder()
                .id(FIRST_GROUP_ID)
                .name(FIRST_GROUP_TITLE)
                .info(FIRST_GROUP_INFO)
                .startAt(LocalDate.of(2025,1, 1))
                .endAt(LocalDate.of(2025,7, 1))
                .courseName(getFirstCourse().getName())
                .build();
    }

}