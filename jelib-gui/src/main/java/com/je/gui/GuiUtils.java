package com.je.gui;

import com.je.gui.dialog.Dialog;
import com.je.gui.dialog.ExceptionDialog;

/**
 * Utilities related to GUI stuff.
 */
public final class GuiUtils {
    private static Dialog<Throwable> sExceptionDialog;
    
    public static Dialog<Throwable> exceptionDialog() {
        if(sExceptionDialog == null)
            sExceptionDialog = new ExceptionDialog();
        return sExceptionDialog;
    }

    /**
     * A private constructor prevents instantiation.
     */
    private GuiUtils(){
    }
}
