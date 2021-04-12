package dao;

public abstract class FileDao<T> implements Dao<T> {
    protected String filename = null;

    protected Class<T> clazz = null;

    public FileDao() { }

    protected FileDao(String filename, Class<T> clazz) {
        this.filename = filename;
        this.clazz = clazz;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
