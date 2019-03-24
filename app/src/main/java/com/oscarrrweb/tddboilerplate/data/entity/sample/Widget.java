package com.oscarrrweb.tddboilerplate.data.entity.sample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.oscarrrweb.tddboilerplate.data.entity.sample.Widget.TABLE_NAME;

@Entity(tableName = TABLE_NAME,
        indices = {
                @Index(value = "uuid", unique = true),
                @Index("gizmo_uuid")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Gizmo.class,
                        parentColumns = "uuid",
                        childColumns = "gizmo_uuid"
                )
        }
)
public class Widget extends com.oscarrrweb.tddboilerplate.data.entity.base.Entity {

    protected static final String TABLE_NAME = "widgets";

    @ColumnInfo(name = "gizmo_uuid")
    @SerializedName("gizmo_uuid")
    private String gizmoUuid;

    private String name;

    private String value;

    @Ignore
    private List<Doodad> doodads;


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public String getGizmoUuid() {
        return gizmoUuid;
    }

    public void setGizmoUuid(String gizmoUuid) {
        this.gizmoUuid = gizmoUuid;
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

    public List<Doodad> getDoodads() {
        return doodads;
    }

    public void setDoodads(List<Doodad> doodads) {
        this.doodads = doodads;
    }

}