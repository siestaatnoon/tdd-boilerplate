package com.cccdlabs.sample.seeder;

import com.cccdlabs.sample.data.repository.GizmoRepository;
import com.cccdlabs.sample.domain.interactors.SampleDisplayUseCase;
import com.cccdlabs.sample.domain.model.Gizmo;
import com.cccdlabs.sample.presentation.di.components.DaggerTestUseCaseComponent;
import com.cccdlabs.sample.presentation.di.components.TestUseCaseComponent;
import com.cccdlabs.tddboilerplate.data.network.exception.NetworkConnectionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class SampleDisplayUseCaseTest {

    private SampleDisplayUseCase useCase;
    private GizmoRepository gizmoRepositoryMock;
    private List<Gizmo> gizmoList;

    @Before
    public void setUp() throws Exception {
        useCase = new SampleDisplayUseCase();
        TestUseCaseComponent component = DaggerTestUseCaseComponent.builder().build();
        component.inject(component.gizmoMapper());
        component.inject(component.gizmoRepository());
        component.inject(useCase);

        gizmoRepositoryMock = component.gizmoRepository();
        gizmoList = new ArrayList<>();

        Gizmo gizmo = new Gizmo();
        gizmo.setId(1);
        gizmoList.add(gizmo);

        gizmo = new Gizmo();
        gizmo.setId(2);
        gizmoList.add(gizmo);

        gizmo = new Gizmo();
        gizmo.setId(3);
        gizmoList.add(gizmo);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void SampleDisplayUserCase_shouldEmitSingleList() throws Exception {
        SampleDisplayUseCase useCaseSpy = spy(useCase);
        when(gizmoRepositoryMock.getAll()).thenReturn(gizmoList);
        when(useCaseSpy.run(null)).thenReturn(gizmoList);

        Single<List<Gizmo>> single = useCase.execute(null);
        verify(gizmoRepositoryMock, times(1)).getAll();
        verify(useCaseSpy, times(1)).run(null);

        TestObserver<List<Gizmo>> testObserver = single.test();
        testObserver.assertComplete();
        testObserver.assertResult(gizmoList);
        testObserver.assertValue(gizmoList);
        testObserver.dispose();
    }

    @Test
    public void SampleDisplayUserCase_shouldEmitSingleError() throws Exception {
        SampleDisplayUseCase useCaseSpy = spy(useCase);
        NetworkConnectionException error = new NetworkConnectionException();
        Single<List<Gizmo>> single = Single.error(error);
        when(useCaseSpy.execute(null)).thenReturn(single);

        single = useCaseSpy.execute(null);
        TestObserver<List<Gizmo>> testObserver = single.test();
        testObserver.assertError(error);
        testObserver.assertNotComplete();
        testObserver.dispose();
    }

    @After
    public void tearDown() throws Exception {
        useCase = null;
        gizmoList = null;
    }
}
