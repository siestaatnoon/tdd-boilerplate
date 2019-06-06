package com.cccdlabs.sample.data.repository;

import com.cccdlabs.sample.data.mappers.DoodadMapper;
import com.cccdlabs.sample.data.storage.dao.DoodadDao;
import com.cccdlabs.sample.data.entity.DoodadEntity;
import com.cccdlabs.sample.domain.model.Doodad;
import com.cccdlabs.tddboilerplate.data.repository.base.AbstractRepository;

import javax.inject.Inject;

public class DoodadRepository extends AbstractRepository<DoodadEntity, Doodad, DoodadMapper, DoodadDao> {

    @Inject
    public DoodadRepository() {}
}
