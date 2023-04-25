package Dao;

import java.io.IOException;

public interface Dao {
    void insert() throws IOException;
    void update() throws IOException;
    void delete() throws IOException;

}
