package com.oscarrrweb.tddboilerplate.data.entity.sample;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;

import static com.oscarrrweb.tddboilerplate.data.entity.sample.Gizmo.TABLE_NAME;

@Entity(tableName = TABLE_NAME,
        indices = {
                @Index(value = "uuid", unique = true),
                @Index("created_at"),
                @Index("updated_at")
        }
)
public class Gizmo extends com.oscarrrweb.tddboilerplate.data.entity.base.Entity {

    protected static final String TABLE_NAME = "gizmos";

    private String name;

    private String value;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
