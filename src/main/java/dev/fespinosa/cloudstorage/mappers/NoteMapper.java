package dev.fespinosa.cloudstorage.mappers;

import dev.fespinosa.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM notes n JOIN users u ON n.userid =n.userid WHERE u.username = #{username}")
    List<Note> getNotesByUsername(String username);

    @Insert("INSERT INTO notes (title, description, userId) VALUES (#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

    @Update("UPDATE TABLE notes SET title = #{title}, description = #{description} WHERE id = #{id}")
    void update(Note note);

    @Delete("DELETE FROM notes WHERE id = #{id}")
    void delete(Note note);

}
