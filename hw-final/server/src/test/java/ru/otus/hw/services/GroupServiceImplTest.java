package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;
import ru.otus.hw.mappers.GroupMapperImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Сервис Group должен ")
@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({GroupServiceImpl.class, GroupMapperImpl.class})
public class GroupServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_GROUPS = 6;

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

    @Autowired
    private GroupService groupService;

    @Test
    @DisplayName("загружать группу по id")
    public void shouldFindGroupById() {
        var actualGroup = groupService.findById(FIRST_GROUP_ID);
        var expectedGroup = getFirstGroup();

        assertThat(actualGroup).isNotNull()
                .matches(group -> group.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedGroup);
    }

    @DisplayName("загружать список всех групп")
    @Test
    void shouldReturnCorrectGroupsList() {
        var actualGroups = groupService.findAll();

        assertThat(actualGroups).isNotEmpty()
                .hasSize(EXPECTED_NUMBER_OF_GROUPS)
                .hasOnlyElementsOfType(GroupInfoDto.class);

        assertThat(actualGroups.get(0).getName()).isEqualTo(FIRST_GROUP_TITLE);
    }

    @DisplayName("сохранять новую группу")
    @Test
    void shouldSaveCreatedGroup() {
        var expectedGroup = getNewGroupData();
        var actualGroup = groupService.create(expectedGroup);
        expectedGroup.setId(7L);
        assertThat(actualGroup).isNotNull()
                .matches(group -> group.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedGroup);
    }


    @DisplayName("сохранять измененную группу")
    @Test
    void shouldSaveUpdatedGroup() {
        var expectedGroup = getFirstGroup();
        expectedGroup.setName(GROUP_TITLE_UPDATED);

        assertThat(groupService.findById(expectedGroup.getId()))
                .isNotNull();

        assertThat(groupService.findById(expectedGroup.getId()).getName()).isNotEqualTo(expectedGroup.getName());

        var actualGroup = groupService.update(expectedGroup);
        assertThat(actualGroup).isNotNull()
                .matches(group -> group.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedGroup);

        assertThat(groupService.findById(actualGroup.getId()))
                .isNotNull();

    }

    @DisplayName("удалять группу по id ")
    @Test
    void shouldDeleteGroup() {

        assertThat(groupService.findById(FIRST_GROUP_ID)).isNotNull();
        groupService.delete(FIRST_GROUP_ID);

        assertThatThrownBy(() -> groupService.findById(FIRST_GROUP_ID)).isInstanceOf(Exception.class)
                .hasMessage("Group with id 1 not found");
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

}