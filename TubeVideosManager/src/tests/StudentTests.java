package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import tubeVideosManager.Genre;
import tubeVideosManager.Playlist;
import tubeVideosManager.TubeVideosManager;
import tubeVideosManager.Video;

/**
 * 
 * You need student tests if you are asking for help during office hours about
 * bugs in your code. Feel free to use tools available in TestingSupport.java
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

	Video video = new Video("hi", "google.com", 10, Genre.Educational);
	Video copyVideo = new Video(video);
	Video diffVideo = new Video("abc", "bob.com", 18, Genre.Documentary);
	Playlist playlist = new Playlist("messi");
	Playlist copyPlaylist = new Playlist(playlist);
	TubeVideosManager tube = new TubeVideosManager();

	@Test
	public void test01VideoConstructor() {
		System.out.println(video);
	}

	@Test
	public void test02VideoCopyConstructor() {
		System.out.println(video);
		System.out.println(copyVideo);
	}

	@Test
	public void test03VideoGetters() {
		System.out.println(video.getTitle());
		System.out.println(video.getUrl());
		System.out.println(video.getDurationInMinutes());
		System.out.println(video.getGenre());
	}

	@Test
	public void test04VideoComments() {
		video.addComments("hiiiiiii");
		System.out.println(video.getComments());
	}

	@Test
	public void test05VideoCompareTo() {
		System.out.println(diffVideo.compareTo(video));
	}

	@Test
	public void test06VideoEquals() {
		System.out.println(video.equals(copyVideo));
	}

	@Test
	public void test07PlaylistConstructor() {
		System.out.println(playlist);
	}

	@Test
	public void test07PlaylistCopyConstructor() {
		System.out.println(playlist);
		System.out.println(copyPlaylist);
	}

	@Test
	public void test08PlaylistGetName() {
		System.out.println(playlist.getName());
	}

	@Test
	public void test09PlaylistAddAndGetPlaylist() {
		playlist.addToPlaylist("testtttt");
		System.out.println(playlist.getPlaylistVideosTitles());
	}

	@Test
	public void test10PlaylistRemove() {
		playlist.addToPlaylist("hii");
		playlist.addToPlaylist("ronaldo");
		playlist.addToPlaylist("hii");
		System.out.println(playlist.getPlaylistVideosTitles());
		playlist.removeFromPlaylistAll("hii");
		System.out.println(playlist.getPlaylistVideosTitles());
	}

	@Test
	public void test11PlaylistShuffle() {
		playlist.addToPlaylist("chargers");
		playlist.addToPlaylist("chiefs");
		playlist.addToPlaylist("broncos");
		playlist.addToPlaylist("raiders");
		System.out.println(playlist.getPlaylistVideosTitles());
		playlist.shuffleVideoTitles(null);
		System.out.println(playlist.getPlaylistVideosTitles());
	}

	@Test
	public void test12TubeVideosManagerConstructor() {
		System.out.println(tube);
	}

	@Test
	public void test13TubeVideosManagerAddGetVideoDB() {
		System.out.println(tube.addVideoToDB("packers", "vikings.com", 100, Genre.Comedy));
		System.out.println(tube.addVideoToDB("eagles", "cowboys.com", 100, Genre.Comedy));
		System.out.println(tube.getAllVideosInDB());
	}

	@Test
	public void test14TubeVideosManagerFindVideo() {
		tube.addVideoToDB("bears", "lions.com", 1000, Genre.Comedy);
		tube.addVideoToDB("Arsenal", "Manchester.com", 100, Genre.FilmAnimation);
		System.out.println(tube.findVideo("Arsenal"));
	}

	@Test
	public void test15TubeVideosManagerAddComments() {
		tube.addVideoToDB("Saka", "Arsenal.com", 120, Genre.Documentary);
		tube.addComments("Saka", "Best Soccer Player");
		System.out.println(tube.findVideo("Saka").getComments());
	}

	@Test
	public void test16TubeVideosManagerAddGetPlaylistNames() {
		tube.addPlaylist("Soccer");
		tube.addPlaylist("Tennis");
		tube.addPlaylist("Basketball");
		tube.addPlaylist("Football");
		System.out.println(Arrays.toString(tube.getPlaylistsNames()));
	}

	@Test
	public void test17AddGetVideoToPlaylist() {
		tube.addPlaylist("Hockey");
		tube.addVideoToDB("Wayne", "Hockey.com", 300, Genre.Educational);
		tube.addVideoToPlaylist("Wayne", "Hockey");
		System.out.println(tube.getPlaylist("Hockey"));
	}

	@Test
	public void test18ClearDatabase() {
		tube.addPlaylist("Golf");
		tube.addVideoToDB("Tiger", "Golf.com", 300, Genre.Documentary);
		System.out.println(Arrays.toString(tube.getPlaylistsNames()));
		System.out.println(tube.getAllVideosInDB());
		tube.clearDatabase();
		System.out.println(Arrays.toString(tube.getPlaylistsNames()));
		System.out.println(tube.getAllVideosInDB());
	}

	@Test
	public void test19SearchForVideos() {
		tube.addVideoToDB("Tiger1", "Golf1.com", 100, Genre.Documentary);
		tube.addVideoToDB("Tiger2", "Golf2.com", 200, Genre.Comedy);
		tube.addVideoToDB("Tiger3", "Golf3.com", 300, Genre.Educational);
		tube.addVideoToDB("Tiger4", "Golf4.com", 400, Genre.FilmAnimation);
		tube.addVideoToDB("Tiger5", "Golf5.com", 500, Genre.Music);
		tube.addVideoToDB("Tiger6", "Golf6.com", 600, Genre.Documentary);
		tube.addVideoToDB("Tiger7", "Golf7.com", 700, Genre.Comedy);
		tube.addVideoToDB("Tiger1", "Golf11.com", 800, Genre.FilmAnimation);
		tube.addVideoToDB("Tiger1", "Golf111.com", 900, Genre.Documentary);
		System.out.println(tube.searchForVideos("Hi", "Tiger1", 1000, Genre.Documentary));
		System.out.println(tube.searchForVideos("Hi2", null, -1, null));
		System.out.println(tube.searchForVideos("Hi3", null, 1000, Genre.Documentary));
		System.out.println(tube.searchForVideos("Hi4", "Tiger1", 500, Genre.Documentary));
		System.out.println(tube.searchForVideos("Hi5", null, -1, Genre.FilmAnimation));
		System.out.println(tube.searchForVideos("Hi6", "Tiger1", 1000, null));
		System.out.println(tube.searchForVideos("Hi7", null, 600, null));
	}
}
