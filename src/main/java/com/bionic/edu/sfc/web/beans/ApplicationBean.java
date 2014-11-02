package com.bionic.edu.sfc.web.beans;

import javax.inject.Named;

/**
 * Created by docent on 28.10.14.
 */
@Named
public class ApplicationBean {

    public String getTheme() {
        return "/project/faces/javax.faces.resource/theme.css?ln=primefaces-hot-sneaks";
    }
}
