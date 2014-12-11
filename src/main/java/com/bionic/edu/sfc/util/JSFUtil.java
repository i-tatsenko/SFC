package com.bionic.edu.sfc.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by docent on 04.12.14.
 */
public final class JSFUtil {

    private JSFUtil() {
    }

    public static void showMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }

    public static void showMessage(String message, String details) {

    }
}
