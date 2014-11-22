/** Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.xmlvm.tutorial.android.map;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * This application uses the Google API to display a map of San Francisco. This
 * API is not part of the core Android API but is Google-specific. In order to
 * use the map API, an API key has to be created and pasted into
 * AndroidManifest.xml. Map applications also require Google Play Services to be
 * configured. Note that this app only runs on an actual device and not on the
 * emulator. A complete step-by-step tutorial is given on the official Google
 * Map web page: https://developers.google.com/maps/documentation/android/start
 */
public class MapActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        MapFragment frag = (MapFragment) this.getFragmentManager().findFragmentById(R.id.mapview);
        GoogleMap map = frag.getMap();

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.779300, -122.419200));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

        map.moveCamera(center);
        map.animateCamera(zoom);
    }
}