package com.cccdlabs.sample.data.storage.dao;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.data.entity.DoodadEntity;
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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application=TestApp.class)
public class DoodadDaoTest {

    private static final String WIDGET_UUID =  "be50d7f6-bf8c-4bad-9869-56990737d1a2";
    private static final String GIZMO_UUID =  "4e44eaaa-aecf-4d54-9379-ec4c80579a5b";

    private AppDatabase mDb;
    private DoodadDao mDao;
    private DoodadEntity entity1;
    private DoodadEntity entity2;
    private DoodadEntity entity3;
    private DoodadEntity entity4;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        TestDataComponent dataComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext, true))
                .build();

        mDb = dataComponent.appDatabase();
        mDao = dataComponent.doodadDao();

        entity1 = new DoodadEntity();
        entity1.setUuid("185276b0-84bf-41b4-ab5f-8681d8d72186");
        entity1.setWidgetUuid(WIDGET_UUID);
        entity1.setName("DoodadEntity One");
        entity1.setDescription("This little piggy went to market");
        entity1.touch();

        entity2 = new DoodadEntity();
        entity2.setUuid("f417e177-c340-439c-9502-082abf53e444");
        entity2.setWidgetUuid(WIDGET_UUID);
        entity2.setName("DoodadEntity Two");
        entity2.setDescription("This little piggy stayed home");
        entity2.touch();

        entity3 = new DoodadEntity();
        entity3.setUuid("3ec19c59-c127-4c62-a353-7de551dfb382");
        entity3.setWidgetUuid(WIDGET_UUID);
        entity3.setName("DoodadEntity Three");
        entity3.setDescription("This little piggy ate roast beef");
        entity3.touch();

        entity4 = new DoodadEntity();
        entity4.setUuid("55bad4ee-12af-45bc-a0da-9b2b711fd787");
        entity4.setWidgetUuid(WIDGET_UUID);
        entity4.setName("DoodadEntity Four");
        entity4.setDescription("I forgot what the last one did");
        entity4.touch();

        // Add a gizmo so we don't get an SQL foreign key error
        GizmoEntity gizmo = new GizmoEntity();
        gizmo.setUuid(GIZMO_UUID);
        gizmo.setName("Gizmo One");
        gizmo.setDescription("One, two, three");
        gizmo.touch();
        dataComponent.gizmoDao().insert(gizmo);

        // Then add a Widget so we don't get an SQL foreign key error
        WidgetEntity widget = new WidgetEntity();
        widget.setUuid(WIDGET_UUID);
        widget.setGizmoUuid(GIZMO_UUID);
        widget.setName("Widget One");
        widget.setDescription("Just one widget for here");
        widget.touch();
        dataComponent.widgetDao().insert(widget);
    }

    @Test
    public void shouldInsertAndGetFromId() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        DoodadEntity entity = mDao.fromId((int) id);
        assertNotNull("Entity after fromId(id)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldInsertAndGetFromUuid() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        DoodadEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);
        assertEntitiesEqual(entity1, entity);
    }

    @Test
    public void shouldUpdate() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        DoodadEntity entity = mDao.fromUuid(entity1.getUuid());
        assertNotNull("Entity after fromUuid(uuid)", entity);

        entity.setName("DoodadEntity One point One");
        entity.setDescription("No more piggies");
        entity.touch(); // update timestamps
        int count = mDao.update(entity);

        assertEquals("updateCount not 1", 1, count);

        DoodadEntity entity2 = mDao.fromUuid(entity1.getUuid());
        assertEntitiesEqual(entity, entity2);
    }

    @Test
    public void shouldInsertMultipleAndGetAll() throws Exception {
        DoodadEntity[] entities = new DoodadEntity[]{entity1, entity2, entity3, entity4};
        long[] ids = mDao.insert(entities);
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<DoodadEntity> entityList = mDao.getAll();
        assertNotNull("List<DoodadEntity> after getAll() null", entityList);
        assertEquals("List<DoodadEntity> after getAll() count incorrect", 4, entityList.size());

        // entities can be returned in any order so we'll use a loop,
        // check each matching UUID and compare to the corresponding entity
        String uuid;
        for (DoodadEntity entity : entityList) {
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
        long[] ids = mDao.insert(new DoodadEntity[]{entity1, entity2, entity3, entity4});
        assertTrue("IDs count is zero", ids.length > 0);
        for (int i=0; i < ids.length; i++) {
            assertTrue("ID[" + i + "] is zero", ids[i] > 0);
        }

        List<DoodadEntity> entities = mDao.getAll();
        assertNotNull("List<DoodadEntity> after getAll() null", entities);
        assertEquals("List<DoodadEntity> after getAll() count incorrect", 4, entities.size());

        // TEST delete(entity)
        DoodadEntity entity = entities.get(0);
        int count = mDao.delete(entity);
        assertEquals("deleteCount[entity] not 1", 1, count);

        // TEST delete(uuid)
        entity = entities.get(1);
        String uuid = entity.getUuid();
        assertNotNull("Entity for DELETE by UUID null", entity);
        count = mDao.delete(uuid);
        assertEquals("deleteCount[UUID] not 1", 1, count);

        // TEST delete(entity[]...)
        DoodadEntity[] entitiesArr = new DoodadEntity[]{entities.get(2), entities.get(3)};
        count = mDao.delete(entitiesArr);
        assertEquals("deleteCount[entity[]...] not 2", 2, count);

        // TEST getAll() returns empty List after deletes
        entities = mDao.getAll();
        assertNotNull("List<DoodadEntity> after getAll() null", entities);
        assertEquals("List<DoodadEntity> after getAll() count incorrect", 0, entities.size());
    }

    @Test
    public void shouldSetUpdatedAt() throws Exception {
        long id = mDao.insert(entity1);
        assertTrue("ID is zero", id > 0);

        DoodadEntity entity = mDao.fromUuid(entity1.getUuid());
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

    private void assertEntitiesEqual(DoodadEntity entity1, DoodadEntity entity2) {
        assertNotNull("Entity 1 null", entity1);
        assertNotNull("Entity 2 null", entity2);
        assertEquals("UUIDs not equal", entity1.getUuid(), entity2.getUuid());
        assertEquals("Widget UUIDs not equal", entity1.getWidgetUuid(), entity2.getWidgetUuid());
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
