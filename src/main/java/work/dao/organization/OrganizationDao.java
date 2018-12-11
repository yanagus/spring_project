package work.dao.organization;

import work.model.Organization;

import java.util.List;

/**
 * DAO для работы с Organization
 */
public interface OrganizationDao {

    /**
     * Получить все объекты Organization
     *
     * @return List<Organization>
     */
    List<Organization> all();

    /**
     * Получить Organization по идентификатору
     *
     * @param id
     * @return Organization
     */
    Organization loadById(Integer id);

    /**
     * Обновить Organization
     *
     * @param organization
     */
    void update(Organization organization);

    /**
     * Сохранить Organization
     *
     * @param organization
     */
    void save(Organization organization);
}
