package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Group;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Group должен ")
@DataJpaTest
public class GroupRepositoryJpaTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GroupRepository groupRepository;

    private static final long FIRST_GROUP_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_GROUPS = 6;
    private static final String GROUP_TITLE_CREATED = "GroupName_Created";
    private static final String GROUP_TITLE_UPDATED = "GroupName_Updated";

    @DisplayName("загружать группу по id")
    @Test
    void shouldFindGroupById() {
        val optionalActualGroup = groupRepository.findById(FIRST_GROUP_ID);
        val expectedGroup = em.find(Group.class, FIRST_GROUP_ID);
        assertThat(optionalActualGroup).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGroup);
    }

    @DisplayName("загружать список всех групп")
    @Test
    void shouldFindAllGroups() {
        var groups = groupRepository.findAll();
        var expectedGroup1 = em.find(Group.class, FIRST_GROUP_ID);

        assertThat(groups).hasSize(EXPECTED_NUMBER_OF_GROUPS);
        assertThat(expectedGroup1.getId()).isEqualTo(groups.get(0).getId());
    }

    @DisplayName("сохранять новую группу")
    @Test
    void shouldSaveNewGroup() {
        var expectedGroup = getCreatedGroup();
        var savedGroup = groupRepository.save(expectedGroup);
        assertThat(savedGroup).isNotNull()
                .matches(group -> group.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedGroup);

        assertThat(em.find(Group.class,
                savedGroup.getId())).isEqualTo(savedGroup);
    }

    @DisplayName("сохранять измененную группу")
    @Test
    void shouldSaveUpdatedGroup() {
        var expectedGroup = getUpdatedGroup();
        assertThat(em.find(Group.class, expectedGroup.getId())).isNotEqualTo(expectedGroup);

        var savedGroup = groupRepository.save(expectedGroup);
        assertThat(savedGroup).isNotNull()
                .matches(group -> group.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedGroup);

        assertThat(em.find(Group.class, savedGroup.getId())).isEqualTo(savedGroup);
    }

    @DisplayName("удалять группу по id")
    @Test
    void shouldDeleteGroup() {
        assertThat(em.find(Group.class, FIRST_GROUP_ID)).isNotNull();
        groupRepository.deleteById(FIRST_GROUP_ID);
        assertThat(em.find(Group.class, FIRST_GROUP_ID)).isNull();
    }

    private Group getCreatedGroup() {

        return Group.builder()
                .id(0)
                .name(GROUP_TITLE_CREATED)
                .info("Desc")
                .startAt(LocalDate.of(2025,1, 1))
                .endAt(LocalDate.of(2025,7, 1))
                .build();
    }

    private Group getUpdatedGroup() {
        return Group.builder()
                .id(FIRST_GROUP_ID)
                .name(GROUP_TITLE_UPDATED)
                .info("Desc")
                .startAt(LocalDate.of(2025,1, 1))
                .endAt(LocalDate.of(2025,7, 1))
                .build();
    }
}
