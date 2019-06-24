package com.cccdlabs.sample.data.storage.dao;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.data.entity.GizmoEntity;
import com.cccdlabs.sample.data.entity.WidgetEntity;
import com.cccdlabs.sample.data.storage.database.AppDatabase;
import com.cccdlabs.sample.presentation.di.components.DaggerTestDataComponent;
import com.cccdlabs.sample.presentation.di.components.TestDataComponent;
import com.cccdlabs.sample.presentation.di.modules.TestDataModule;
import com.cccdlabs.tddboilerplate.data.utils.DateUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application=TestApp.class)
public class WidgetDaoTest {

    private static final String GIZMO_UUID =  "be50d7f6-bf8c-4bad-9869-56990737d1a2";
    private static final String GIZMO_UUID_2 =  "8ff6ade9-396b-4503-a038-99a372ccf023";

    private AppDatabase mDb;
    private WidgetDao mDao;
    private WidgetEntity entity1;
    private WidgetEntity entity2;
    private WidgetEntity entity3;
    private WidgetEntity entity4;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        TestDataComponent dataComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext, true))
                .build();

        mDb = dataComponent.appDatabase();
        mDao = dataComponent.widgetDao();

        entity1 = new WidgetEntity();
        entity1.setUuid("589a0b6e-5ce8-4759-944f-81f993b7694e");
        entity1.setGizmoUuid(GIZMO_UUID);
        entity1.setName("WidgetEntity One");
        entity1.setDescription("Whooptee doo");
        entity1.touch();

        entity2 = new WidgetEntity();
        entity2.setUuid("86261454-f597-4586-8506-192ac6ddd471");
        entity2.setGizmoUuid(GIZMO_UUID);
        entity2.setName("WidgetEntity Two");
        entity2.setDescription("Whooptee doo too");
        entity2.touch();

        entity3 = new WidgetEntity();
        entity3.setUuid("ac47372a-8589-4139-98ef-1559c28d974a");
        entity3.setGizmoUuid(GIZMO_UUID);
        entity3.setName("WidgetEntity Three");
        entity3.setDescription("Whooptee doo too three");
        entity3.touch();

        entity4 = new WidgetEntity();
        entity4.setUuid("98021546-5437-40ef-8f3e-5f0afae2f564");
        entity4.setGizmoUuid(GIZMO_UUID_2);
        entity4.setName("WidgetEntity Four");
        entity4.setDescription("Four is the chore");
        entity4.touch();

        // Add the gizmos so we don't get an SQL foreign key error
        GizmoEntity gizmo = new GizmoEntity();
        gizmo.setUuid(GIZMO_UUID);
        gizmo.setName("Gizmo One");
        gizmo.setDescription("One, two, three");
        dataComponent.gizmoDao().insert(gizmo);

        gizmo = new GizmoEntity();
        gizmo.setUuid(GIZMO_UUID_2);
        gizmo.setName("Gizmo Two");
        gizmo.setDescription("Four, five, six");
        dataComponent.gizmoDao().insert(gizmo);
    }

    @Test
    public void shouldInsertAndGetFromId() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        WidgetEntity entity = mDao.fromId((int) id);
        assertNotNull("Entity after fromId(id)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldInsertAndGetFromUuid() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        WidgetEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldGetByGizmo() throws Exception {
        mDao.insert(new WidgetEntity[]{entity1, entity2, entity3, entity4});

        List<WidgetEntity> entities = mDao.getByGizmo(GIZMO_UUID);
        assertNotNull("List<WidgetEntity> after getByGizmo(uuid) null", entities);
        assertEquals("List<WidgetEntity> after getByGizmo(uuid) count incorrect", 3, entities.size());
    }

    @Test
    public void shouldGetByGizmos() throws Exception {
        mDao.insert(new WidgetEntity[]{entity1, entity2, entity3, entity4});

        List<String> uuids = new ArrayList<>(1);
        uuids.add(GIZMO_UUID);
        List<WidgetEntity> entities = mDao.getByGizmos(uuids);
        assertNotNull("List<WidgetEntity> after getByGizmo(List<String>) null", entities);
        assertEquals("List<WidgetEntity> after getByGizmo(List<String>) count incorrect", 3, entities.size());
    }

    @Test
    public void shouldUpdate() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        WidgetEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);

        entity.setName("WidgetEntity One point One");
        entity.setDescription("Whooptee doo daa");
        entity.touch(); // update timestamps
        int count = mDao.update(entity);

        assertEquals("updateCount not 1", 1, count);

        WidgetEntity entity2 = mDao.fromUuid(entity1.getUuid());
        assertEntitiesEqual(entity, entity2);
    }

    @Test
    public void shouldInsertMultipleAndGetAll() throws Exception {
        WidgetEntity[] entities = new WidgetEntity[]{entity1, entity2, entity3, entity4};
        long[] ids = mDao.insert(entities);
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<WidgetEntity> entityList = mDao.getAll();
        assertNotNull("List<WidgetEntity> after getAll() null", entityList);
        assertEquals("List<WidgetEntity> after getAll() count incorrect", 4, entityList.size());

        // entities can be returned in any order so we'll use a loop,
        // check each matching UUID and compare to the corresponding entity
        String uuid;
        for (WidgetEntity entity : entityList) {
            uuid = entity.getUuid();
            if (uuid.equals(entity1.getUuid())) {
                assertEntitiesEqual(entity1, entity);
            } else if (uuid.equals(entity2.getUuid())) {
                assertEntitiesEqual(entity2, entity);
            } else if (uuid.equals(entity3.getUuid())) {
                assertEntitiesEqual(entity3, entity);
            } else if (uuid.equals(entity4.getUuid())) {
                assertEntitiesEqual(entity4, entity);
            } else {
                fail("Invalid UUID [" + uuid + "]");
            }
        }
    }

    @Test
    public void shouldDeleteByEntityAndUuidAndArray() throws Exception {
        long[] ids = mDao.insert(new WidgetEntity[]{entity1, entity2, entity3, entity4});
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<WidgetEntity> entities = mDao.getAll();
        assertNotNull("List<WidgetEntity> after getAll() null", entities);
        assertEquals("List<WidgetEntity> after getAll() count incorrect", 4, entities.size());

        // TEST delete(entity)
        WidgetEntity entity = entities.get(0);
        int count = mDao.delete(entity);
        assertEquals("deleteCount[entity] not 1", 1, count);

        // TEST delete(uuid)
        entity = entities.get(1);
        String uuid = entity.getUuid();
        assertNotNull("Entity for DELETE by UUID null", entity);
        count = mDao.delete(uuid);
        assertEquals("deleteCount[UUID] not 1", 1, count);

        // TEST delete(entity[]...)
        WidgetEntity[] entitiesArr = new WidgetEntity[]{entities.get(2), entities.get(3)};
        count = mDao.delete(entitiesArr);
        assertEquals("deleteCount[entity[]...] not 2", 2, count);

        // TEST getAll() returns empty List after deletes
        entities = mDao.getAll();
        assertNotNull("List<WidgetEntity> after getAll() null", entities);
        assertEquals("List<WidgetEntity> after getAll() count incorrect", 0, entities.size());
    }

    @Test
    public void shouldSetUpdatedAt() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        WidgetEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);
        assertEntitiesEqual(entity1, entity);

        // TEST setUpdatedAt(uuid, updatedAt)
        long newTime = entity.getUpdatedAt().getTime() + 2000;
        Date updatedAt = new Date(newTime);
        mDao.setUpdatedAt(entity.getUuid(), DateUtils.dateToSqlString(updatedAt));
        entity = mDao.fromUuid(entity.getUuid());
        assertNotNull("Entity after setUpdatedAt(uuid, updatedAt) null", entity);
        assertEquals("updatedAt timestamp incorrect", newTime, entity.getUpdatedAt().getTime());
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    private void assertEntitiesEqual(WidgetEntity entity1, WidgetEntity entity2) {
        assertNotNull("Entity 1 null", entity1);
        assertNotNull("Entity 2 null", entity2);
        assertEquals("UUIDs not equal", entity1.getUuid(), entity2.getUuid());
        assertEquals("Gizmo UUIDs not equal", entity1.getGizmoUuid(), entity2.getGizmoUuid());
        assertEquals("name not equal", entity1.getName(), entity2.getName());
        assertEquals("description not equal", entity1.getDescription(), entity2.getDescription());

        assertNotNull("Entity 1 getCreatedAt null", entity1.getCreatedAt());
        assertNotNull("Entity 2 getCreatedAt null", entity2.getCreatedAt());
        assertEquals(
                "createdAt not equal",
                DateUtils.dateToSqlString(entity1.getCreatedAt()),
                DateUtils.dateToSqlString(entity2.getCreatedAt())
        );

        assertNotNull("Entity 1 getUpdatedAt null", entity1.getUpdatedAt());
        assertNotNull("Entity 2 getUpdatedAt null", entity2.getUpdatedAt());
        assertEquals(
                "updatedAt not equal",
                DateUtils.dateToSqlString(entity1.getUpdatedAt()),
                DateUtils.dateToSqlString(entity2.getUpdatedAt())
        );
    }
}
