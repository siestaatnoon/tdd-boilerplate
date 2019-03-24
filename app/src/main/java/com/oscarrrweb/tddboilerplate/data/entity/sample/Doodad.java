package com.oscarrrweb.tddboilerplate.data.entity.sample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import com.google.gson.annotations.SerializedName;

import static com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad.TABLE_NAME;

@Entity(tableName = TABLE_NAME,
        indices = {
                @Index(value = "uuid", unique = true),
                @Index("widget_uuid"),
                @Index("date")
        },
        foreignKeys = @ForeignKey(
                entity = Widget.class,
                parentColumns = "uuid",
                childColumns = "widget_uuid"
        )
)
public class Doodad extends com.oscarrrweb.tddboilerplate.data.entity.base.Entity {

    protected static final String TABLE_NAME = "doodads";

    @ColumnInfo(name = "widget_uuid")
    @SerializedName("widget_uuid")
    private String widgetUuid;

    private String name;

    private String value;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public String getWidgetUuid() {
        return widgetUuid;
    }

    public void setWidgetUuid(String widgetUuid) {
        this.widgetUuid = widgetUuid;
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
