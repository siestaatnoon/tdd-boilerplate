package com.oscarrrweb.tddboilerplate.data.mappers.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.GizmoEntity;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GizmoMapperTest {

    private static final String GIZMO_UUID_1 =  "589a0b6e-5ce8-4759-944f-81f993b7694e";
    private static final String GIZMO_UUID_2 =  "86261454-f597-4586-8506-192ac6ddd471";

    private GizmoMapper mMapper;
    private GizmoEntity entity1;
    private GizmoEntity entity2;

    @Before
    public void setUp() throws Exception {
        mMapper = new GizmoMapper();

        entity1 = new GizmoEntity();
        entity1.setId(1);
        entity1.setUuid(GIZMO_UUID_1);
        entity1.setName("Gizmo One");
        entity1.setDescription("Old MacDonald had a farm.");
        entity1.touch();

        entity2 = new GizmoEntity();
        entity2.setId(2);
        entity2.setUuid(GIZMO_UUID_2);
        entity2.setName("Gizmo Two");
        entity2.setDescription("Then sold hamburgers or something.");
        entity2.touch();
    }

    @Test
    public void WidgetMapper_shouldConvertEntityToDomainAndBack() {
        // entity model -> domain model
        Gizmo model = mMapper.toDomainModel(entity1);
        assertNotNull("Converted domain object null", model);
        assertEqualValues(entity1, model);

        // domain model -> entity model
        entity1 = mMapper.fromDomainModel(model);
        assertNotNull("Converted entity object null", entity1);
        assertEqualValues(entity1, model);
    }

    @Test
    public void WidgetMapper_shouldConvertEntityToDomainListAndBack() {
        List<GizmoEntity> entityList = new ArrayList<>(2);
        entityList.add(entity1);
        entityList.add(entity2);

        // List<entity> -> List<domain>
        List<Gizmo> domainList = mMapper.toDomainModel(entityList);
        assertNotNull("Converted entity List null", domainList);
        assertEquals("Original entity and converted domain List are not equal size", entityList.size(), domainList.size());
        for (int i=0; i < entityList.size(); i++) {
            assertEqualValues(entityList.get(i), domainList.get(i));
        }

        // List<domain> -> List<entity>
        entityList = mMapper.fromDomainModel(domainList);
        assertNotNull("Converted domain List null", domainList);
        assertEquals("Original domain and converted entity List are not equal size", domainList.size(), entityList.size());
        for (int i=0; i < entityList.size(); i++) {
            assertEqualValues(entityList.get(i), domainList.get(i));
        }
    }

    @After
    public void tearDown() throws Exception {
        mMapper = null;
    }

    private void assertEqualValues(GizmoEntity entity, Gizmo model) {
        assertNotNull("Entity null", entity);
        assertNotNull("Model null", model);
        assertEquals("IDs not equal", entity.getId(), model.getId());
        assertEquals("UUIDs not equal", entity.getUuid(), model.getUuid());
        assertEquals("name not equal", entity.getName(), model.getName());
        assertEquals("description not equal", entity.getDescription(), model.getDescription());

        assertNotNull("Entity getCreatedAt null", entity.getCreatedAt());
        assertNotNull("Model getCreatedAt null", model.getCreatedAt());
        assertEquals("createdAt not equal", entity.getCreatedAt().getTime(), model.getCreatedAt().getTime());

        assertNotNull("Entity getUpdatedAt null", entity.getUpdatedAt());
        assertNotNull("Model getUpdatedAt null", model.getUpdatedAt());
        assertEquals("updatedAt not equal", entity.getUpdatedAt().getTime(), model.getUpdatedAt().getTime());
    }
}
