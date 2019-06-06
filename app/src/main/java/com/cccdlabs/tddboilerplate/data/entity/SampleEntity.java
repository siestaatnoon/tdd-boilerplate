package com.cccdlabs.tddboilerplate.data.entity;

import androidx.room.Index;

import com.cccdlabs.tddboilerplate.data.entity.base.Entity;

import static com.cccdlabs.tddboilerplate.data.entity.SampleEntity.TABLE_NAME;

@androidx.room.Entity(tableName = TABLE_NAME,
        indices = {
                @Index(value = "uuid", unique = true)
        }
)
public class SampleEntity extends Entity {

    protected static final String TABLE_NAME = "sample";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
