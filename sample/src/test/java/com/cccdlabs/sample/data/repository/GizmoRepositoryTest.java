package com.cccdlabs.sample.data.repository;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.cccdlabs.sample.TestApp;
import com.cccdlabs.sample.data.entity.WidgetEntity;
import com.cccdlabs.sample.data.storage.database.AppDatabase;
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
 * Unit test for GizmoRepository class.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application= TestApp.class)
public class GizmoRepositoryTest {

    private TestDataComponent mComponent;
    private AppDatabase mDb;
    private GizmoRepository mRepository;
    private Gizmo model1;
    private Gizmo model2;
    private Gizmo model3;

    private List<WidgetEntity> widgetEntities;
    private int count = 0;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        mComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext, true))
                .build();

        mDb = mComponent.appDatabase();
        mRepository = new GizmoRepository();
        mComponent.inject(mComponent.widgetMapper());
        mComponent.inject(mComponent.gizmoMapper());
        mComponent.inject(mComponent.widgetRepository());
        mComponent.inject(mRepository);

        model1 = new Gizmo();
        model1.setUuid("775ae963-603d-460e-8ca4-f65fa39b4ed3");
        model1.setName("Gizmo One");
        model1.setDescription("Whooptee doo city");

        model2 = new Gizmo();
        model2.setUuid("bf401b34-c7d3-446c-b010-40f034746495");
        model2.setName("Gizmo Two");
        model2.setDescription("Whooptee doo pueblo");

        model3 = new Gizmo();
        model3.setUuid("eb17c902-c2dd-47bf-b8b8-02206fc537c3");
        model3.setName("Gizmo Three");
        model3.setDescription("Whooptee doo and don't die");

        // Initialize the widgets
        widgetEntities = new ArrayList<>();

        WidgetEntity doodad = new WidgetEntity();
        doodad.setUuid("7ba3dc62-7dfa-4f86-9ba3-6d48b0a82055");
        doodad.setName("Widget One");
        doodad.setDescription("One for the sun");
        widgetEntities.add(doodad);

        doodad = new WidgetEntity();
        doodad.setUuid("36584ec4-0d31-4a88-b64f-40cebc5f2210");
        doodad.setName("Widget Two");
        doodad.setDescription("Two for the road");
        widgetEntities.add(doodad);

        doodad = new WidgetEntity();
        doodad.setUuid("5f5d7587-4e31-43fb-aba1-86629b1f37b5");
        doodad.setName("Widget Three");
        doodad.setDescription("Three to get ready");
        widgetEntities.add(doodad);
    }

    @Test
    public void shouldInsertAndGetByIdAndUuid() throws Exception {
        // TEST insert(model)
        int id = mRepository.insert(model1);

        // TEST getById(id)
        Gizmo model = mRepository.getById(id);
        assertNotNull("Model after getById(id)", model);
        assertModelsEqual(model1, model);

        // TEST update(model)
        model.setName("GizmoEntity One point One");
        model.setDescription("Whooptee doo daa");
        int count = mRepository.update(model);
        assertEquals("updateCount not 1", 1, count);

        // TEST getByUuid(uuid)
        Gizmo updated = mRepository.getByUuid(model.getUuid());
        assertNotNull("Model after getByUuid(uuid)", updated);
        assertModelsEqual(model, updated);
    }

    @Test
    public void shouldInsertAndGetAll() throws Exception {
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);

        List<Gizmo> models = mRepository.getAll();
        assertNotNull("List<Gizmo> after getAll() null", models);
        assertEquals("List<Gizmo> after getAll() count incorrect", 3, models.size());

        // entities can be returned in any order so we'll use a loop,
        // check each matching UUID and compare to the corresponding entity
        String uuid;
        for (Gizmo m : models) {
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

        List<Gizmo> models = mRepository.getAll();
        assertNotNull("List<Gizmo> after getAll() null", models);

        // TEST delete(model)
        Gizmo model = models.get(0);
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
        assertNotNull("List<Gizmo> after getAll() null", models);
        assertEquals("List<Gizmo> after getAll() count incorrect", 0, models.size());
    }

    @Test
    public void shouldDeleteByModelList() throws Exception {
        int id = mRepository.insert(model1);
        model1.setId(id);
        id = mRepository.insert(model2);
        model2.setId(id);
        id = mRepository.insert(model3);
        model3.setId(id);
        List<Gizmo> list = new ArrayList<>(3);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        int count = mRepository.delete(list);
        assertEquals("deleteCount[List<Gizmo>] not 3", 3, count);

        // TEST getAll() returns empty List after deletes
        List<Gizmo> models = mRepository.getAll();
        assertNotNull("List<Gizmo> after delete(List<Gizmo>) null", models);
        assertEquals("List<Gizmo> after delete(List<Gizmo>) count incorrect", 0, models.size());
    }

    @Test
    public void shouldAttachWidgets() throws Exception {
        mRepository.insert(model1);
        mRepository.insert(model2);
        mRepository.insert(model3);
        insertWidget(model1.getUuid()); // insert
        insertWidget(model2.getUuid()); // relational
        insertWidget(model3.getUuid()); // widgets

        // TEST attachWidgets(model)
        Gizmo model = mRepository.getByUuid(model1.getUuid());
        assertNotNull("Gizmo after getByUuid() null", model);
        model = mRepository.attachWidgets(model);
        List<Widget> widgets = model.getWidgets();
        assertNotNull("attachWidgets(model) null", widgets);
        assertEquals("attachWidgets(model) size not 1", 1, widgets.size());

        // TEST attachWidgets(List)
        List<Gizmo> models = mRepository.getAll();
        assertNotNull("List<Gizmo> after getAll() null", models);
        models = mRepository.attachWidgets(models);
        for (Gizmo m : models) {
            widgets = m.getWidgets();
            assertNotNull("attachWidgets(List) [UUID: " + m.getUuid() + "] widgets list null", widgets);
            assertEquals("attachWidgets(List) size not 1", 1, widgets.size());
        }
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }

    private void assertModelsEqual(Gizmo model1, Gizmo model2) {
        assertNotNull("Gizmo 1 null", model1);
        assertNotNull("Gizmo 2 null", model1);
        assertEquals("UUIDs not equal", model1.getUuid(), model2.getUuid());
        assertEquals("name not equal", model1.getName(), model2.getName());
        assertEquals("description not equal", model1.getDescription(), model2.getDescription());
        assertNotNull("createdAt null value", model2.getCreatedAt());
        assertNotNull("updatedAt null value", model2.getUpdatedAt());
    }

    private void insertWidget(String gizmoUuid) {
        if (gizmoUuid == null || (count + 1) > widgetEntities.size()) {
            return;
        }

        WidgetEntity widget = widgetEntities.get(count);
        widget.setGizmoUuid(gizmoUuid);
        mComponent.widgetDao().insert(widget);
        count++;
    }
}