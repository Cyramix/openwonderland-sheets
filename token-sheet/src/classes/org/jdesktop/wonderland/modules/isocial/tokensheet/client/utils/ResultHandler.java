/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils;

import org.jdesktop.wonderland.modules.isocial.client.view.ResultListener;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;

/**
 *
 * @author Ryan
 */
public abstract class ResultHandler implements ResultListener {

    
    protected abstract void processAndReactToResult(Result result);
    
    public void resultAdded(Result result) {
        processAndReactToResult(result);
    }

    public void resultUpdated(Result result) {
        processAndReactToResult(result);
    }
    
}
