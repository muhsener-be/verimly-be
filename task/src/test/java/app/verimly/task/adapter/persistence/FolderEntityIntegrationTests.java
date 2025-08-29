package app.verimly.task.adapter.persistence;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.adapter.persistence.jparepo.FolderJpaRepository;
import app.verimly.task.adapter.persistence.mapper.FolderDbMapper;
import app.verimly.task.adapter.persistence.mapper.FolderDbMapperImpl;
import app.verimly.task.application.exception.FolderNotFoundException;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import app.verimly.task.domain.vo.folder.FolderId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({FolderDbMapperImpl.class, CoreVoMapperImpl.class, FolderWriteRepositoryAdapter.class})
@TestPropertySource(properties = {
        "spring.jpa.defer-datasource-initialization=true"
})
public class FolderEntityIntegrationTests {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private FolderDbMapper dbMapper;

    FolderTestData DATA = FolderTestData.getInstance();
    Folder folder;
    FolderEntity jpa;
    FolderEntity jpaWithUser;
    UserEntity userEntity;

    @Autowired
    public FolderJpaRepository folderJpaRepository;


    @Autowired
    public FolderWriteRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity(UUID.randomUUID(), "TEset", "Test", "email.com", "aslkdjaksd");
        entityManager.persist(userEntity);
        folder = DATA.folderWithFullFields();
        jpa = dbMapper.toJpaEntity(folder);


        jpaWithUser = dbMapper.toJpaEntity(folder);
        jpaWithUser.setOwnerId(userEntity.getId());

        assertNotNull(folderJpaRepository);
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(entityManager);
        assertNotNull(dbMapper);
    }


    @Test
    void persist_whenUserNotExist_thenThrows() throws Throwable {

        Executable persistAndFlushExecutable = getPersistAndFlushExecutable(jpa);


        assertThrowsConstraintViolationException(persistAndFlushExecutable);
    }

    @Test
    void persist_whenNameIsNull_thenThrows() {
        jpa.setName(null);

        Executable exec = getPersistAndFlushExecutable(jpa);

        assertThrowsConstraintViolationException(exec);
    }


    @Test
    void persist_whenNameIsTooLong_thenThrows() {
        jpa.setName(DATA.nameTooLong());

        Executable persistAndFlushExecutable = getPersistAndFlushExecutable(jpa);

        assertThrowsDataException(persistAndFlushExecutable);

    }


    @Test
    void persist_whenDescriptionIsNull_noProblem() {

        persistAndFlush(jpaWithUser);


    }

    @Test
    void persist_whenTooLongDescription_thenThrows() {
        String tooLong = DATA.descriptionTooLong();
        jpaWithUser.setDescription(tooLong);

        Executable persistAndFlushExecutable = getPersistAndFlushExecutable(jpaWithUser);

        assertThrowsDataException(persistAndFlushExecutable);
    }

    @Test
    void persist_whenNoLabelColor_noProblem() {
        jpaWithUser.setLabelColor(null);

        persistAndFlush(jpaWithUser);
    }


    @Test
    void findOwnerByOf_whenFound_thenReturnsOwnerId() {
        UserId ownerId = UserId.of(jpaWithUser.getOwnerId());
        FolderId folderId = FolderId.of(jpaWithUser.getId());
        persistAndFlush(jpaWithUser);

        UserId foundOwnerId = adapter.findOwnerOf(folderId);

        assertEquals(ownerId, foundOwnerId);

    }


    @Test
    void findByOwnerOf_thenFolderNotFound_thenThrowsFolderNotFound() {
        FolderId random = FolderId.random();

        assertThrows(FolderNotFoundException.class, () -> adapter.findOwnerOf(random));
    }

    private @NotNull Executable getPersistAndFlushExecutable(FolderEntity jpa) {
        return () -> persistAndFlush(jpa);
    }


    private void assertThrowsDataException(Executable executable) {
        assertThrows(DataException.class, executable);
    }


    private static void assertThrowsConstraintViolationException(Executable exec) {
        assertThrows(ConstraintViolationException.class, exec);
    }

    private void persistAndFlush(FolderEntity jpa) {
        entityManager.persist(jpa);
        entityManager.flush();
    }


}
