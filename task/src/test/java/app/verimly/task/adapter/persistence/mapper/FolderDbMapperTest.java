package app.verimly.task.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.persistence.entity.FolderEntity;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.domain.entity.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {FolderDbMapperImpl.class, CoreVoMapperImpl.class})
class FolderDbMapperTest {

    @Autowired
    FolderDbMapper mapper;

    private static final FolderTestData DATA = FolderTestData.getInstance();

    private Folder folder;

    @BeforeEach
    void setup() {
        folder = DATA.folder();
    }

    @Test
    void should_setup_is_ok() {
        assertNotNull(mapper);
    }

    @Test
    void toJpaEntity_whenNullArgument_thenReturnsNull() {
        folder = null;

        FolderEntity actual = mapper.toJpaEntity(folder);

        assertNull(actual);
    }

    @Test
    void toJpaEntity_whenFolderWithAllNullFields_thenReturnJpaEntityWithAllFieldsNull() {
        folder = DATA.folderWithNullFields();

        FolderEntity actual = mapper.toJpaEntity(folder);

        assertNull(actual.getId());
        assertNull(actual.getOwnerId());
        assertNull(actual.getName());
        assertNull(actual.getDescription());
        assertNull(actual.getLabelColor());
    }


    @Test
    void toJpaEntity_withValidFolder_thenMapsToJpaEntity() {
        FolderEntity actual = mapper.toJpaEntity(folder);

        assertEquals(folder.getId().getValue(), actual.getId());
        assertEquals(folder.getOwnerId().getValue(), actual.getOwnerId());
        assertEquals(folder.getName().getValue(), actual.getName());
        assertEquals(folder.getDescription().getValue(), actual.getDescription());
        assertEquals(folder.getLabelColor().getValue(), actual.getLabelColor());
    }


    @Test
    void toDomainEntity_whenNullJpa_thenReturnNull() {
        FolderEntity jpa = null;

        Folder domainEntity = mapper.toDomainEntity(jpa);

        assertNull(domainEntity);


    }

    @Test
    void toDomainEntity_whenValidJpa_thenMapsToDomain() {
        FolderEntity jpa = mapper.toJpaEntity(folder);

        Folder domainEntity = mapper.toDomainEntity(jpa);

        assertEquals(jpa.getId(), domainEntity.getId().getValue());
        assertEquals(jpa.getOwnerId(), domainEntity.getOwnerId().getValue());
        assertEquals(jpa.getName(), domainEntity.getName().getValue());
        assertEquals(jpa.getDescription(), domainEntity.getDescription().getValue());
        assertEquals(jpa.getLabelColor(), domainEntity.getLabelColor().getValue());


    }
}