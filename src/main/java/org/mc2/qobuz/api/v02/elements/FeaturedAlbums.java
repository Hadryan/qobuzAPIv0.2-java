/*
 * JAVA client for QOBUZ.API (http://www.qobuz.com/fr-fr/page/labs).
 *
 * Copyright (C) 2017 Marco Curti (marcoc1712 at gmail dot com).
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.mc2.qobuz.api.v02.elements;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.mc2.qobuz.api.v02.exceptions.QobuzAPIException;
import org.mc2.qobuz.api.v02.lists.AlbumList;

/**
 *
 * @author marco
 */
public final class FeaturedAlbums extends QobuzObject{

    public static final String ALBUMS = "albums";
    
    
    private AlbumList albums;
    
    public FeaturedAlbums() {
        super();
    }

    public FeaturedAlbums (JSONObject jsonObject)throws QobuzAPIException {
        super(jsonObject);
        
        KeyList.add(ALBUMS);
        
        checkJSONObject(jsonObject);
        /**
         * If there is no parameter there is no need to go further
         */
        if (jsonObject == null) {
            throw new NullPointerException();
        }

        try {
                albums = jsonObject.has(ALBUMS) ? 
                        jsonObject.isNull(ALBUMS) ? 
                            null : new AlbumList(jsonObject.getJSONObject(ALBUMS)) : null;
                
                
            } catch (JSONException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                throw new QobuzAPIException(ex.getMessage(), ex);
        }
    }
    
    /**
     * @return the store
     */
    public AlbumList getAlbums() {
        return albums;
    }

}
