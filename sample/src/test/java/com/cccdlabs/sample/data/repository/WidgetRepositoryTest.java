package com.cccdlabs.sample.data.repository;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.data.entity.DoodadEntity;
import com.cccdlabs.sample.data.entity.GizmoEntity;
import com.cccdlabs.sample.data.storage.database.AppDatabase;
import com.cccdlabs.sample.domain.model.Doodad;
import com.cccdlabs.sample.domain.model.Gizmo;
import com.cccdlabs.sample.domain.model.Widget;
import com.cccdlabs.sample.presentation.di.components.DaggerTestDataComponent;
import com.cccdlabs.sample.presentation.di.components.TestDataComponent;
import com.cccdlabs.sample.presentation.di.modules.TestDataModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit test for WidgetRepository class.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application= TestApp.class)
public class WidgetRepositoryTest {

    private static final String GIZMO_UUID_1 =  "be50d7f6-bf8c-4bad-9869-56990737d1a2";
    private static final String GIZMO_UUID_2 =  "da5be187-a7b4-43c2-9076-6a8a873bf180";

    private TestDataComponent mComponent;
    private AppDatabase mDb;
    private WidgetRepository mRepository;
    private Widget model1;
    private Widget model2;
    private Widget model3;

    private List<DoodadEntity> doodadEntities;
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        mComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext, true))
                .build();

        mDb = mComponent.appDatabase();
        mComponent.inject(mComponent.widgetMapper());
        mComponent.inject(mComponent.gizmoMapper());
        mRepository = new WidgetRepository();
        mComponent.inject(mRepository);

        model1 = new Widget();
        model1.setUuid("589a0b6e-5ce8-4759-944f-81f993b7694e");
        model1.setGizmoUuid(GIZMO_UUID_1);
        model1.setName("Widget One");
        model1.setDescription("Whooptee doo");

        model2 = new Widget();
        model2.setUuid("86261454-f597-4586-8506-192ac6ddd471");
        model2.setGizmoUuid(GIZMO_UUID_1);
        model2.setName("Widget Two");
        model2.setDescription("Whooptee doo too");

        model3 = new Widget();
        model3.setUuid("ac47372a-8589-4139-98ef-1559c28d974a");
        model3.setGizmoUuid(GIZMO_UUID_2);
        model3.setName("Widget Three");
        model3.setDescription("Whooptee doo too three");

        // Add the relational gizmos
        GizmoEntity gizmo = new GizmoEntity();
        gizmo.setUuid(GIZMO_UUID_1);
        gizmo.setName("Gizmo One");
        gizmo.setDescription("One, two, three");
        gizmo.touch();
        mComponent.gizmoDao().insert(gizmo);

        gizmo = new GizmoEntity();
        gizmo.setUuid(GIZMO_UUID_2);
        gizmo.setName("Gizmo Two");
        gizmo.setDescription("One, two, buckle my shoe");
        gizmo.touch();
        mComponent.gizmoDao().insert(gizmo);

        // Initialize the doodads
        doodadEntities = new ArrayList<>();

        DoodadEntity doodad = new DoodadEntity();
        doodad.setUuid("2e1fe525-0d07-49b5-bac6-91a9a35d5314");
        doodad.setName("Doodad One");
        doodad.setDescription("One for the sun");
        doodadEntities.add(doodad);

        doodad = new DoodadEntity();
        doodad.setUuid("7c284297-e959-438f-8843-e62524b737a5");
        doodad.setName("Doodad Two");
        doodad.setDescription("Two for the road");
        doodadEntities.add(doodad);

        doodad = new DoodadEntity();
        doodad.setUuid("ebddc083-29c9-4fee-95e2-703c79c157a4");
        doodad.setName("Doodad Three");
        doodad.setDescription("Three to get ready");
        doodadEntities.add(doodad);

    }

    @Test
    public void shouldInsertAndGetByIdAndUuid() throws Exception {
        // TEST insert(model)
        int id = mRepository.insert(model1);

        // TEST getById(id)
        Widget model = mRepository.getById(id);
        assertNotNull("Model after getById(id)", model);
        assertModelsEqual(model1, model);

        // TEST update(model)
        model.setName("WidgetEntity One point One");
        model.setDescription("Whooptee doo daa");
        int count = mRepository.update(model);
        assertEquals("updateCount not 1", 1, count);

        // TEST getByUuid(uuid)
        Widget updated = mRepository.getByUuid(model.getUuid());
        assertNotNull("Model after getByUuid(uuid)", updated);
        assertModelsEqual(model, updated);
    }

    @Test
    public void shouldInsertAndGetAll() throws Exception {
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);

        List<Widget> models = mRepository.getAll();
        assertNotNull("List<Widget> after getAll() null", models);
        assertEquals("List<Widget> after getAll() count incorrect", 3, models.size());

        // entities can be returned in any order so we'll use a loop,
        // check each matching UUID and compare to the corresponding entity
        String uuid;
        for (Widget m : models) {
            uuid = m.getUuid();
            if (uuid.equals(model1.getUuid())) {
                assertModelsEqual(model1, m);
            } else if (uuid.equals(model2.getUuid())) {
                assertModelsEqual(model2, m);
            } else if (uuid.equals(model3.getUuid())) {
                assertModelsEqual(model3, m);
            } else {
                fail("Invalid UUID [" + uuid + "]");
            }
        }
    }

    @Test
    public void shouldDeleteByModelAndIdAndUuid() throws Exception {
        // TEST getAll()
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);

        List<Widget> models = mRepository.getAll();
        assertNotNull("List<Widget> after getAll() null", models);

        // TEST delete(model)
        Widget model = models.get(0);
        int count = mRepository.delete(model);
        assertEquals("deleteCount[model] not 1", 1, count);

        // TEST delete(id)
        model = models.get(1);
        int id = model.getId();
        assertNotNull("Model for DELETE by ID null", model);
        count = mRepository.delete(id);
        assertEquals("deleteCount[ID] not 1", 1, count);

        // TEST delete(uuid)
        model = models.get(2);
        String uuid = model.getUuid();
        assertNotNull("Model for DELETE by UUID null", model);
        count = mRepository.delete(uuid);
        assertEquals("deleteCount[UUID] not 1", 1, count);

        // TEST getAll() returns empty List after deletes
        models = mRepository.getAll();
        assertNotNull("List<Widget> after getAll() null", models);
        assertEquals("List<Widget> after getAll() count incorrect", 0, models.size());
    }

    @Test
    public void shouldDeleteByModelList() throws Exception {
        int id = mRepository.insert(model1);
        model1.setId(id);
        id = mRepository.insert(model2);
        model2.setId(id);
        id = mRepository.insert(model3);
        model3.setId(id);
        List<Widget> list = new ArrayList<>(3);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        int count = mRepository.delete(list);
        assertEquals("deleteCount[List<Widget>] not 3", 3, count);

        // TEST getAll() returns empty List after deletes
        List<Widget> models = mRepository.getAll();
        assertNotNull("List<Widget> after delete(List<Widget>) null", models);
        assertEquals("List<Widget> after delete(List<Widget>) count incorrect", 0, models.size());
    }

    @Test
    public void shouldAttachGizmo() throws Exception {
        mRepository.insert(model1);
        Widget model = mRepository.getByUuid(model1.getUuid());

        assertNotNull("Model after getByUuid(uuid)", model);

        model = mRepository.attachGizmo(model);
        Gizmo gizmo = model.getGizmo();

        assertNotNull("attachGizmo(model) null", gizmo);
        assertEquals("attachGizmo(model) UUID not equal", GIZMO_UUID_1, gizmo.getUuid());
    }

    @Test
    public void shouldAttachDoodad() throws Exception {
        mRepository.insert(model1);
        insertDoodad(model1.getUuid());
        Widget model = mRepository.getByUuid(model1.getUuid());

        assertNotNull("Model after getByUuid(uuid)", model);

        model = mRepository.attachDoodads(model);
        List<Doodad> doodads = model.getDoodads();

        assertNotNull("attachDoodads(model) null", doodads);
        assertEquals("attachDoodads(model) size not 1", 1, doodads.size());
    }

    @Test
    public void shouldAttachGizmos() throws Exception {
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);
        List<Widget> models = mRepository.getAll();

        assertNotNull("List<Widget> after getAll() null", models);
        assertEquals("List<Widget> after getAll() count incorrect", 3, models.size());

        models = mRepository.attachGizmo(models);
        for (Widget m : models) {
            Gizmo gizmo = m.getGizmo();
            assertNotNull("attachGizmo(List) [UUID: " + m.getUuid() + "] Gizmo null", gizmo);
        }
    }

    @Test
    public void shouldAttachDoodads() throws Exception {
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);
        insertDoodad(model2.getUuid()); // insert
        insertDoodad(model3.getUuid()); // relational
        insertDoodad(model1.getUuid()); // doodads
        List<Widget> models = mRepository.getAll();

        assertNotNull("List<Widget> after getAll() null", models);
        assertEquals("List<Widget> after getAll() count incorrect", 3, models.size());

        models = mRepository.attachDoodads(models);
        for (Widget m : models) {
            List<Doodad> doodads = m.getDoodads();
            assertNotNull("attachDoodads(List) [UUID: " + m.getUuid() + "] doodads list null", doodads);
            assertEquals("attachDoodads(List) size not 1", 1, doodads.size());
        }
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    private void assertModelsEqual(Widget model1, Widget model2) {
        assertNotNull("Widget 1 null", model1);
        assertNotNull("Widget 2 null", model1);
        assertEquals("UUIDs not equal", model1.getUuid(), model2.getUuid());
        assertEquals("Gizmo UUIDs not equal", model1.getGizmoUuid(), model2.getGizmoUuid());
        assertEquals("name not equal", model1.getName(), model2.getName());
        assertEquals("description not equal", model1.getDescription(), model2.getDescription());
        assertNotNull("createdAt null value", model2.getCreatedAt());
        assertNotNull("updatedAt null value", model2.getUpdatedAt());
    }

    private void insertDoodad(String widgetUuid) {
        if (widgetUuid == null || (count + 1) > doodadEntities.size()) {
            return;
        }

        DoodadEntity doodad = doodadEntities.get(count);
        doodad.setWidgetUuid(widgetUuid);
        mComponent.doodadDao().insert(doodad);
        count++;
    }
}