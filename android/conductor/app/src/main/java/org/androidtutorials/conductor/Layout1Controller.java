package org.androidtutorials.conductor;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

/**
 * The Controller for Layout 1.
 * This controller is responsible for inflating the simple layout file we created, controller_1_layout.xml.
 */

public class Layout1Controller extends Controller {
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.controller_1_layout, container, false);
        return viewGroup;
    }
}
