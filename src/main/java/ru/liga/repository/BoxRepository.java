package ru.liga.repository;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.liga.entity.Box;
import ru.liga.exception.IdentityNameBoxException;
import ru.liga.mapper.BoxMapper;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
public class BoxRepository {
    private static final Logger log = LoggerFactory.getLogger(BoxRepository.class);
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final BoxMapper boxMapper;

    public BoxRepository(NamedParameterJdbcTemplate namedJdbcTemplate, BoxMapper boxMapper) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.boxMapper = boxMapper;
    }

    /**
     * Получает все типы коробки
     *
     * @return Список коробок
     */
    public List<Box> findAll() {
        String sql = "SELECT * FROM public.boxes;";
        return namedJdbcTemplate.query(sql, boxMapper);
    }

    /**
     * Поиск типа коробки по имени
     * @param name Имя типа коробик
     * @return Коробка
     * @throws IdentityNameBoxException Ошибка идентичности коробки (повторяются имена)
     */
    public @Nullable Box findByName(String name) {

        String sql = "SELECT * FROM public.boxes WHERE name = :name";
        SqlParameterSource namedParameter = new MapSqlParameterSource()
                .addValue("name", name);
        List<Box> boxes = namedJdbcTemplate.query(sql, namedParameter, boxMapper);
        if (boxes.size() > 1) {
            throw new IdentityNameBoxException();
        } else if (boxes.isEmpty()) {
            return null;
        }
        return boxes.get(0);
    }

    /**
     * Сохраняет тип коробки
     * @param box Коробка для сохранения
     */
    public Box save(Box box) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate());

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", box.getName())
                .addValue("width", box.getWidth())
                .addValue("height", box.getHeight())
                .addValue("space", boxMapper.mapListSpaceToStringSpace(box.getSpace()));
        Number id = jdbcInsert.withTableName("boxes").usingGeneratedKeyColumns("id").executeAndReturnKey(parameterSource);
        box.setId(id.intValue());
        return box;
    }

    /**
     * Обновление коробки полностью
     * @param box Коробка для обновления
     */
    public void update(Box box) {
        log.debug("{}", box);
        String sql = "UPDATE boxes SET name = :name, width = :width, height = :height, space = :space WHERE id = :id;";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", box.getId(), Types.INTEGER)
                .addValue("name", box.getName(), Types.VARCHAR)
                .addValue("width", box.getWidth(), Types.INTEGER)
                .addValue("height", box.getHeight(), Types.INTEGER)
                .addValue("space", boxMapper.mapListSpaceToStringSpace(box.getSpace()), Types.VARCHAR);
        log.debug("{}", namedJdbcTemplate.update(sql, parameterSource));


    }

    /**
     * Удаляет коробку
     * @param box Коробка для удаления
     */
    public void remove(Box box) {
        String sql = "DELETE FROM boxes WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", box.getId());
        namedJdbcTemplate.update(sql, sqlParameterSource);
    }

    public void removeByName(String name) {
        String sql = "DELETE FROM boxes WHERE name = :name";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", name);
        namedJdbcTemplate.update(sql, sqlParameterSource);
    }

}
