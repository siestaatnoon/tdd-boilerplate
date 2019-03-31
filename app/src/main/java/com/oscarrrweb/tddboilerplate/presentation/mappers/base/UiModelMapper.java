package com.oscarrrweb.tddboilerplate.presentation.mappers.base;

import com.oscarrrweb.tddboilerplate.domain.model.base.Model;
import com.oscarrrweb.tddboilerplate.presentation.model.base.UiModel;

public final class UiModelMapper {

    public static UiModel fromDomainModel(UiModel uiModel, Model domainModel) {
        if (uiModel == null || domainModel == null) {
            return uiModel;
        }

        uiModel.setId(domainModel.getId());
        uiModel.setUuid(domainModel.getUuid());
        uiModel.setCreatedAt(domainModel.getCreatedAt());
        uiModel.setUpdatedAt(domainModel.getUpdatedAt());
        return uiModel;
    }

    public static Model toDomainModel(Model domainModel, UiModel uiModel) {
        if (domainModel == null || uiModel == null) {
            return domainModel;
        }

        domainModel.setId(uiModel.getId());
        domainModel.setUuid(uiModel.getUuid());
        domainModel.setCreatedAt(uiModel.getCreatedAt());
        domainModel.setUpdatedAt(uiModel.getUpdatedAt());
        return domainModel;
    }
}
