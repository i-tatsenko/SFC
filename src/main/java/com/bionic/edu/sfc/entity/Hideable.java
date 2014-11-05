package com.bionic.edu.sfc.entity;

import javax.persistence.*;

/**
 * Created by docent on 05.11.14.
 */
@MappedSuperclass
public abstract class Hideable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean visible = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
