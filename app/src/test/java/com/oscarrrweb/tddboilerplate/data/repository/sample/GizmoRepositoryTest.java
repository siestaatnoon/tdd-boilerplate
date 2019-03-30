package com.oscarrrweb.tddboilerplate.data.repository.sample;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.TestApp;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerTestDataComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.TestDataComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit test for GizmoRepository class. While trying various configurations including
 * {@link @BeforeClass} and {@link @AfterClass}, it was decided that, for best performance,
 * a single test would be best although not very elegant. Initializing database connections
 * can get pretty expensive.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=28, application= TestApp.class)
public class GizmoRepositoryTest {

    private AppDatabase mDb;
    private GizmoRepository mRepository;
    private Gizmo model1;
    private Gizmo model2;
    private Gizmo model3;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        TestDataComponent dataComponent = DaggerTestDataComponent.builder()
                .testDataModule(new TestDataModule(appContext))
                .build();

        mDb = dataComponent.appDatabase();
        mRepository = new GizmoRepository();
        dataComponent.inject(mRepository);

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
    }

    @Test
    public void GizmoRepository_shouldPerformAllFuntions() throws Exception {
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

        // TEST getAll()
        model1 = model;
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

        // TEST delete(model)
        model = models.get(0);
        count = mRepository.delete(model);
        assertEquals("deleteCount[model] not 1", 1, count);

        // TEST delete(id)
        model = models.get(1);
        id = model.getId();
        assertNotNull("Model for DELETE by ID null", model);
        count = mRepository.delete(id);
        assertEquals("deleteCount[ID] not 1", 1, count);

        // TEST delete(uuid)
        model = models.get(2);
        uuid = model.getUuid();
        assertNotNull("Model for DELETE by UUID null", model);
        count = mRepository.delete(uuid);
        assertEquals("deleteCount[UUID] not 1", 1, count);

        // TEST getAll() returns empty List after deletes
        models = mRepository.getAll();
        assertNotNull("List<Gizmo> after getAll() null", models);
        assertEquals("List<Gizmo> after getAll() count incorrect", 0, models.size());
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
}