package app.verimly.task.adapter.persistence.jparepo;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.application.ports.out.persistence.FolderSummaryProjection;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.adapter.persistence.jparepo.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.defer-datasource-initialization=true"
})
public class FolderJpaRepositoryTest {


    private static final int TEST_FOLDER_COUNT = 3;
    private static final FolderTestData DATA = FolderTestData.getInstance();
    private static final UserId OWNER_ID = UserId.random();


    List<FolderEntity> folderEntities = new ArrayList<>();


    @Autowired
    private FolderJpaRepository repo;
    @Autowired
    private UserJpaRepository userJpaRepository;


    @BeforeEach
    public void setup() {
        UserEntity userEntity = DATA.userEntityWithId(OWNER_ID);

        for (int i = 0; i < TEST_FOLDER_COUNT; i++) {
            folderEntities.add(
                    DATA.folderEntityWithUserId(OWNER_ID)
            );
        }
        userJpaRepository.saveAndFlush(userEntity);
        repo.saveAllAndFlush(folderEntities);

    }


    @Test
    void should_startup_is_ok() {
        assertNotNull(repo);
    }

    @Test
    void findSummariesByOwnerId_thenReturnsFolders() {
        List<FolderSummaryProjection> list = repo.findSummariesByOwnerId(OWNER_ID.getValue());

        assertEquals(TEST_FOLDER_COUNT, list.size());
        for (int i = 0; i < TEST_FOLDER_COUNT; i++) {
            FolderEntity expected = folderEntities.get(i);
            FolderSummaryProjection actual = list.get(i);
            assertProjectionsEquals(expected, actual);

        }

    }

    void assertProjectionsEquals(FolderEntity expected, FolderSummaryProjection actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getLabelColor(), actual.getLabelColor());

    }


}
