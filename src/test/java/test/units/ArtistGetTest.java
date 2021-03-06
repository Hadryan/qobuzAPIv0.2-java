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
package test.units;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.mc2.qobuz.api.v02.elements.Artist;
import org.mc2.qobuz.api.v02.elements.SimilarArtists;
import org.mc2.qobuz.api.v02.lists.AlbumList;
import org.mc2.qobuz.api.v02.lists.ArtistList;
import org.mc2.qobuz.api.v02.lists.TrackList;
import org.mc2.qobuz.api.v02.query.ArtistGet;
import org.mc2.qobuz.api.v02.query.ArtistGetSimilar;
import test.utils.TestUtils;

/**
 *
 * @author marco
 */
public class ArtistGetTest {
    @Test
    public void getArtistComplete() {
    
        try {
            ArtistGet q = new ArtistGet((long)720);
            Artist artist= q.getArtist();
            
            ArtistGetSimilar artistGetSimilar = new ArtistGetSimilar(artist.getId(),(long)0);
            SimilarArtists similarArtists = artistGetSimilar.getSimilaArtist();
            ArtistList similarArtistList = similarArtists.getArtists();
            
            ArtistGet artistAlbumsQuery = new ArtistGet((long)720, ArtistGet.EXTRA_ALBUMS,(long)0);
            Artist artistAlbums = artistAlbumsQuery.getArtist();
            AlbumList artistAlbumsList = artistAlbums.getAlbums();
            
            ArtistGet artistTracksQuery = new ArtistGet((long)720, ArtistGet.EXTRA_TRACKS,(long)0);
            Artist artistTracks = artistTracksQuery.getArtist();
            TrackList artistTracksList = artistTracks.getTracks();
            
           
        
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    //@Test
    public void getArtist() {

        try {
                ArtistGet q = new ArtistGet((long)720);
                Artist artist= q.getArtist();

                //System.setOut(new PrintStream(System.out, true, "utf-8"));
                
                System.out.println("======================================================================");
                System.out.println("= ARTIST (with no Album)                                             =");
                System.out.println("======================================================================");
                
                TestUtils.printArtist(artist,""); 
                
                System.out.println("======================================================================");
                
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    //@Test
    public void getArtistWithAlbums() {

        try {
                ArtistGet q = new ArtistGet((long)720,ArtistGet.EXTRA_ALBUMS, (long)0);
                Artist artist= q.getArtist();
                artist.completeAlbumList();

                //System.setOut(new PrintStream(System.out, true, "utf-8"));
                
                System.out.println("======================================================================");
                System.out.println("= ARTIST (with complete Album list)                                  =");
                System.out.println("======================================================================");
                
                TestUtils.printArtist(artist,""); 
                
                System.out.println("======================================================================");
                
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
     //@Test
    public void getArtistWithTracks() {

        try {
                ArtistGet q = new ArtistGet((long)720,ArtistGet.EXTRA_TRACKS, (long)0);
                Artist artist= q.getArtist();
                artist.completeTrackList();

                //System.setOut(new PrintStream(System.out, true, "utf-8"));
                
                System.out.println("======================================================================");
                System.out.println("= ARTIST (with complete Track list)                                  =");
                System.out.println("======================================================================");
                
                TestUtils.printArtist(artist,""); 
                
                System.out.println("======================================================================");
                
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    //@Test
    public void getArtistWithTracksAndPagination() {

        try {
                ArtistGet q = new ArtistGet((long)720, ArtistGet.EXTRA_TRACKS,(long)0);
                Artist artist= q.getArtist();
                TrackList tracks = artist.getTracks();

                //System.setOut(new PrintStream(System.out, true, "utf-8"));
                
                System.out.println("======================================================================");
                System.out.println("= ARTIST (with Tracks and pagination)                                =");
                System.out.println("======================================================================");
                
                TestUtils.printArtist(artist,""); 
                
                int page=1;
                long size=0;
                
                if (tracks!= null) {         
                    size = tracks.getItems().size();     
                }
                
                boolean stop = (tracks == null);
                
                while (!stop) {
                    
                    System.out.println("= "+page+" =");
                    
                    TrackList extra = artist.loadNextTracksPage();
                    stop = (tracks.getItems().size() == size); 
                    size = tracks.getItems().size();
                    
                    TestUtils.printTrackList(extra, "    ");
                    
                    page++;
                    
                } 
                System.out.println("= "+page+" =");
                System.out.println("======================================================================");
                
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
