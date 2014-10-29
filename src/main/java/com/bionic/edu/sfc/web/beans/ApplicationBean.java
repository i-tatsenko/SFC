package com.bionic.edu.sfc.web.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Created by docent on 28.10.14.
 */
@ManagedBean
@ApplicationScoped
public class ApplicationBean {

    public String getTheme() {
        return "/project/faces/javax.faces.resource/theme.css?ln=primefaces-hot-sneaks";
    }
}
