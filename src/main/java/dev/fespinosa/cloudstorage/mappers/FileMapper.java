package dev.fespinosa.cloudstorage.mappers;

import dev.fespinosa.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files f JOIN users u ON f.userid = u.userid WHERE u.username = #{username}")
    List<File> getFilesByUsername(String username);

    @Select("SELECT * FROM files WHERE name = #{name}")
    Optional<File> getFileByName(String name);

    @Insert("INSERT INTO files (name, contentType, fileSize, userId) VALUES (#{name}, #{contentType}, #{fileSize}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(File file);

    @Update("UPDATE files SET name = #{name}, contentType = #{contentType}, fileSize = #{fileSize}, userId = #{userId} WHERE id = #{id}")
    void update(File file);

    @Delete("DELETE FROM files WHERE id = #{id}")
    void delete(File file);

}
