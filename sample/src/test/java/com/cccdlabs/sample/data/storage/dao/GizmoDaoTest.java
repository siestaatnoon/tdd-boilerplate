package com.cccdlabs.sample.data.storage.dao;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.data.entity.GizmoEntity;
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
public class GizmoDaoTest {

    private AppDatabase mDb;
    private GizmoDao mDao;
    private GizmoEntity entity1;
    private GizmoEntity entity2;
    private GizmoEntity entity3;
    private GizmoEntity entity4;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        TestDataComponent dataComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext, true))
                .build();

        mDb = dataComponent.appDatabase();
        mDao = dataComponent.gizmoDao();

        entity1 = new GizmoEntity();
        entity1.setUuid("c59405a8-2e50-4388-a151-6fa3a5be4700");
        entity1.setName("GizmoEntity One");
        entity1.setDescription("Something to describe");
        entity1.touch();

        entity2 = new GizmoEntity();
        entity2.setUuid("5a3b7075-f7d6-4bea-8b06-8473da3141e1");
        entity2.setName("GizmoEntity Two");
        entity2.setDescription("NOT a real entity");
        entity2.touch();

        entity3 = new GizmoEntity();
        entity3.setUuid("836c319b-f3cf-4773-8b62-2c53ea5b7c70");
        entity3.setName("GizmoEntity Three");
        entity3.setDescription("Oh, how I love being a Gizmo");
        entity3.touch();

        entity4 = new GizmoEntity();
        entity4.setUuid("3d802cfb-2c9e-48c8-9bcd-540e490cee3f");
        entity4.setName("GizmoEntity Four");
        entity4.setDescription("Well, that was quick");
        entity4.touch();
    }

    @Test
    public void shouldInsertAndGetFromId() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        GizmoEntity entity = mDao.fromId((int) id);
        assertNotNull("Entity after fromId(id)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldInsertAndGetFromUuid() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        GizmoEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldGetFromUuids() throws Exception {
        mDao.insert(new GizmoEntity[]{entity1, entity2});

        List<String> uuids = new ArrayList<>(2);
        uuids.add(entity1.getUuid());
        uuids.add(entity2.getUuid());
        List<GizmoEntity> entityList = mDao.fromUuids(uuids);

        assertNotNull("Entity after fromUuids(List)", entityList);
        assertEquals("List<GizmoEntity> after fromUuids(List) count incorrect", 2, entityList.size());
        assertEntitiesEqual(entity1, entityList.get(0));
        assertEntitiesEqual(entity2, entityList.get(1));
    }

    @Test
    public void shouldGetForList() throws Exception {
        mDao.insert(new GizmoEntity[]{entity1, entity2, entity3, entity4});
        List<GizmoDao.ListItem> listItems = mDao.getForList();

        assertNotNull("List<GizmoDao.ListItem> after getForList() null", listItems);
        assertEquals("List<GizmoDao.ListItem> after getForList() count incorrect", 4, listItems.size());
    }

    @Test
    public void shouldUpdate() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        GizmoEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);

        entity.setName("GizmoEntity One point One");
        entity.setDescription("No more piggies");
        entity.touch(); // update timestamps
        int count = mDao.update(entity);

        assertEquals("updateCount not 1", 1, count);

        GizmoEntity entity2 = mDao.fromUuid(entity1.getUuid());
        assertEntitiesEqual(entity, entity2);
    }

    @Test
    public void shouldInsertMultipleAndGetAll() throws Exception {
        GizmoEntity[] entities = new GizmoEntity[]{entity1, entity2, entity3, entity4};
        long[] ids = mDao.insert(entities);
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<GizmoEntity> entityList = mDao.getAll();
        assertNotNull("List<GizmoEntity> after getAll() null", entityList);
        assertEquals("List<GizmoEntity> after getAll() count incorrect", 4, entityList.size());

        // entities can be returned in any order so we'll use a loop,
        // check each matching UUID and compare to the corresponding entity
        String uuid;
        for (GizmoEntity entity : entityList) {
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
        long[] ids = mDao.insert(new GizmoEntity[]{entity1, entity2, entity3, entity4});
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<GizmoEntity> entities = mDao.getAll();
        assertNotNull("List<GizmoEntity> after getAll() null", entities);
        assertEquals("List<GizmoEntity> after getAll() count incorrect", 4, entities.size());

        // TEST delete(entity)
        GizmoEntity entity = entities.get(0);
        int count = mDao.delete(entity);
        assertEquals("deleteCount[entity] not 1", 1, count);

        // TEST delete(uuid)
        entity = entities.get(1);
        String uuid = entity.getUuid();
        assertNotNull("Entity for DELETE by UUID null", entity);
        count = mDao.delete(uuid);
        assertEquals("deleteCount[UUID] not 1", 1, count);

        // TEST delete(entity[]...)
        GizmoEntity[] entitiesArr = new GizmoEntity[]{entities.get(2), entities.get(3)};
        count = mDao.delete(entitiesArr);
        assertEquals("deleteCount[entity[]...] not 2", 2, count);

        // TEST getAll() returns empty List after deletes
        entities = mDao.getAll();
        assertNotNull("List<GizmoEntity> after getAll() null", entities);
        assertEquals("List<GizmoEntity> after getAll() count incorrect", 0, entities.size());
    }

    @Test
    public void shouldSetUpdatedAt() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        GizmoEntity entity = mDao.fromUuid(entity1.getUuid());
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

    private void assertEntitiesEqual(GizmoEntity entity1, GizmoEntity entity2) {
        assertNotNull("Entity 1 null", entity1);
        assertNotNull("Entity 2 null", entity2);
        assertEquals("UUIDs not equal", entity1.getUuid(), entity2.getUuid());
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
