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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mc2.qobuz.api.v02.exceptions.QobuzAPIException;
import org.mc2.qobuz.api.v02.lists.AlbumList;
import org.mc2.qobuz.api.v02.query.GenreGet;

/**
 *
 * @author marco
 */
public final class  Genre extends QobuzObject{

    public static final String ID = "id";
    public static final String COLOR = "color";
    public static final String NAME = "name";
    public static final String PATH = "path";
    public static final String SLUG = "slug";
    public static final String ALBUMS = "albums";
    
    private Long id;
    private String slug;
    private String color;
    private String name;
    private AlbumList albums;
     
    private ArrayList<Long> path = new ArrayList<>();
    
    public Genre() {
        super();
    }

    public Genre (JSONObject jsonObject)throws QobuzAPIException {
         super(jsonObject);
         
        KeyList.add(ID);
        KeyList.add(COLOR);
        KeyList.add(NAME);
        KeyList.add(PATH);
        KeyList.add(SLUG);
        KeyList.add(ALBUMS);
        
        checkJSONObject(jsonObject);
        /**
         * If there is no parameter there is no need to go further
         */
        if (jsonObject == null) {
            throw new NullPointerException();
        }

        try {
                name = jsonObject.getString(NAME);
                               
                id = jsonObject.has(ID) ? 
                            jsonObject.isNull(ID) ? 
                            null : jsonObject.getLong(ID) : null;
                
                slug = jsonObject.has(SLUG) ? 
                            jsonObject.isNull(SLUG) ? 
                            null : jsonObject.getString(SLUG) : null;
                
                color = jsonObject.has(COLOR) ? 
                            jsonObject.isNull(COLOR) ? 
                            null : jsonObject.getString(COLOR) : null;
                
                albums = jsonObject.has(ALBUMS) ? 
                        jsonObject.isNull(ALBUMS) ? 
                            null : new AlbumList(jsonObject.getJSONObject(ALBUMS)) : null;
                
                if (jsonObject.has(PATH)) {
                    JSONArray Jgenres = jsonObject.getJSONArray(PATH);
                    for (int i = 0; i < Jgenres.length(); i++) {
                        path.add(Jgenres.getLong(i));
                    }
                }
                
            } catch (JSONException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                throw new QobuzAPIException(ex.getMessage(), ex);
        }
    }
     public boolean isAlbumlistComplete(){
        
        if (albums == null) return true; //no album to get.
        
        // API BUG: sometime reported total is different than the actual number
        // of records, forget it and get all that we have.
        
        //return (albums.getItems().size()>= albums.getTotal());
        return false;
    }
    public void completeAlbumList() throws QobuzAPIException{

        long size = 0;
        if (albums != null){
            
            size = albums.getItems().size();
        }
        boolean stop = (albums == null); //no albums.
        
        while (!stop) {

            loadNextAlbumsPage();
            stop = (albums.getItems().size() == size); 
            size = albums.getItems().size();
            
        } 
    }
    public AlbumList loadNextAlbumsPage() throws QobuzAPIException{
    
        if (!isAlbumlistComplete()){
            
            AlbumList extra = getNexAlbumsPage();
            
            albums.getItems().addAll(extra.getItems());
            return extra;
        }
        return null;
    }
    private AlbumList getNexAlbumsPage() throws QobuzAPIException{
    
        if (!isAlbumlistComplete()) {
            
            GenreGet q = new GenreGet(id, (long)albums.getItems().size());
            Genre extra = q.getGenre();
            
            return extra.getAlbums();
        }
        return null;
    }
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the path
     */
    public ArrayList<Long> getPath() {
        return path;
    }

    /**
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }
    /**
     * @return the albums
     */
    public AlbumList getAlbums() {
        return albums;
    }
}
