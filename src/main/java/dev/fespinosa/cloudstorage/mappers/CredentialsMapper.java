package dev.fespinosa.cloudstorage.mappers;


import dev.fespinosa.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CredentialsMapper {


    @Select("SELECT * FROM credentials c JOIN users u ON c.userid = u.userid WHERE u.username = #{username}")
    List<Credentials> getCredentialsByUsername(String username);

    @Select("SELECT * FROM credentials WHERE id = #{id}")
    Optional<Credentials> getCredentialById(Integer id);

    @Insert("INSERT INTO credentials (url, key, userId, username, password) VALUES (#{url}, #{key}, #{userId}, #{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Credentials credentials);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, password = #{password} WHERE id = #{id}")
    void update(Credentials credentials);

    @Delete("DELETE FROM credentials WHERE id = #{id}")
    void delete(Credentials credentials);
}
