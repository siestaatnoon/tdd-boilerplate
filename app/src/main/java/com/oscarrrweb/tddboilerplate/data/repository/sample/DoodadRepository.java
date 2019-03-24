package com.oscarrrweb.tddboilerplate.data.repository.sample;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad;
import com.oscarrrweb.tddboilerplate.data.mappers.sample.DoodadMapper;
import com.oscarrrweb.tddboilerplate.data.repository.base.AbstractRepository;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;

import javax.inject.Inject;

public class DoodadRepository extends AbstractRepository<Doodad,
        com.oscarrrweb.tddboilerplate.domain.model.sample.Doodad, DoodadMapper, DoodadDao> {

    @Inject
    public DoodadRepository() {}
}
