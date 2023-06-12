package musicmanager.application.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MusicCollectionArrayList extends MusicCollection{
    public MusicCollectionArrayList() {
        super();
        this.setMusics(new ArrayList<Music>());
    }
    public MusicCollectionArrayList(MusicCollectionArrayList musics) {
        super();
        this.setMusics(musics);
    }

    @Override
    public Music searchMusic(String title) {
        for (Music m: musics) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public boolean addMusic(Music music) {
        Music m = searchMusic(music.getTitle());
        if (m == null) {
            musics.add(music);
            setQtdMusics(getQtdMusics() + 1);
            return true;
        }
        return false;
    }

    @Override
    public Music removeMusic(Music music) {
        Music m = searchMusic(music.getTitle());
        if (m != null) {
            musics.remove(m);
            setQtdMusics(getQtdMusics() - 1);
        }
        return m;
    }

    @Override
    public boolean updateMusic(Music music) {
        Music m = searchMusic(music.getTitle());
        if (m != null) {
            m.setId(music.getId());
            m.setTitle(music.getTitle());
            m.setAuthors(music.getAuthors());
            m.setDuration(music.getDuration());
            m.setGenre(music.getGenre());
            m.setDate(music.getDate());
            return true;
        }
        return false;
    }

    @Override
    public void show() {
        for (Music m : getMusics()) {
            System.out.println(m.toString());
        }
    }

    @Override
    public int size() {
        return getQtdMusics();
    }

    @Override
    public boolean isEmpty() {
        return getQtdMusics() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Music m: getMusics()) {
            if (m.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Music> iterator() {
        return getMusics().iterator();
    }

    @Override
    public Object[] toArray() {
        /*int pos = 0;
        Object[] array = new Object[getQtdMusics()];
        for (Music m: getMusics()) {
            array[pos++] = m;
        }
        return array;*/
        return getMusics().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getMusics().toArray(a);
    }

    @Override
    public boolean add(Music music) {
        if (contains(music)) {
            return false;
        }
        return getMusics().add(music);
    }

    @Override
    public boolean remove(Object o) {
        return getMusics().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getMusics().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Music> c) {
        return getMusics().addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getMusics().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getMusics().retainAll(c);
    }

    @Override
    public void clear() {
        getMusics().clear();
    }
}
