package com.oscarrrweb.tddboilerplate.data.repository.sample;

import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.entity.sample.GizmoEntity;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.WidgetMapper;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.database.AppDatabase;
import com.oscarrrweb.tddboilerplate.domain.model.sample.Widget;
import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerTestApplicationComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.DaggerTestDataComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.TestApplicationComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.components.TestDataComponent;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestApplicationModule;
import com.oscarrrweb.tddboilerplate.presentation.di.modules.TestDataModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WidgetRepositoryTest {

    private AppDatabase mDb;
    private WidgetRepository mRepository;
    private WidgetDao mWidgetDao;
    private GizmoDao mGizmoDao;
    private DoodadDao mDoodadDao;
    private Widget widget1;
    private Widget widget2;
    private Widget widget3;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        TestApplicationComponent applicationComponent = DaggerTestApplicationComponent.builder()
                .testApplicationModule(new TestApplicationModule(appContext))
                .build();
        TestDataComponent dataComponent = DaggerTestDataComponent.builder()
                .testApplicationComponent(applicationComponent)
                .testDataModule(new TestDataModule(appContext))
                .build();

        mDb = dataComponent.appDatabase();
        WidgetMapper mapper = dataComponent.widgetMapper();
        dataComponent.inject(mapper);
        mWidgetDao = dataComponent.widgetDao();
        mGizmoDao = dataComponent.gizmoDao();
        mDoodadDao = dataComponent.doodadDao();
        mRepository = new WidgetRepository();
        dataComponent.inject(mRepository);

        String gizmoUuid = "be50d7f6-bf8c-4bad-9869-56990737d1a2";

        // Adding a gizmo so we don't get a foreign key error in DB
        GizmoEntity gizmo = new GizmoEntity();
        gizmo.setId(1);
        gizmo.setUuid(gizmoUuid);
        gizmo.setName("Gizmo One");
        gizmo.setDescription("One, two, three");
        mGizmoDao.insert(gizmo);

        widget1 = new Widget();
        widget1.setUuid("589a0b6e-5ce8-4759-944f-81f993b7694e");
        widget1.setGizmoUuid(gizmoUuid);
        widget1.setName("WidgetEntity One");
        widget1.setDescription("Whooptee doo");

        widget2 = new Widget();
        widget2.setUuid("86261454-f597-4586-8506-192ac6ddd471");
        widget2.setGizmoUuid(gizmoUuid);
        widget2.setName("WidgetEntity Two");
        widget2.setDescription("Whooptee doo too");

        widget3 = new Widget();
        widget3.setUuid("ac47372a-8589-4139-98ef-1559c28d974a");
        widget3.setGizmoUuid(gizmoUuid);
        widget3.setName("WidgetEntity Three");
        widget3.setDescription("Whooptee doo too three");
    }

    @Test
    public void shouldInsertAndGetWidget() throws Exception {
        int id = mRepository.insert(widget1);
        Widget widget = mRepository.getById(id);
        assertEquals("UUIDs not equal", widget1.getUuid(), widget.getUuid());
    }

    @After
    public void tearDown() throws Exception {
        mDb.close();
    }
}